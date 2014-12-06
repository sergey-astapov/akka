## Synopsis

This is an application based on AKKA actor model in conjunction with Spark web microframework.

## Code Example

This application processes all requests in reactive manner.

## Motivation

This is a simple POC to study reactive concepts.

## Installation

## API Reference
```
http PUT localhost:8888/facts uid=<uid> type=single data=<some data>
http PUT localhost:8888/facts uid=<uid> type=start data=<some data>
http PUT localhost:8888/facts uid=<uid> type=inner data=<some data>
http PUT localhost:8888/facts uid=<uid> type=stop total=<total number of inner messages> data=<some data>
```

## License

Apache
