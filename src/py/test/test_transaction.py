from util.packager.Decoder import Decoder

with open('test_user_create.json') as tst_usr:
    read_in = tst_usr.read()
    tst_create = Decoder(read_in).deserialize()

with open('test_review_update.json') as tst_usr:
    read_in = tst_usr.read()
    tst_update = Decoder(read_in).deserialize()


def test_create_transaction():
    create_transaction = tst_create.create_transaction()
    assert (create_transaction.user_id != -1)  # i.e. user created no issues


def test_update_transaction():
    update_transaction = tst_create.update_transaction()
    assert (update_transaction.user_id != -1)  # i.e. user created no issues
