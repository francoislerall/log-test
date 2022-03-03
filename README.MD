# A Maven project to test `logback` logging functionalities.

##Tasks

### Currently implemented:

- logging to console;
- logging to a log file:
  - log files saved in `./log`,
  - logs cannot exceed 3Gb,
  - logs cannot be older than 1 month,
  - a log file cannot exceed 3Mb,
  - a new file is created every day;
- different logging configurations for `main` and `test`.
- pop-up appender

### In progress:
- dynamic logging level adjustment

## Migration to `logback`

- `logback` uses the same facade as log4j, namely `slf4j`. Thus, to my knowledge, there is no changes to be made in scripts using `slf4j` to log.
- `slf4j` is a dependency of `logback`, thus it is only needed to import `logback` in `pom.xml`.
- `logback` configuration is made in `logback.xml` for main scripts (respectively `logback-test.xml` for tests):
  - [`log4j.properties`](https://github.com/GreenDelta/olca-modules/blob/master/olca-core/src/test/resources/log4j.properties) is to be deleted.
  - rolling html log files policy is defined in the `logback.xml` and `logback-test.xml` files.
- Addition of an extended appender for the logs popped up in a window.
- Refactoring of [`LoggerConfig.setUp`](https://github.com/GreenDelta/olca-app/blob/d62f84cd8964355c7dd944b65384fca6a959b9d9/olca-app/src/org/openlca/app/logging/LoggerConfig.java#L24) to ensure that the log level can be set in the app properties.

