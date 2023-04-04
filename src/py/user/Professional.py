"""
py class for professional data structure
"""
from dataclasses import dataclass
from util.database import Database as db, DatabaseLookups as dl
@dataclass
class Professional:
    professional_id: int
    subscription_id: int = None
    user_id: int = None
    def create_professional(self,user_id: int):
        try:
            database = db.Database.database_handler(dl.DatabaseLookups.User.value)  # create database to connect to
            database.database_connect()  # connect to database
            query = "SELECT User_id FROM Project.User WHERE User_id=%d"
            query_data = (user_id)
            validationcheck = database.database_query(query, query_data)
            if not isinstance(validationcheck, int) or validationcheck < 0:
                return "invalid id"
        except Exception as e:
            print("Database Connection Error")
        query = ("INSERT INTO Project.Clients "
                 "(Subscription_id, User_id) "
                 "VALUES (%d, %d)")
        query_data = (self.subscription_id, user_id)
        database.database_query(query, query_data)
        query = "SELECT Client_id FROM Project.Client WHERE User_id=%d AND Billing_Type = %s"
        query_data = (user_id)

        tclient_id = database.database_query(query, query_data)
        if tclient_id is None:
            return "billing creation failed"
        self.clint_id = tclient_id
