from util.packager.Decoder import Decoder
from service.Review import Review

with open('test_review_create.json') as tst_usr:
    read_in = tst_usr.read()
    tst_create = Decoder(read_in).deserialize()

with open('test_review_update.json') as tst_usr:
    read_in = tst_usr.read()
    tst_update = Decoder(read_in).deserialize()
    print(tst_update)


def test_create_review():
    created_review = tst_create.create_review()
    assert (created_review.user_id != -1)  # i.e. user created no issues


def test_update_review():
    created_review = tst_update.update_review()
    assert (created_review.user_id != -1)  # i.e. user created no issues
