"""
py that describes transaction object, handles all functionality to do with transactions
"""
from datetime import datetime
from dataclasses import dataclass

from mysql.connector import errors

from service.Request import Request
from util.database.Database import Database
from util.database.DatabaseLookups import DatabaseLookups
from util.database.DatabaseStatus import DatabaseStatus
from util.handling.errors.database.DatabaseConnectionError import DatabaseConnectionError
from util.handling.errors.database.DatabaseObjectAlreadyExists import DatabaseObjectAlreadyExists
from util.handling.errors.database.FailedToCreateDatabaseObject import FailedToCreateDatabaseObject


@dataclass
class Transaction:
    transaction_id: int
    cost: float = None
    transaction_date: datetime = None
    transaction_status_id: int = None
    user_id: int = None
    billing_type_id: int = None
    billing_id: int = None

    def create_transaction(self, user_id: int):
        database = Database.database_handler(DatabaseLookups.User)  # create database connection

        # check if database is connected, if not connect
        if database.status is DatabaseStatus.Disconnected:
            database.connect()

        elif database.status is DatabaseStatus.NoImplemented:
            raise DatabaseConnectionError(table='transaction', query=None, database_object=None)

        # if user_id not populated
        if self.user_id is None:
            self.user_id = user_id

        # construct query for creation
        database.insert(self, 'transaction', ('transaction_id',))

        # try to run query
        try:
            self.transaction_id = database.run()
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
                raise DatabaseObjectAlreadyExists(table='user', query=query, database_object=self)

            # some other consistency constraint check
            raise FailedToCreateDatabaseObject(table='user', query=query, database_object=self)

        # clear database tool
        database.clear()
        database.disconnect()

        return self

    def update_transaction(self, user_id: int):
        database = Database.database_handler(DatabaseLookups.User)  # create database connection

        # check if database is connected, if not connect
        if database.status is DatabaseStatus.Disconnected:
            database.connect()

        elif database.status is DatabaseStatus.NoImplemented:
            raise DatabaseConnectionError(table='request', query=None, database_object=None)

        # if user_id not populated
        if self.user_id is None:
            self.user_id = user_id

        # construct query for creation
        database.update(self, 'transaction', ('transaction_id',))

        # try to run query
        try:
            self.transaction_id = database.run()
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
                raise DatabaseObjectAlreadyExists(table='user', query=query, database_object=self)

            # some other consistency constraint check
            raise FailedToCreateDatabaseObject(table='user', query=query, database_object=self)

        # clear database tool
        database.clear()
        database.disconnect()

        return self

    def get_transaction(self):
        pass
