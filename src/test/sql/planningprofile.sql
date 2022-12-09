CREATE TABLE planning_profile (
  id PRIMARY KEY,
  name VARCHAR2(32) NOT NULL,
  description VARCHAR2(255),
  active BOOLEAN NOT NULL
);

INSERT INTO planning_profile (name, description,       active)
     VALUES                  ('PL', 'Project Manager', true);
