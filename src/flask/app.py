"""
main flask file
"""
from flask import Flask
from services.user import user_blueprint as ub
from services.service import service_blueprint as sb

# create flask instance
api = Flask(__name__)
api.register_blueprint(ub)
api.register_blueprint(sb)

if __name__ == "__main__":
    api.run(debug=True)
