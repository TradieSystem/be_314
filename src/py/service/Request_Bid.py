"""
py that holds all details regarding Offer
"""
from datetime import datetime
from dataclasses import dataclass
from decimal import Decimal

from mysql.connector import errors

from service.Bid_Status import Bid_Status
from user.Professional import Professional
from user.User import User
from util.database.Database import Database
from util.database.DatabaseLookups import DatabaseLookups
from util.database.DatabaseStatus import DatabaseStatus
from util.handling.errors.database.DatabaseConnectionError import DatabaseConnectionError
from util.handling.errors.database.DatabaseObjectAlreadyExists import DatabaseObjectAlreadyExists
from util.handling.errors.database.FailedToCreateDatabaseObject import FailedToCreateDatabaseObject
from util.handling.errors.database.NoDatabaseObjectFound import NoDatabaseObjectFound


@dataclass
class Request_Bid:
    request_bid_id: int = None
    request_id: int = None
    professional_id: int = None
    amount: Decimal = None
    sent_date: datetime = None
    accepted_by_client_date: datetime = None
    professional_cancelled_date: datetime = None
    bid_status_id: int = None

    def create_bid(self):
        database = Database.database_handler(DatabaseLookups.User)  # create database connection

        # check if database is connected, if not connect
        if database.status is DatabaseStatus.Disconnected:
            database.connect()

        elif database.status is DatabaseStatus.NoImplemented:
            raise DatabaseConnectionError(table='request', query=None, database_object=None)

        # construct query for creation
        database.insert(self, 'request_bid', ('request_bid_id', 'accepted_by_client_date',
                                              'professional_cancelled_date'))

        # try to run query
        try:
            self.request_bid_id = database.run()
            database.commit()

        except errors.IntegrityError as ie:  # in case that change violates consistency constraints
            # clean up instance and rollback to remove lock
            database.rollback()
            query = database.review_query()
            database.clear()
            database.disconnect()

            # if there is an integrity error
            if ie.errno == 1452:  # cannot solve gracefully
                # raise error
                raise DatabaseObjectAlreadyExists(table='request_bid', query=query, database_object=self)

            # some other consistency constraint check
            raise FailedToCreateDatabaseObject(table='request_bid', query=query, database_object=self)

        # clear database tool
        database.clear()
        database.disconnect()

        return Request_Bid.get_request_bid(self.request_bid_id)

    def update_bid(self):
        database = Database.database_handler(DatabaseLookups.User)  # create database connection

        # check if database is connected, if not connect
        if database.status is DatabaseStatus.Disconnected:
            database.connect()

        elif database.status is DatabaseStatus.NoImplemented:
            raise DatabaseConnectionError(table='request', query=None, database_object=None)

        # construct query for creation
        database.update(self, 'request_bid', ('request_bid_id', 'accepted_by_client_date',
                                              'professional_cancelled_date'))
        database.where('request_bid_id = %s', self.request_bid_id)

        # try to run query
        try:
            database.run()
            database.commit()

        except errors.IntegrityError as ie:  # in case that change violates consistency constraints
            # clean up instance and rollback to remove lock
            database.rollback()
            query = database.review_query()
            database.clear()
            database.disconnect()

            # if there is an integrity error
            if ie.errno == 1452:  # cannot solve gracefully
                # raise error
                raise DatabaseObjectAlreadyExists(table='request_bid', query=query, database_object=self)

            # some other consistency constraint check
            raise FailedToCreateDatabaseObject(table='request_bid', query=query, database_object=self)

        # clear database tool
        database.clear()
        database.disconnect()

        return Request_Bid.get_request_bid(self.request_bid_id)

    @staticmethod
    def get_request_bid(request_bid_id: int) -> 'Request_Bid':
        # create database session
        database = Database.database_handler(DatabaseLookups.User)

        # check if session is connected
        if database.status is not DatabaseStatus.Connected:
            database.connect()

        elif database.status is DatabaseStatus.NoImplemented:
            raise DatabaseConnectionError(table='request', query=None, database_object=None)

        # create query for database connection
        database.select(('request_bid_id', 'request_id', 'professional_id', 'amount', 'sent_date', 'bid_status_id'),
                        'request_bid')
        database.where('request_bid_id = %s', request_bid_id)

        # try to run query
        result = database.run()

        # if nothing is found return None
        if len(result) == 0:
            return None

        # parse into request object
        request_bid = Request_Bid(request_bid_id=result[0][0], request_id=result[0][1], professional_id=result[0][2],
                                  amount=result[0][3], sent_date=result[0][4], bid_status_id=result[0][5])

        return request_bid

    @staticmethod
    def get_by_request_id(request_id: int) -> ['Request_Bid']:
        database = Database.database_handler(DatabaseLookups.User)

        # check if session is connected
        if database.status is not DatabaseStatus.Connected:
            database.connect()

        elif database.status is DatabaseStatus.NoImplemented:
            raise DatabaseConnectionError(table='request', query=None, database_object=None)

        # create query for database connection
        database.select(('request_bid_id', 'request_id', 'professional_id', 'amount', 'sent_date', 'bid_status_id'),
                        'request_bid')
        database.where('request_id = %s', request_id)

        # run database
        results = database.run()

        # if no objects found
        if len(results) == 0:
            return None

        # iterate through results and create request bids
        request_bids = []
        for result in results:
            request_bids.append(Request_Bid(request_bid_id=result[0], request_id=result[1], professional_id=result[2],
                                            amount=result[3], sent_date=result[4], bid_status_id=result[5]))

        return request_bids

    @staticmethod
    def ToAPI(obj):
        if isinstance(obj, Request_Bid):
            remap = {
                "applicationID": obj.request_bid_id,
                "requestID": obj.request_id,
                "offerDate": obj.sent_date.strftime('%m/%d/%Y') if obj.sent_date is not None else None,
                "professionalID": Professional.get_by_professional_id(obj.professional_id).user_id if obj.professional_id is not None else None,
                "applicationStatus": Bid_Status.get_by_status_id(obj.bid_status_id).status_name if obj.bid_status_id is not None else None,
                "cost": obj.amount.__str__()
            }

            return remap

        raise TypeError

    @staticmethod
    def FromAPI(obj):
        # convert user_id for professional to professional_id
        professional_id = User.get_user(obj.get('professionalID')).professional.professional_id if obj.get('professionalID') is not None else None

        # get bid status id
        if obj.get('applicationStatus') is not None:
            bid_status_id = Bid_Status.get_by_status_name(obj.get('applicationStatus')).bid_status_id
        else:
            bid_status_id = None

        # convert date
        sent_date = datetime.strptime(obj.get('offerDate'), '%m/%d/%Y') if obj.get('offerDate') is not None else None

        # create object
        return Request_Bid(request_bid_id=obj.get('applicationID'), request_id=obj.get('requestID'), sent_date=sent_date,
                           professional_id=professional_id, amount=obj.get('cost'), bid_status_id=bid_status_id)
