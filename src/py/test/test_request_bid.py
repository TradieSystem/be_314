from util.packager.Decoder import Decoder
from service.Request_Bid import Request_Bid

with open('test_request_bid_create.json') as tst_usr:
    read_in = tst_usr.read()
    tst_create = Decoder(read_in).deserialize()

with open('test_request_bid_update.json') as tst_usr:
    read_in = tst_usr.read()
    tst_update = Decoder(read_in).deserialize()

def test_get_request_bid():
    get_request_bid = Request_Bid.get_request_bid(1)
    assert(get_request_bid is not None)

def test_get_by_request_id():
    get_request_bid =Request_Bid.get_by_request_id(1)
    assert(get_request_bid is not None)
