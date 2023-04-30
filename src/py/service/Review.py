"""
py file that contains all information about the review object and functions supporting object
"""
from datetime import datetime
from dataclasses import dataclass

from mysql.connector import errors

from util.database.Database import Database
from util.database.DatabaseLookups import DatabaseLookups
from util.database.DatabaseStatus import DatabaseStatus
from util.handling.errors.database.DatabaseConnectionError import DatabaseConnectionError
from util.handling.errors.database.DatabaseObjectAlreadyExists import DatabaseObjectAlreadyExists
from util.handling.errors.database.FailedToCreateDatabaseObject import FailedToCreateDatabaseObject


@dataclass
class Review:
    review_id: int
    review_date: datetime = None
    rating: float = None
    comment: str = None
    request_id: int = None

    def create_review(self):
        database = Database.database_handler(DatabaseLookups.User)  # create database connection

        # check if database is connected, if not connect
        if database.status is DatabaseStatus.Disconnected:
            database.connect()

        elif database.status is DatabaseStatus.NoImplemented:
            raise DatabaseConnectionError(table='review', query=None, database_object=None)

        # construct query for creation
        database.insert(self, 'review', ('review_id', ))

        # try to run query
        try:
            self.review_id = database.run()
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
                raise DatabaseObjectAlreadyExists(table='review', query=query, database_object=self)

            # some other consistency constraint check
            raise FailedToCreateDatabaseObject(table='review', query=query, database_object=self)

        # clear database tool
        database.clear()
        database.disconnect()

        return Review.get_review(self.review_id)

    def update_review(self):
        pass

    @staticmethod
    def get_review(request_id: int):
        # create database session
        database = Database.database_handler(DatabaseLookups.User)

        # check if session is connected
        if database.status is not DatabaseStatus.Connected:
            database.connect()

        elif database.status is DatabaseStatus.NoImplemented:
            raise DatabaseConnectionError(table='review', query=None, database_object=None)

        # create query for database connection
        database.select(('request_id',), 'request')
        database.where('professional_id = %s', professional_id)

        # try to run query
        results = database.run()

        # if nothing is found return error
        if len(results) == 0:
            return None

        # parse into request object
        requests = []
        for result in results:
            requests.append(Request.get_request(result[0]))

        return requests
