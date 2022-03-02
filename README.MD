# A Maven project to test `logback` logging functionalities.

## Currently implemented:

- logging to console;
- logging to a log file:
  - log files saved in `./log`,
  - logs cannot exceed 3Gb,
  - logs cannot be older than 1 month,
  - a log file cannot exceed 3Mb,
  - a new file is created every day;
- different logging configurations for `main` and `test`.

## In progress:
- dynamic logging level adjustment
- pop-up appender


