"""
main flask file
"""
from flask import Flask
from flask_cors import CORS
from constants.api_info import api_version
from services.user_route import user_blueprint as ub
from services.service_route import service_blueprint as sb

# create flask instance
api = Flask(__name__)
CORS(api)
api.register_blueprint(ub, url_prefix="/{}".format(api_version))
api.register_blueprint(sb, url_prefix="/{}".format(api_version))


if __name__ == "__main__":
    api.run(debug=True)
