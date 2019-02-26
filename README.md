# Health-core

[ ![Download](https://api.bintray.com/packages/leco-libs/health-core/health-core/images/download.svg) ](https://bintray.com/leco-libs/health-core/health-core/_latestVersion)

## About

Health core is a simple health check, designed to validate the dependencies of applications that use javalin or ktor.

## Environments

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

   - Mongo

```bash
$ export NOSQL_DATABASE_HOSTNAME=localhost
$ export NOSQL_DATABASE_NAME=health
$ export NOSQL_DATABASE_USERNAME=sa
$ export NOSQL_DATABASE_PASSWORD=sa
$ export NOSQL_DATABASE_CHECK=true
```
> Cache

   - Redis

```bash
$ export NOSQL_DATABASE_HOSTNAME=localhost:port, localhost:port
$ export CACHE_CHECK=true
```

> if you need disable dependencies, by default is false 

```bash
$ export NOSQL_DATABASE_CHECK=false
$ export NOSQL_DATABASE_CHECK=false
$ export CACHE_CHECK=false

```

## Repository

```bash
repositories {
    flatDir {
            dirs 'libs'
    }
}
``` 


## Dependency

Dependency requirement by `Javalin`:

   * Gradle

```properties
dependencies {
    # Dependency Injection 
    api 'org.koin:koin-core:1.0.2'
    # Api 
    api 'io.javalin:javalin:2.6.0'
    # required by javalin
    compile "org.slf4j:slf4j-simple:1.7.25"
    # required by javalin
    compile 'com.fasterxml.jackson.core:jackson-databind:2.9.8'
    # If you using sql database
    runtime 'org.postgresql:postgresql:42.2.5'
    # If you using mongo
    api 'org.litote.kmongo:kmongo:3.9.2'
}

```

> and add 

Create folder name `libs` and add `health-core-1.0-SNAPSHOT.jar`.
Configure your gradle.properties.

```bash
compile "co.l3co:health-core:1.0-SNAPSHOT"
```

## Setup Javalin + Koin

After you add dependencies requirements.

Sample :
 
`Application.kt`

```kotlin
import co.l3co.health.core.application.controller.HealthControllerJavalin
import io.javalin.Javalin
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class Application(private val port: Int) : KoinComponent {

    private val health by inject<HealthControllerJavalin>()

    fun init(): Javalin {
        val app = Javalin.create().apply {
            port(port)
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        }.start()

        app.routes {
            app.get("/health") { ctx -> health.basic(ctx) }
            app.get("/health/complete") { ctx -> health.complete(ctx) }
        }

        return app
    }
}
```

`Main.kt`

```kotlin
import co.l3co.health.core.application.config.healthModule
import org.koin.standalone.StandAloneContext

fun main() {
    StandAloneContext.startKoin(arrayListOf(healthModule))
    Application(port = 7000).init()
}
```

> output

```bash
 _________________________________________
|        _                  _ _           |
|       | | __ ___   ____ _| (_)_ __      |
|    _  | |/ _` \ \ / / _` | | | '_ \     |
|   | |_| | (_| |\ V / (_| | | | | | |    |
|    \___/ \__,_| \_/ \__,_|_|_|_| |_|    |
|_________________________________________|
|                                         |
|    https://javalin.io/documentation     |
|_________________________________________|
[main] INFO io.javalin.Javalin - Starting Javalin ...
[main] INFO org.eclipse.jetty.server.Server - jetty-9.4.14.v20181114; built: 2018-11-14T21:20:31.478Z; git: c4550056e785fb5665914545889f21dc136ad9e6; jvm 1.8.0_191-b12
[main] INFO org.eclipse.jetty.server.session - DefaultSessionIdManager workerName=node0
[main] INFO org.eclipse.jetty.server.session - No SessionScavenger set, using defaults
[main] INFO org.eclipse.jetty.server.session - node0 Scavenging every 660000ms
[main] INFO org.eclipse.jetty.server.handler.ContextHandler - Started i.j.c.u.initialize$httpHandler$1@6d3a388c{/,null,AVAILABLE}
[main] INFO org.eclipse.jetty.server.handler.ContextHandler - Started o.e.j.s.ServletContextHandler@242b836{/,null,AVAILABLE}
[main] INFO org.eclipse.jetty.server.AbstractConnector - Started ServerConnector@4eeee472{HTTP/1.1,[http/1.1]}{0.0.0.0:7000}
[main] INFO org.eclipse.jetty.server.Server - Started @1040ms
[main] INFO io.javalin.Javalin - Jetty is listening on: [http://localhost:7000/]
[main] INFO io.javalin.Javalin - Javalin has started \o/
```

## Integration Test

Run dependencies on docker

```bash
$ docker run --name health -p 6379:6379 -d redis
$ docker run --name=test-health -d -p 5432:5432  -e POSTGRES_PASSWORD=sa -e POSTGRES_USER=sa -e POSTGRES_DB=health postgres
$ docker run -e MONGO_INITDB_ROOT_USERNAME=sa -e MONGO_INITDB_ROOT_PASSWORD=sa -e MONGO_INITDB_DATABASE=health -d -p 27017:27017 mongo
```

> After

```bash
$ ./gradlew test
```

> Or build 

```bash
$ ./gradlew build
```

## Response

> /health

```json
{
  "status" : true
}
```

> /complete
```json
{
   "database":{
      "name":"POSTGRES",
      "address":"localhost",
      "status":true,
      "elapsed":162,
      "lastRunning":{
         "second":50,
         "nano":84000000,
         "hour":17,
         "minute":53,
         "dayOfYear":42,
         "dayOfWeek":"MONDAY",
         "month":"FEBRUARY",
         "dayOfMonth":11,
         "year":2019,
         "monthValue":2,
         "chronology":{
            "id":"ISO",
            "calendarType":"iso8601"
         }
      }
   },
   "nosql":{
      "name":"mongo",
      "address":"localhost",
      "status":true,
      "elapsed":666,
      "lastRunning":{
         "second":50,
         "nano":751000000,
         "hour":17,
         "minute":53,
         "dayOfYear":42,
         "dayOfWeek":"MONDAY",
         "month":"FEBRUARY",
         "dayOfMonth":11,
         "year":2019,
         "monthValue":2,
         "chronology":{
            "id":"ISO",
            "calendarType":"iso8601"
         }
      }
   }
}
```

## Example

* [Github](https://github.com/leco-poc-projects/javalin-health) 