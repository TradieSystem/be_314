CREATE TABLE client (
     client_id          MEDIUMINT NOT NULL AUTO_INCREMENT,
     user_id            MEDIUMINT NOT NULL,
     subscription_id    MEDIUMINT,
     PRIMARY KEY (client_id),
     FOREIGN KEY (user_id) REFERENCES user(user_id),
     FOREIGN KEY (subscription_id) REFERENCES subscription(subscription_id)
);
CREATE UNIQUE INDEX ak_client_user_id ON client(user_id);