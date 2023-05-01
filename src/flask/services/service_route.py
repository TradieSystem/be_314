from flask import Blueprint
from flask import request
from flask import make_response

from tools.request_construct import Request_Construct
from service.Service_Controller import Service_Controller


# create service routes blueprint
service_blueprint = Blueprint("service_blueprint", __name__)

# service route statics
service_base = "serviceRequest"


# routes
@service_blueprint.get("/{}".format(service_base))
def serviceRequestGet():
    result = Service_Controller.Event_Start(Request_Construct.construct_request(request))
    print(result)
    if result is None:
        return {}

    return result


@service_blueprint.post("/{}".format(service_base))
def serviceRequestCreate():
    result = Service_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@service_blueprint.put("/{}".format(service_base))
def serviceRequestUpdate():
    result = Service_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@service_blueprint.get("/{}/available".format(service_base))
def availableServiceRequest():
    result = Service_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@service_blueprint.post("/{}/application".format(service_base))
def applicationCreate():
    result = Service_Controller.Event_Start(Request_Construct.construct_request(request))

    if result is None:
        return {}

    return result


@service_blueprint.put("/{}/application".format(service_base))
def applicationUpdate():
    result = Service_Controller.Event_Start(Request_Construct.construct_request(request))

    return result


@service_blueprint.get("/{}/review".format(service_base))
def ReviewGet():
    result = Service_Controller.Event_Start(Request_Construct.construct_request(request))
    return result


@service_blueprint.post("/{}/review".format(service_base))
def ReviewCreate():
    result = Service_Controller.Event_Start(Request_Construct.construct_request(request))
    return result
