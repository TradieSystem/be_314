from util.packager.Decoder import Decoder
from service.Request import Request

with open('test_request_create.json') as tst_request:
    read_in = tst_request.read()
    tst_create = Decoder(read_in).deserialize()

def test_get_request():
    get_request = Request.get_request(1)
    assert(get_request is not None)
    assert(get_request.request_id > 0)

def test_get_by_client_id():
    get_request = Request.get_request(1)
    assert (get_request is not None)
    assert (get_request.request_id > 0)

def test_get_request_postcode():
    get_requests = Request.get_by_postcode(2602)
    assert (get_requests is not None)

def test_create_request():
    create_request = tst_create.create_request()
    assert(create_request is not None)
    assert(create_request.request_id != -1 and create_request.request_id > 0)

def test_update_request():
    update_request = tst_create.create_request()
    update_request.update_request()
    assert (update_request is not None)
