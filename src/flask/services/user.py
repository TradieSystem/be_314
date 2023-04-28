from flask import Blueprint
from constants.api_info import api_version


# create user routes blueprint
user_blueprint = Blueprint("user_blueprint", __name__)

# user route statics
user_base = "user"


@user_blueprint.get("/{}/{}".format(api_version, user_base))
def userGet():
    return "User Get"


@user_blueprint.post("/{}/{}/userCreate".format(api_version, user_base))
def userCreate():
    return "User Create"


@user_blueprint.put("/{}/{}/userUpdate".format(api_version, user_base))
def userUpdate():
    return "User Update"
