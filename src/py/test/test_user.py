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
    assert(original_mobile != updated_user.mobile)
