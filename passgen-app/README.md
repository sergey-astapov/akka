## Synopsis

This is a simple application based on AKKA actor model in conjunction with Spark web microframework which generates passwords to user requests.

## Code Example

This application processes all requests in reactive manner.

## Motivation

This is a simple POC to study reactive concepts.

## Installation

Application runs standalone as embedded Jetty container.

## Protocol

## API Reference
```
http GET localhost:8888/generate length=<pass length>
http GET localhost:8888/generate/<pass length>
```

## License

Apache
