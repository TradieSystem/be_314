"""
Class file for user data type
"""
from dataclasses import dataclass
from user import Professional as P, Client as C, Address as A, Billing as B
from util.database import Database as db, DatabaseLookups as dl

@dataclass
class User:
    user_id: int
    firstname: str = None
    lastname: str = None
    email_address: str = None  # doubles as username
    mobile: str = None
    password: str = None  # will be hashed
    address: A.Address = None
    client: C.Client = None  # possibly null
    professional: P.Professional = None  # possibly null
    outBilling: B.Billing = None
    inBilling: B.Billing = None

    # SQL query to create user in User table
    def create_user(self):
        try:
            database = db.Database.database_handler(dl.DatabaseLookups.User.value)  # create database to connect to
            database.database_connect()  # connect to database
            query = "SELECT User_id FROM Project.User WHERE Email_Address=%s"
            query_data = (self.email_address)
            validationcheck = database.database_query(query, query_data)
            if isinstance(validationcheck, int):
                return "User Already Exists"
        except Exception as e:
            print("Database Connection Error")
        query = ("INSERT INTO Project.User "
                        "(Firstname, Lastname, Email_Address, Mobile, Password) "
                        "VALUES (%s, %s, %s, %s, %s)")
        query_data = (self.firstname, self.lastname, self.email_address, self.mobile, self.password)
        database.database_query(query,query_data)
        query = "SELECT User_id FROM Project.User WHERE Email_Address=%s"
        query_data = (self.email_address)
        tuser_id = database.database_query(query, query_data)
        if not isinstance(tuser_id, int) or tuser_id < 0:
            return "User Creation Failed"
        self.user_id = tuser_id

    # SQL query to update a field in User Table
    def update_detail(self, attribute: str, value):
        pass

    # return user object (possibly in json form)
    def get_user(self):
        pass