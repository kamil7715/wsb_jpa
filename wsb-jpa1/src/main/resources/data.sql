-- Insert addresses
INSERT INTO address (id, address_line1, address_line2, city, postal_code) VALUES (1, 'xx', 'yy', 'city', '62-030');
INSERT INTO address (id, address_line1, address_line2, city, postal_code) VALUES (2, '123 Main St', 'Apt 4', 'Springfield', '12345');
INSERT INTO address (id, address_line1, address_line2, city, postal_code) VALUES (3, '456 Elm St', '', 'Shelbyville', '67890');

-- Insert doctors
INSERT INTO doctor (id, first_name, last_name, telephone_number, email, doctor_number, specialization, address_id) VALUES (1, 'John', 'Doe', '555-1234', 'john.doe@example.com', 'D001', 'SURGEON', 2);
INSERT INTO doctor (id, first_name, last_name, telephone_number, email, doctor_number, specialization, address_id) VALUES (2, 'Jane', 'Smith', '555-5678', 'jane.smith@example.com', 'D002', 'GP', 3);

-- Insert patients
INSERT INTO patient (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth, height, address_id) VALUES (1, 'Alice', 'Johnson', '555-8765', 'alice.johnson@example.com', 'P001', '1980-01-01', 1.65, 2);
INSERT INTO patient (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth, height, address_id) VALUES (2, 'Bob', 'Brown', '555-4321', 'bob.brown@example.com', 'P002', '1990-02-02', 1.75, 3);

-- Insert visits
INSERT INTO visit (id, description, time, doctor_id, patient_id) VALUES (1, 'Annual check-up', '2023-01-01T10:00:00', 1, 1);
INSERT INTO visit (id, description, time, doctor_id, patient_id) VALUES (2, 'Follow-up visit', '2023-02-01T11:00:00', 2, 2);

-- Insert medical treatments
INSERT INTO medical_treatment (id, description, type, visit_id) VALUES (1, 'Ultrasound', 'USG', 1);
INSERT INTO medical_treatment (id, description, type, visit_id) VALUES (2, 'Electrocardiogram', 'EKG', 2);