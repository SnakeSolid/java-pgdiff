select
  t.table_catalog as table_catalog,
  t.table_schema as table_schema,
  t.table_name as table_name
from information_schema.tables as t
where t.table_catalog = ?
  and t.table_schema = ?
  and t.table_name = ?
