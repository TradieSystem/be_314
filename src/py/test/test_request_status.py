from util.packager.Decoder import Decoder
from service.Request_Status import Request_Status


def test_get_request_status_by_name():
    request_status = Request_Status.get_request_status_by_name('New')