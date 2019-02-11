# Health-core

> Sql

Database Support

 - PostgreSQL
 - MySQL
 - Oracle
 - SQLite
 - H2
 - SQL Server 

```bash
$ export SQL_DATABASE_DRIVER=org.postgresql.Driver
$ export SQL_DATABASE_URL=jdbc:postgresql://localhost:5432/health
$ export SQL_DATABASE_USERNAME=sa
$ export SQL_DATABASE_PASSWORD=sa
$ export SQL_DATABASE_CHECK=true
```

> NoSql

NoSql Support

Dependency requirement:

   * Gradle

```properties
dependencies {
    implementation 'org.litote.kmongo:kmongo:3.9.2'
}
```
   
   - Mongo

```bash
$ export NOSQL_DATABASE_HOSTNAME=localhost
$ export NOSQL_DATABASE_NAME=health
$ export NOSQL_DATABASE_USERNAME=sa
$ export NOSQL_DATABASE_PASSWORD=sa
$ export NOSQL_DATABASE_CHECK=true
```
