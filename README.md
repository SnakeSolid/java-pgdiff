# PG Diff

Command-line utility to compare PostgreSQL tables row by row.

## Usage Example

Compare two tables using environment variables:

```shell
!/usr/bin/env bash

export PGDIFF_USER1="developer"
export PGDIFF_USER2="reader"
export PGDIFF_PASSWORD1="password"
export PGDIFF_PASSWORD2="password"
export PGDIFF_HOST1="testing"
export PGDIFF_HOST2="staging"
export PGDIFF_DBNAME1="my_database"
export PGDIFF_DBNAME2="my_database"

java -jar target/pgdiff-0.0.1-SNAPSHOT-jar-with-dependencies.jar my.table
```

Compare table using command-line options:

```shell
!/usr/bin/env bash

java -jar target/pgdiff-0.0.1-SNAPSHOT-jar-with-dependencies.jar \
	-U "developer" \
	-W "password" \
	-H "testing" \
	-D "my_database" \
	-u "reader" \
	-w "password" \
	-h "staging" \
	-d "my_database" \
	my.table my.second_table my.third_table
```

Both ways to set connection parameters can be combined. List of table names can be set only as command-line parameter.

## Options

Supported command-line options:

 * `-u`, `--user1` - first PostgreSQL user;
 * `-U`, `--user2` - second PostgreSQL user;
 * `-w`, `--password1` - first password;
 * `-W`, `--password2` - second password;
 * `-h`, `--host1` - first server name (default value is localhost);
 * `-H`, `--host2` - second server name (default value is localhost);
 * `-p`, `--port1` - first server port number (default value is 5432);
 * `-P`, `--port2` - second server port number (default value is 5432);
 * `-d`, `--dbname1` - first database name;
 * `-D`, `--dbname2` - second database name;
 * `-c`, `--config` - path to configuration file.
 * `-h`, `--help` - show help and exit.

Supported environment variables:

 * `PGDIFF_USER1` - first PostgreSQL user;
 * `PGDIFF_USER2` - second PostgreSQL user;
 * `PGDIFF_PASSWORD1` - first password;
 * `PGDIFF_PASSWORD2` - second password;
 * `PGDIFF_HOST1` - first server name (default value is localhost);
 * `PGDIFF_HOST2` - second server name (default value is localhost);
 * `PGDIFF_PORT1` - first server port number (default value is 5432);
 * `PGDIFF_PORT2` - second server port number (default value is 5432);
 * `PGDIFF_DBNAME1` - first database name;
 * `PGDIFF_DBNAME2` - second database name;

Command-line options have priority over environment variables. Default values used only if environment variable and
command-line option are empty.

## Configuration

Configuration file can be set using `-c` or `--config` command-line options. Configuration file can override default
behavior for display and compare table fields.

Configuration file example:

```yaml
---
outputType: TABLE # type of output - DIFF = diff like, TABLE = table like as psql. Default value - TABLE.
delimiter: " | " # delimiter for DIFF output, by default " | "
bufferSize: 10 # buffer size for TABLE output, used to calculate column widths

tables:
  my_schema.good_table: # this settings will be used only for table my_schema.good_table.
    display: # these fields will be shown in diff output.
      - id
      - name
      - description
    compare: # these fields will be used to compare different table rows.
      - name
      - description
      - index
      - is_enabled

  small_table: # this settings will be used for table small_table in any schema.
    display:
      - id
      - name
    compare:
      - name
      - index
```

Display fields by default contains all columns except for binary.

Compare fields by default contains all columns except for primary key.

## Build From Source

Building from source require installed JDK and Apache Maven. To build single jar containing all libraries use
following command:

```shell
mvn package assembly:single
```

compiled jar will be placed to target directory with name `pgdiff-${VERSION}-jar-with-dependencies.jar`.
