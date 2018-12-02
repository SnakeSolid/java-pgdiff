# PG Diff

Command line utility to compare PostgreSQL tables row by row.

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

 * `-U`, `--user1` - first PostgreSQL user;
 * `-u`, `--user2` - second PostgreSQL user;
 * `-W`, `--password1` - first password;
 * `-w`, `--password2` - second password;
 * `-H`, `--host1` - first server name (default value is localhost);
 * `-h`, `--host2` - second server name (default value is localhost);
 * `-P`, `--port1` - first server port number (default value is 5432);
 * `-p`, `--port2` - second server port number (default value is 5432);
 * `-D`, `--dbname1` - first database name;
 * `-d`, `--dbname2` - second database name;
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

## Build From Source

Building from source require installed JDK and Apache Maven. To build single jar containing all libraries use
following command:

```shell
mvn package assembly:single
```

compiled jar will be placed to target directory with name `pgdiff-${VERSION}-jar-with-dependencies.jar`.
