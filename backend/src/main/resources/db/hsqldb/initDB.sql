DROP TABLE visits IF EXISTS CASCADE;
DROP TABLE specialties IF EXISTS CASCADE;
DROP TABLE vet_specialties IF EXISTS CASCADE;
DROP TABLE vets IF EXISTS CASCADE;
DROP TABLE pets IF EXISTS CASCADE;
DROP TABLE types IF EXISTS CASCADE;
DROP TABLE owners IF EXISTS CASCADE;
DROP TABLE roles IF EXISTS CASCADE;
DROP TABLE users IF EXISTS CASCADE;

CREATE TABLE users
(
    username VARCHAR(20)          NOT NULL,
    password VARCHAR(20)          NOT NULL,
    enabled  BOOLEAN DEFAULT TRUE NOT NULL,
    fullname VARCHAR(256)         NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE roles
(
    id       INTEGER IDENTITY PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    role     VARCHAR(20) NOT NULL
);
ALTER TABLE roles
    ADD CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username);
CREATE INDEX fk_username_idx ON roles (username);


CREATE TABLE vets
(
    id         INTEGER IDENTITY PRIMARY KEY,
    first_name VARCHAR(30),
    last_name  VARCHAR(30)
);
CREATE INDEX vets_last_name ON vets (last_name);

CREATE TABLE specialties
(
    id   INTEGER IDENTITY PRIMARY KEY,
    name VARCHAR(80)
);
CREATE INDEX specialties_name ON specialties (name);

CREATE TABLE vet_specialties
(
    vet_id       INTEGER NOT NULL,
    specialty_id INTEGER NOT NULL
);
ALTER TABLE vet_specialties
    ADD CONSTRAINT fk_vet_specialties_vets FOREIGN KEY (vet_id) REFERENCES vets (id);
ALTER TABLE vet_specialties
    ADD CONSTRAINT fk_vet_specialties_specialties FOREIGN KEY (specialty_id) REFERENCES specialties (id);

CREATE TABLE types
(
    id   INTEGER IDENTITY PRIMARY KEY,
    name VARCHAR(80)
);
CREATE INDEX types_name ON types (name);

CREATE TABLE owners
(
    id         INTEGER IDENTITY PRIMARY KEY,
    first_name VARCHAR(30),
    last_name  VARCHAR_IGNORECASE(30),
    address    VARCHAR(255),
    city       VARCHAR(255),
    telephone  VARCHAR(128)
);
CREATE INDEX owners_last_name ON owners (last_name);

CREATE TABLE pets
(
    id         INTEGER IDENTITY PRIMARY KEY,
    name       VARCHAR(30),
    birth_date DATE,
    type_id    INTEGER NOT NULL,
    owner_id   INTEGER NOT NULL
);
ALTER TABLE pets
    ADD CONSTRAINT fk_pets_owners FOREIGN KEY (owner_id) REFERENCES owners (id);
ALTER TABLE pets
    ADD CONSTRAINT fk_pets_types FOREIGN KEY (type_id) REFERENCES types (id);
CREATE INDEX pets_name ON pets (name);

CREATE TABLE visits
(
    id          INTEGER IDENTITY PRIMARY KEY,
    pet_id      INTEGER NOT NULL,
    vet_id      INTEGER,
    visit_date  DATE,
    description VARCHAR(255)
);
ALTER TABLE visits
    ADD CONSTRAINT fk_visits_pets FOREIGN KEY (pet_id) REFERENCES pets (id);
ALTER TABLE visits
    ADD CONSTRAINT fk_visits_vets FOREIGN KEY (vet_id) REFERENCES vets (id);
CREATE INDEX visits_pet_id ON visits (pet_id);
