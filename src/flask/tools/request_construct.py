"""
py that defines how to construct the request in a fashion that is acceptable by the controller
"""
from flask import Request
from constants.api_info import api_version


class Request_Construct:
    def __init__(self):
        pass

    @staticmethod
    def construct_request(request: Request):
        req = dict()  # create dictionary to return
        if request.data.decode('utf-8') != "":
            req["json-body"] = request.json
        else:
            req["json-body"] = None

        # create context and use logic to determine if additional parameters required
        context = {
            "resource-path": request.path.replace("/{}".format(api_version), ''),
            "http-method": request.method
        }

        # transform query names
        for query in request.args:
            key = query
            if query == 'email':
                key = 'email_address'

            elif query == 'userID':
                key = 'user_id'

            elif query == 'userType':
                key = 'user_type'

            context[key] = request.args[query]

        req["context"] = context

        return req
