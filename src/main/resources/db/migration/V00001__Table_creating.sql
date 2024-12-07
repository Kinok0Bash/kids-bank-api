CREATE TABLE Enclosures (
    enclosure_id SERIAL PRIMARY KEY,
    size NUMERIC(10, 2) NOT NULL,
    temperature NUMERIC(5, 2) NOT NULL,
    description TEXT
);

CREATE TABLE Animals (
    animal_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    species VARCHAR(100) NOT NULL,
    date_of_birth DATE,
    arrival_date DATE,
    origin VARCHAR(255),
    enclosure_id INT,
    FOREIGN KEY (enclosure_id) REFERENCES Enclosures(enclosure_id)
);

CREATE TABLE Staff (
    staff_id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    position VARCHAR(100),
    assigned_enclosure_id INT,
    FOREIGN KEY (assigned_enclosure_id) REFERENCES Enclosures(enclosure_id)
);

CREATE TABLE AnimalDiets (
    diet_id SERIAL PRIMARY KEY,
    animal_id INT,
    diet_type TEXT,
    FOREIGN KEY (animal_id) REFERENCES Animals(animal_id)
);

CREATE TABLE Visitors (
    visitor_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    visit_date DATE NOT NULL,
    feedback TEXT
);

CREATE TABLE Medical_checkups (
    checkup_id SERIAL PRIMARY KEY,
    animal_id INT,
    staff_id INT,
    checkup_date DATE NOT NULL,
    diagnosis TEXT,
    treatment TEXT,
    FOREIGN KEY (animal_id) REFERENCES Animals(animal_id),
    FOREIGN KEY (staff_id) REFERENCES Staff(staff_id)
);

CREATE TABLE Tickets (
    ticket_id SERIAL PRIMARY KEY,
    visitor_id INT,
    ticket_type VARCHAR(50) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    purchase_date DATE NOT NULL,
    FOREIGN KEY (visitor_id) REFERENCES Visitors(visitor_id)
);

CREATE TABLE Users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    password TEXT NOT NULL
);