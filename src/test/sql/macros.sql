CREATE TABLE motd (
  id PRIMARY KEY,
  effective_from DATE NOT NULL,
  effective_to DATE
);


-- Scalar Macro

create or replace function is_valid_at (effective_from DATE
                                       ,effective_to DATE
                                       ,DATE effective_date)
  return VARCHAR2 sql_macro(scalar) AS
begin
  return q'{
        effective_from <= effective_date
        AND (effective_to IS NULL
          OR effective_to >= effective_date)}';
end is_valid_at;

SELECT *
  FROM motd
 WHERE is_valid_at(effective_from, effective_to, :effective_date);

-- Table Macro

create or replace function valid_mod (DATE effective_date)
  return VARCHAR2 sql_macro(table) AS
begin
  -- macro of macro
  return q'{
        SELECT *
          FROM motd
         WHERE is_valid_at(effective_from, effective_to, valid_mod.effective_date)}';
end valid_mod;


SELECT *
  FROM valid_mod(:effective_date);

-- Polymorphic Macro


create or replace function valid_table (dbms_tf.table_t t
                                       ,effective_from_column dbms_tf.column_t
                                       ,effective_to_column dbms_tf.column_t)
  return VARCHAR2 sql_macro(table) AS
begin
  -- macro of macro
  return q'{
        SELECT *
          FROM t
         WHERE is_valid_at(effective_from_column, effective_to_column, effective_date)}';
end valid_table;



SELECT *
  FROM valid_table(motd, motd.effective_from, motd.effective_to, :effective_date);



