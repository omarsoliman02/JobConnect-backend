-- ============================================
-- Mock Data for Job Portal
-- ============================================

-- 1. Companies
INSERT INTO companies (name) VALUES
  ('Acme Corp'),
  ('Tech Solutions'),
  ('EduSoft'),
  ('HealthPlus'),
  ('Green Energy Inc');

-- 2. Skills
INSERT INTO skills (name) VALUES
  ('Java'),
  ('Spring Boot'),
  ('Angular'),
  ('React'),
  ('SQL'),
  ('Python'),
  ('AWS'),
  ('Docker');

-- 3. Applicants
INSERT INTO applicants (first_name, last_name, email, phone) VALUES
  ('Alice',   'Smith',    'alice@example.com',    '0100000001'),
  ('Bob',     'Johnson',  'bob@example.com',      '0100000002'),
  ('Carol',   'Williams', 'carol@example.com',    '0100000003'),
  ('David',   'Brown',    'david@example.com',    '0100000004'),
  ('Eve',     'Jones',    'eve@example.com',      '0100000005'),
  ('Frank',   'Miller',   'frank@example.com',    '0100000006'),
  ('Grace',   'Davis',    'grace@example.com',    '0100000007'),
  ('Hank',    'Garcia',   'hank@example.com',     '0100000008'),
  ('Ivy',     'Rodriguez','ivy@example.com',      '0100000009'),
  ('Juliet',  'Stewart',  'juliet@example.com',   '0100000010');

-- 4. Experiences (one per applicant)
INSERT INTO experiences (company_name, role, start_date, end_date, applicant_id) VALUES
  ('Globex Inc',    'Intern',        '2022-06-01', '2022-08-31', 1),
  ('Initech',       'Junior Dev',    '2023-01-01', '2023-12-31', 2),
  ('Umbrella Corp', 'Analyst',       '2021-09-01', '2022-05-30', 3),
  ('Stark Labs',    'Researcher',    '2020-03-01', '2021-02-28', 4),
  ('Wayne Ent.',    'Consultant',    '2022-11-01', '2023-04-30', 5),
  ('Wonka Tech',    'Engineer',      '2021-07-01', '2022-06-30', 6),
  ('Cyberdyne',     'Developer',     '2023-02-01', '2024-01-31', 7),
  ('Soylent Corp',  'Support',       '2020-01-01', '2020-12-31', 8),
  ('Tyrell Corp',   'Tester',        '2022-04-01', '2023-03-31', 9),
  ('Vandelay Ind.', 'Sales Rep',     '2021-05-01', '2022-04-30',10);

-- 5. Jobs (two per company)
INSERT INTO jobs (title, description, location, job_type, min_salary, max_salary, experience_level, company_id) VALUES
  ('Backend Engineer',    'Build REST APIs',                   'Paris',     'FULL_TIME', 50000, 70000, 'SENIOR',       1),
  ('Fullstack Developer', 'Java + Angular features',          'Lyon',      'FULL_TIME', 45000, 65000, 'INTERMEDIATE', 1),
  ('DevOps Engineer',     'Manage CI/CD & Docker',             'Marseille', 'CONTRACT',  60000, 80000, 'SENIOR',       2),
  ('Frontend Developer',  'React single-page apps',            'Nice',      'FULL_TIME', 40000, 55000, 'INTERMEDIATE', 2),
  ('Data Analyst',        'SQL queries & dashboards',          'Bordeaux',  'PART_TIME', 30000, 45000, 'ENTRY_LEVEL',  3),
  ('E-Learning Specialist','Course content dev',               'Toulouse',  'FREELANCE', 25000, 40000, 'INTERMEDIATE', 3),
  ('QA Engineer',         'Automated tests',                   'Lille',     'FULL_TIME', 35000, 50000, 'ENTRY_LEVEL',  4),
  ('Product Manager',     'Lead health app roadmap',           'Strasbourg','FULL_TIME', 70000, 90000, 'EXECUTIVE',    4),
  ('Cloud Architect',     'Design AWS infrastructure',         'Rennes',    'CONTRACT',  65000, 85000, 'SENIOR',       5),
  ('Sustainability Lead', 'Green energy projects',             'Nantes',    'FULL_TIME', 55000, 75000, 'SENIOR',       5);

-- 6. Applications (each of 10 applicants applies to 2 different jobs)
INSERT INTO applications (applicant_id, job_id, applied_at) VALUES
  (1, 1, NOW()), (1, 3, NOW()),
  (2, 2, NOW()), (2, 4, NOW()),
  (3, 5, NOW()), (3, 6, NOW()),
  (4, 7, NOW()), (4, 8, NOW()),
  (5, 1, NOW()), (5,10, NOW()),
  (6, 3, NOW()), (6, 9, NOW()),
  (7, 2, NOW()), (7,10, NOW()),
  (8, 4, NOW()), (8, 5, NOW()),
  (9, 6, NOW()), (9, 7, NOW()),
  (10,8, NOW()), (10, 9, NOW());

-- 7. Applicant–Skill relationships
INSERT INTO applicant_skill (applicant_id, skill_id) VALUES
  (1, 1), (1, 2),    -- Alice: Java, Spring Boot
  (2, 3), (2, 4),    -- Bob: Angular, React
  (3, 5), (3, 8),    -- Carol: SQL, Docker
  (4, 6), (4, 7),    -- David: Python, AWS
  (5, 1), (5, 6),    -- Eve: Java, Python
  (6, 2), (6, 5),    -- Frank: Spring Boot, SQL
  (7, 3), (7, 7),    -- Grace: Angular, AWS
  (8, 4), (8, 8),    -- Hank: React, Docker
  (9, 1), (9, 5),    -- Ivy: Java, SQL
  (10,2), (10,6);    -- Juliet: Spring Boot, Python
