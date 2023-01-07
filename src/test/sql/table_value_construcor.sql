SELECT 'PL', 'Project Manager'
  FROM dual
UNION ALL
SELECT 'RE', 'Requirement Engineer'
  FROM dual
UNION ALL
SELECT 'RTE', 'Release Train Engineer'
  FROM dual
UNION ALL
SELECT 'CSE', 'Customer Success Executive'
  FROM dual;


SELECT *
  FROM (VALUES
        ('PL', 'Project Manager'),
        ('RE', 'Requirement Engineer'),
        ('RTE', 'Release Train Engineer'),
        ('CSE', 'Customer Success Executive'));

