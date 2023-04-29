from flask import Blueprint
from flask import request
from tools.request_construct import Request_Construct
from security.Security_Controller import Security_Controller


# create service routes blueprint
service_blueprint = Blueprint("service_blueprint", __name__)

# service route statics
service_base = "serviceRequest"


# routes
@service_blueprint.get("/{}".format(service_base))
def serviceRequestGet():
    result = Security_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@service_blueprint.post("/{}".format(service_base))
def serviceRequestCreate():
    result = Security_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@service_blueprint.put("/{}".format(service_base))
def serviceRequestUpdate():
    result = Security_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@service_blueprint.get("/{}/available".format(service_base))
def availableServiceRequest():
    result = Security_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@service_blueprint.post("/{}/application".format(service_base))
def applicationCreate():
    result = Security_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@service_blueprint.put("/{}/application".format(service_base))
def applicationUpdate():
    result = Security_Controller.Event_Start(Request_Construct.construct_request(request))
    return result
