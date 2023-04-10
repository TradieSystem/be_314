CREATE TABLE service (
     service_id         MEDIUMINT NOT NULL AUTO_INCREMENT,
     service_name       VARCHAR(255) NOT NULL,
     cost               DECIMAL(10,2) NOT NULL,
     retired            DATETIME NOT NULL,
     PRIMARY KEY (service_id)
);

CREATE UNIQUE INDEX uc_service_name ON service(service_name, retired);

