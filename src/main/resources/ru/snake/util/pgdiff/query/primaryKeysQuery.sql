select
  ccu.column_name as column_name
from information_schema.table_constraints as tc
  inner join information_schema.constraint_column_usage as ccu on (
    ccu.constraint_catalog = tc.constraint_catalog
    and ccu.constraint_schema = tc.constraint_schema
    and ccu.constraint_name = tc.constraint_name
  )
where tc.table_catalog = ?
  and tc.table_schema = ?
  and tc.table_name = ?
  and tc.constraint_type = 'PRIMARY KEY' ;
