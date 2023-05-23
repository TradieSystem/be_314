from util.packager.Decoder import Decoder
from service.Associated_Service import Associated_Service

def test_create_associated_service():
    associated_service_test = Associated_Service(service_id=1, professional_id=1)
    created_associated_service = associated_service_test.create_associated_service(1)
    assert(created_associated_service is not None)

def test_no_professional_id():
    associated_service_test = Associated_Service(service_id=1, professional_id=None)
    created_associated_service = associated_service_test.create_associated_service(1)
    assert(created_associated_service is not None)
