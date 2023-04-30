from flask import Blueprint
from flask import request
from flask_cors import cross_origin

from tools.request_construct import Request_Construct
from service.Service_Controller import Service_Controller


# create service routes blueprint
service_blueprint = Blueprint("service_blueprint", __name__)

# service route statics
service_base = "serviceRequest"


# routes
@service_blueprint.get("/{}".format(service_base))
@cross_origin()
def serviceRequestGet():
    result = Service_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@service_blueprint.post("/{}".format(service_base))
@cross_origin()
def serviceRequestCreate():
    result = Service_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@service_blueprint.put("/{}".format(service_base))
@cross_origin()
def serviceRequestUpdate():
    result = Service_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@service_blueprint.get("/{}/available".format(service_base))
@cross_origin()
def availableServiceRequest():
    result = Service_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@service_blueprint.post("/{}/application".format(service_base))
@cross_origin()
def applicationCreate():
    result = Service_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@service_blueprint.put("/{}/application".format(service_base))
@cross_origin()
def applicationUpdate():
    result = Service_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@service_blueprint.post("/{}/review".format(service_base))
@cross_origin()
def createReview():
    result = Service_Controller.Event_Start(Request_Construct.construct_request(request))
    return result
