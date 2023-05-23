import pytest

from util.handling.errors.database.DatabaseObjectAlreadyExists import DatabaseObjectAlreadyExists
from util.handling.errors.database.FailedToCreateDatabaseObject import FailedToCreateDatabaseObject
from util.packager.Encoder import Encoder
from util.packager.Decoder import Decoder
from user.User import User

#  read in test data
with open('test_user_create.json') as tst_usr:
    read_in = tst_usr.read()
    tst_create = Decoder(read_in).deserialize()
    print(tst_create)

with open('test_user_update.json') as tst_usr:
    read_in = tst_usr.read()
    tst_update = Decoder(read_in).deserialize()
    print(tst_update)


# global user_id for rest of tests

global_user_id = -1
def test_get_user():
    get_user = User.get_user(1)
    assert(get_user is not None)

def test_create_user():
    created_user = tst_create.create_user()
    assert(created_user.user_id != -1)  # i.e. user created no issues

#  test of non nested values
def test_update_user():
    to_update = User.get_user(1)
    original_mobile = to_update.mobile
    to_update.mobile = '9999999999'
    updated_user = to_update.update_user()
    update_mobile = updated_user.mobile
    assert(original_mobile != update_mobile)

# test should fail as user already exists
def test_duplicate_user():
    try:
        create_user = tst_create.create_user()
        assert False
    except FailedToCreateDatabaseObject as ftcdo:
        assert True

# should return None
def test_update_user_not_exists():
    to_update = tst_update
    to_update.user_id = 999
    updated_user = to_update.update_user()
    assert(updated_user is None)

# should throw error as user should not be created without security questions
def test_empty_security_questions():
    try:
        to_create = tst_create.create_user()
        to_create.security_questions = None
        created_user = to_create.create_user()
        assert False
    except FailedToCreateDatabaseObject as e:
        assert True
