from flask import Blueprint
from flask import request
import json
from tools.request_construct import Request_Construct
from user.User_Controller import User_Controller
from security.Security_Controller import Security_Controller


# create user routes blueprint
user_blueprint = Blueprint("user_blueprint", __name__)

# user route statics
user_base = "user"


@user_blueprint.get("/{}".format(user_base))
def userGet():
    user_controller = User_Controller.Event_Start(Request_Construct.construct_request(request))
    return user_controller


@user_blueprint.get("/{}/validate".format(user_base))
def userValidate():
    user_controller = User_Controller.Event_Start(Request_Construct.construct_request(request))
    return user_controller


@user_blueprint.get("/{}/login".format(user_base))
def userLogin():
    security_controller = Security_Controller.Event_Start(Request_Construct.construct_request(request))
    return security_controller


@user_blueprint.get("/{}/resetPassword".format(user_base))
def userResetPassword():
    security_controller = Security_Controller.Event_Start(Request_Construct.construct_request(request))
    return security_controller


@user_blueprint.post("/{}/userCreate".format(user_base))
def userCreate():
    user_controller = User_Controller.Event_Start(Request_Construct.construct_request(request))
    print("Result {}".format(user_controller))
    return user_controller


@user_blueprint.put("/{}/updateUser".format(user_base))
def userUpdate():
    user_controller = User_Controller.Event_Start(Request_Construct.construct_request(request))
    return user_controller
