"""
py class for credit card data structure
"""
from datetime import datetime
from util.database import Database as db, DatabaseLookups as dl

class Billing:
    billing_id: int
    name: str = None
    card_number: int = None
    expiry_date: datetime = None
    cvv: int = None
    billing_type: str = None

    def create_billing(self,user_id: int):
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
        query = ("INSERT INTO Project.Billing "
                 "(Name, Card_Number, Expiry_Date, CCV, Billing_Type, User_id) "
                 "VALUES (%s, %s, %s, %s, %d, %d)")
        query_data = (self.name, self.card_number, self.expiry_date, self.cvv, self.password)
        database.database_query(query, query_data)
        query = "SELECT Billing_id FROM Project.Billing WHERE User_id=%d AND Billing_Type = %s"
        query_data = (self.email_address)

        tbill_id = database.database_query(query, query_data)
        if tbill_id is None:
            return "billing creation failed"
        self.billing_id = tbill_id
