from flask import Blueprint
from flask import request
import json

from flask_cors import cross_origin

from tools.request_construct import Request_Construct
from user.User_Controller import User_Controller
from security.Security_Controller import Security_Controller


# create user routes blueprint
user_blueprint = Blueprint("user_blueprint", __name__)

# user route statics
user_base = "user"


@user_blueprint.get("/{}/userGet".format(user_base))
def userGet():
    print(Request_Construct.construct_request(request))
    result = User_Controller.Event_Start(Request_Construct.construct_request(request))
    if result is None:
        return {}
    else:
        return result


@user_blueprint.get("/{}/validate".format(user_base))
def userValidate():
    result = User_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@user_blueprint.post("/{}/login".format(user_base))
def userLogin():
    result = Security_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@user_blueprint.get("/{}/resetPassword".format(user_base))
def userResetPasswordGet():
    result = Security_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@user_blueprint.post("/{}/resetPassword".format(user_base))
def userResetPasswordPost():
    result = Security_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@user_blueprint.put("/{}/resetPassword".format(user_base))
def userResetPasswordPut():
    result = Security_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@user_blueprint.post("/{}/userCreate".format(user_base))
def userCreate():
    result = User_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@user_blueprint.put("/{}/updateUser".format(user_base))
def userUpdate():
    result = User_Controller.Event_Start(Request_Construct.construct_request(request))
    return result
