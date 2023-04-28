from flask import Blueprint
from constants.api_info import api_version


# create service routes blueprint
service_blueprint = Blueprint("service_blueprint", __name__)

# service route statics
service_base = "serviceRequest"


# routes
@service_blueprint.get("/{}/{}".format(api_version, service_base))
def serviceRequestGet():
    return "Get Service Request"


@service_blueprint.post("/{}/{}".format(api_version, service_base))
def serviceRequestCreate():
    return "Create Service Request"


@service_blueprint.put("/{}/{}".format(api_version, service_base))
def serviceRequestUpdate():
    return "Update Service Request"


@service_blueprint.get("/{}/{}/available".format(api_version, service_base))
def availableServiceRequest():
    return "Get Available Service Requests"


@service_blueprint.post("/{}/{}/application".format(api_version, service_base))
def applicationCreate():
    return "Create Application For Service Request"


@service_blueprint.put("/{}/{}/application".format(api_version, service_base))
def applicationUpdate():
    return "Update Application For Service Request"
