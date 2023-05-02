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
    review_id: int = None
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
        database.insert(self, 'review', ('review_id',))

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
    def get_review(review_id: int):
        # create database session
        database = Database.database_handler(DatabaseLookups.User)

        # check if session is connected
        if database.status is not DatabaseStatus.Connected:
            database.connect()

        elif database.status is DatabaseStatus.NoImplemented:
            raise DatabaseConnectionError(table='review', query=None, database_object=None)

        # create query for database connection
        database.select(('review_id', 'rating', 'comment', 'request_id'), 'review')
        database.where('review_id = %s', review_id)

        # try to run query
        results = database.run()

        # if nothing is found return error
        review = Review(review_id=results[0][0], rating=results[0][1], comment=results[0][2], request_id=results[0][3])

        return review

    @staticmethod
    def get_by_request(request_id: int):
        # create database session
        database = Database.database_handler(DatabaseLookups.User)

        # check if session is connected
        if database.status is not DatabaseStatus.Connected:
            database.connect()

        elif database.status is DatabaseStatus.NoImplemented:
            raise DatabaseConnectionError(table='review', query=None, database_object=None)

        # create query for database connection
        database.select(('review_id', 'rating', 'comment', 'request_id'), 'review')
        database.where('request_id = %s', request_id)

        # try to run query
        results = database.run()

        # if nothing is found return None
        if len(results) == 0:
            return None

        review = Review(review_id=results[0][0], rating=results[0][1], comment=results[0][2], request_id=results[0][3])

        return review

    @staticmethod
    def ToAPI(obj):
        if isinstance(obj, Review):
            remap = {
                "reviewID": obj.review_id,
                "rating": float(obj.rating.__str__()),
                "review": obj.comment,
                "request_id": obj.request_id
            }

            return remap

        raise TypeError

    @staticmethod
    def FromAPI(obj):
        # get request status ids
        return Review(review_id=obj.get('reviewID'), rating=obj.get('rating'), comment=obj.get('review'),
                      request_id=obj.get('request_id'))
