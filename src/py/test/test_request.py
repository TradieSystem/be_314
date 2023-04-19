import json
import unittest

from service.Request import Request
from service.Request_Bid import Request_Bid
from util.handling.Result_Handler import Result_Handler
from util.packager.Encoder import Encoder


class MyTestCase(unittest.TestCase):
    def test_create_request(self):
        request = Request(request_date='10/12/2023', service_id=1, postcode='2570',
                          request_status_id=1, instruction="Test", client_id=1)

        print(request.create_request())

    def test_get_request(self):
        print(Request.get_request(41))

    def test_get_by_client_id(self):
        requests = Request.get_client_requests(1)
        encoded = Encoder(requests).serialize()
        print(json.loads(Encoder(requests).serialize().decode('utf-8')))

    def test_get_request_postcode(self):
        print(Request.get_by_postcode(2570))

    def test_request_bid(self):
        print(Request_Bid.get_request_bid(6))


if __name__ == '__main__':
    unittest.main()
