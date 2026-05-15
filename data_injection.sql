-- Tunisian Data Injection for Student Management System
-- This script populates the database with realistic Tunisian names, modules, and grades.

-- 1. Clear existing data (optional, but good for a fresh start)
DELETE FROM grades;
DELETE FROM modules;
DELETE FROM students;

-- 2. Inject Students
INSERT INTO students (nom, prenom, cin, email, telephone, date_naissance, niveau, filiere, groupe) VALUES
('Ben Ammar', 'Mohamed', '09876543', 'mohamed.benammar@esprit.tn', '55123456', '2001-03-12', 'ING 1', 'Informatique', 'G1'),
('Trabelsi', 'Ines', '11223344', 'ines.trabelsi@enit.tn', '22334455', '2002-07-25', 'ING 1', 'Informatique', 'G1'),
('Mansouri', 'Ahmed', '07654321', 'ahmed.mansouri@supcom.tn', '98765432', '2001-11-05', 'ING 2', 'Télécom', 'G2'),
('Jendoubi', 'Myriam', '12341234', 'myriam.jendoubi@insat.tn', '44556677', '2002-01-15', 'ING 1', 'Informatique', 'G3'),
('Gharbi', 'Yassine', '00112233', 'yassine.gharbi@fst.tn', '50607080', '2000-09-30', 'ING 3', 'Informatique', 'G1'),
('Selmi', 'Sonia', '44556677', 'sonia.selmi@enit.tn', '21222324', '2001-05-14', 'ING 2', 'Génie Civil', 'G2'),
('Kammoun', 'Amine', '88776655', 'amine.kammoun@isg.tn', '99887766', '2002-12-20', 'ING 1', 'Informatique', 'G1'),
('Dridi', 'Fatma', '33445566', 'fatma.dridi@uib.tn', '52334455', '2001-08-08', 'ING 2', 'Informatique', 'G2'),
('Ayari', 'Slim', '66778899', 'slim.ayari@esprit.tn', '20112233', '2000-02-28', 'ING 3', 'Informatique', 'G1'),
('Mejri', 'Nour', '99001122', 'nour.mejri@isg.tn', '58667788', '2002-04-10', 'ING 1', 'Gestion', 'G4'),
('Belhadj', 'Ali', '22330011', 'ali.belhadj@enit.tn', '25667788', '2001-06-22', 'ING 2', 'Informatique', 'G2'),
('Kouki', 'Hana', '55443322', 'hana.kouki@supcom.tn', '94332211', '2002-10-12', 'ING 1', 'Télécom', 'G3'),
('Zaidi', 'Hamza', '11992288', 'hamza.zaidi@insat.tn', '51223344', '2001-01-01', 'ING 2', 'Informatique', 'G1'),
('Haddad', 'Rim', '77881122', 'rim.haddad@fst.tn', '29334455', '2002-09-18', 'ING 1', 'Informatique', 'G5'),
('Ferchichi', 'Khaled', '44119922', 'khaled.ferchichi@esprit.tn', '54667788', '2000-11-30', 'ING 3', 'Informatique', 'G2');

-- 3. Inject Modules
INSERT INTO modules (code, nom, coefficient, enseignant) VALUES
('JAVA101', 'Programmation Java FX', 3.0, 'Prof. Dridi'),
('PYTH202', 'Data Science avec Python', 2.5, 'Mme. Selmi'),
('MATH301', 'Analyse Numérique', 4.0, 'Prof. Ben Ammar'),
('DB404', 'Bases de Données Avancées', 3.0, 'Mr. Mansouri'),
('WEB505', 'Développement Web Moderne', 2.5, 'Mme. Gharbi'),
('SOFT606', 'Soft Skills & Communication', 1.5, 'Mme. Ayari'),
('AI707', 'Intelligence Artificielle', 3.5, 'Prof. Jendoubi'),
('SEC808', 'Cybersécurité', 3.0, 'Mr. Trabelsi');

-- 4. Inject Grades
-- Student 1 (Mohamed Ben Ammar)
INSERT INTO grades (student_id, module_code, note_cc, note_examen) VALUES
(1, 'JAVA101', 14.5, 16.0),
(1, 'MATH301', 12.0, 10.5),
(1, 'DB404', 15.0, 14.0),
(1, 'AI707', 13.5, 15.0);

-- Student 2 (Ines Trabelsi)
INSERT INTO grades (student_id, module_code, note_cc, note_examen) VALUES
(2, 'JAVA101', 16.0, 18.5),
(2, 'WEB505', 17.5, 17.0),
(2, 'SOFT606', 15.0, 16.0);

-- Student 3 (Ahmed Mansouri)
INSERT INTO grades (student_id, module_code, note_cc, note_examen) VALUES
(3, 'PYTH202', 11.0, 12.5),
(3, 'DB404', 13.0, 14.0),
(3, 'SEC808', 10.5, 11.0);

-- Student 4 (Myriam Jendoubi)
INSERT INTO grades (student_id, module_code, note_cc, note_examen) VALUES
(4, 'JAVA101', 18.0, 19.0),
(4, 'MATH301', 15.5, 16.0),
(4, 'AI707', 17.0, 18.0);

-- Student 5 (Yassine Gharbi)
INSERT INTO grades (student_id, module_code, note_cc, note_examen) VALUES
(5, 'SEC808', 14.0, 15.5),
(5, 'AI707', 12.0, 13.0),
(5, 'WEB505', 15.0, 14.5);

-- Add some more random grades for diversity
INSERT INTO grades (student_id, module_code, note_cc, note_examen) VALUES
(6, 'MATH301', 10.0, 11.0),
(7, 'JAVA101', 13.5, 14.0),
(8, 'PYTH202', 15.0, 16.5),
(9, 'SEC808', 12.5, 13.0),
(10, 'SOFT606', 16.0, 17.0),
(11, 'DB404', 14.0, 15.0),
(12, 'WEB505', 13.0, 12.5),
(13, 'AI707', 11.0, 10.5),
(14, 'JAVA101', 15.5, 16.0),
(15, 'MATH301', 9.0, 10.0);
