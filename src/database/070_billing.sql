-- If retired is null then the record is active
CREATE TABLE billing (
     billing_id         MEDIUMINT NOT NULL AUTO_INCREMENT,
     user_id            MEDIUMINT NOT NULL,
     name               VARCHAR(100) NOT NULL,
     card_number        VARCHAR(256) NOT NULL,
     expiry_date        VARCHAR(20) NOT NULL,
     ccv                INT NOT NULL,
     billing_type_id    INT NOT NULL,       
     retired            DATE,
     PRIMARY KEY (billing_id),
     FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE UNIQUE INDEX uc_billing_user_id ON billing(user_id, billing_type_id, retired);