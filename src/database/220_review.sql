CREATE TABLE review (
    review_id       MEDIUMINT NOT NULL AUTO_INCREMENT,
    request_id      MEDIUMINT NOT NULL,
    rating          DECIMAL(10,2) NOT NULL,
    comment         VARCHAR(4000),
    PRIMARY KEY (review_id),
    FOREIGN KEY (request_id) REFERENCES request(request_id)
);