UPDATE emp e
   SET e.salary = e.salary * 2
  FROM dept d
 WHERE e.dept_id = d.dept_id
   AND d.name = 'Development';


MERGE INTO emp e
     USING (SELECT dept_id
	      FROM dept
	     WHERE name = 'Development') d
        ON (e.dept_id = d.dept_id)
WHEN MATCHED THEN
   UPDATE
      SET e.salary = e.salary * 2;


