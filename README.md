# LifeChoseesU---BackEnd
#### LifeChoosesU servlet Web Application (backend of [LCU Client](https://github.com/Yass-dah/LifeChoosesU)), works with JDBC -> PostgreSQL<br/> 
#### Reminder: change application properties file to your pgadmin ones

<hr/>

Technologies used:
- Java
- Spring
- Maven

<hr/>

SQL template code for database creation:
```
CREATE TABLE countries (
    name VARCHAR(50) PRIMARY KEY,
    flag VARCHAR(2) UNIQUE NOT NULL
);

CREATE TABLE location (
    id SERIAL PRIMARY KEY,
    city VARCHAR(100) NOT NULL,
    country VARCHAR(50),
    CONSTRAINT fk_country
        FOREIGN KEY (country) REFERENCES countries(name)
        ON DELETE SET NULL
);

CREATE TABLE users (
    username VARCHAR(100) PRIMARY KEY,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('RICHIEDENTE', 'MEDIATORE'))
);

CREATE TABLE help_requests (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    location INT NOT NULL,
    description TEXT NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('FAMILIARE', 'SCOLASTICO', 'LAVORATIVO', 'SOCIALE', 'ALTRO')),
    urgency VARCHAR(10) NOT NULL CHECK (urgency IN ('BASSA', 'MEDIA', 'ALTA')),
    status VARCHAR(20) NOT NULL CHECK (status IN ('IN_ATTESA', 'IN_GESTIONE', 'RISOLTO')),
    requester_username VARCHAR(100) NOT NULL,
    mediator_username VARCHAR(100),
    anonymous BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_location
        FOREIGN KEY (location) REFERENCES location(id),

    CONSTRAINT fk_requester
        FOREIGN KEY (requester_username) REFERENCES users(username)
        ON DELETE CASCADE,

    CONSTRAINT fk_mediator
        FOREIGN KEY (mediator_username) REFERENCES users(username)
        ON DELETE SET NULL
);

CREATE TABLE aid_answer (
    id SERIAL PRIMARY KEY,
    answer TEXT NOT NULL,
    request_id INT UNIQUE NOT NULL,
    modified_at TIMESTAMP,

    CONSTRAINT fk_request
        FOREIGN KEY (request_id)
        REFERENCES help_requests(id)
        ON DELETE CASCADE
);

CREATE TABLE assignments (
    id SERIAL PRIMARY KEY,
    mediator_username VARCHAR(100) NOT NULL,
    request_id INT UNIQUE NOT NULL,
    type VARCHAR(30) NOT NULL CHECK (type IN ('PRESA_CARICO', 'RISOLTO')),
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_mediator
        FOREIGN KEY (mediator_username) REFERENCES users(username)
        ON DELETE CASCADE,

    CONSTRAINT fk_request
        FOREIGN KEY (request_id)
        REFERENCES help_requests(id)
        ON DELETE CASCADE
)
```
