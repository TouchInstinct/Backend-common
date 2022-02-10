# Workers Agent

Framework for clustered scheduling based on Quartz and Spring Data.
Stores all data in a database (Postgres by default).

Used as manageable alternative for Spring's `@Scheduled`.

## How to
1.  Add dependency
    ```
    implementation project(":spring-workers-manager-agent")
    ```
1.  Define a job by annotations to be scheduled in any component.
    ```
    package ru.touchin
    
    @Component
    class MyJob {
    
      @ScheduledAction
      @InitTrigger(type = "CRON", expression = "0 15 * * * ?")
      fun sayHello(){
        println("Hello, world!")
      }
    
    }
    ```
    
1.  Enable job in `application.properties`
    
    ```
    workers.names=ru.touchin.MyJob
    ```
    or:
    ```
    workers.names=*    
    ```

1. Start the application.

## Annotations
### @ScheduledAction
Registers method as action of some job.

Parameters:
- `name` - name of job. Defaults to class full name.
    Must be unique in application scope.

### @Trigger
Declares default trigger for the job.
Default triggers are created when launching job first time.

Parameters:
-  `name` - Optional name for trigger. Defaults to some (maybe random) string.
    Name must be unique in scope of corresponding job.
-  `type` - Trigger type. See triggers types. 
    SpEL expressions are supported like in `@Scheduled` annotation of Spring.
-  `expression` - The value for trigger.
    SpEL expressions are supported like in `@Scheduled` annotation of Spring.

## Configuration
### Enabling workers

Agent ignores workers by default. To enable worker add its name to `worker.names` property.
Example:
```
worker.names=com.eample.Job1,\
  com.example.Job2
```

#### Patterns for names
`workers.names` support Glob-like patterns. 
- Asterisk (`*`) symbol is for "zero or more any symbols" (as `.*` in regex)
- Question mark (`?`) is for "any single symbol" (as `.` in regex) 

## TODO
- External data source, unrelated to application code. 
