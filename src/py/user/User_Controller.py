"""
py that describes the controller for the user sub-package. Enabling User creation, manipulation and
authentication/authorisation
"""
from types import SimpleNamespace
from util.database import Database
import json
from user import User as U


class User_Controller:
    def __init__(self, data: str):
        self.data = data

    def create_user(self, data: str) -> U.User:
        try:
            userdata = json.loads(data)
            userinstance = U.User(-1, userdata['firstname'], userdata['lastname'], userdata['email'], userdata['mobile'], userdata['password'])
            userinstance.create_user()
            if(userinstance.user_id > -1):


        except Exception as e:
            print("User Format Error")

    def update_user(self, data: str) -> U.User:
        pass
