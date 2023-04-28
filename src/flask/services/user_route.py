from flask import Blueprint
from flask import request
import json
from tools.request_construct import Request_Construct
from user.User_Controller import User_Controller


# create user routes blueprint
user_blueprint = Blueprint("user_blueprint", __name__)

# user route statics
user_base = "user"


@user_blueprint.get("/{}".format(user_base))
def userGet():
    #user_controller = User_Controller.Event_Start(request.json)
    print(request.path)
    req = Request_Construct.construct_request(request)
    return req


@user_blueprint.post("/{}/userCreate".format(user_base))
def userCreate():
    return "User Create"


@user_blueprint.put("/{}/userUpdate".format(user_base))
def userUpdate():
    return "User Update"
