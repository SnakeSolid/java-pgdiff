select
  c.column_name as column_name,
  c.data_type as data_type,
  c.is_nullable as is_nullable
from information_schema.columns as c
where c.table_catalog = ?
  and c.table_schema = ?
  and c.table_name = ?
order by c.ordinal_position
