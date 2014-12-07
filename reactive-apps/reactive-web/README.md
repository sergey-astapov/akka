## Synopsis

This is an application based on AKKA actor model in conjunction with Spark web microframework.

## Code Example

This application processes all requests in reactive manner.

## Motivation

This is a simple POC to study reactive concepts.

## Installation

Application runs standalone as embedded Jetty container.

## Protocol

Application processes three types of messages:
* Fact - single facts
* ContinuousFact - continuous facts with start, stop and inner facts
* Commands - retrieve states commands

Single Fact:
```java
public class SingleFact implements Fact {
    public final String uid;
    public final String data;
}
```
Continuous Facts:
```java
public class StartFact implements ContinuousFact {
    public final String uid;
    public final String data;
}
public class InnerFact implements ContinuousFact {
    public final String uid;
    public final String data;
}
public class StopFact implements ContinuousFact {
    public final String uid;
    public final String data;
    public final Long total;
}
```
Commands:
```java
public class GetContinuousState {
    public final String uid;

    public static class Result {
        public final Status status;
        public final List<ContinuousFact> facts;
    }
}
```

## API Reference
```
http PUT localhost:8888/facts uid=<uid> type=single data=<some data>
http PUT localhost:8888/facts uid=<uid> type=start data=<some data>
http PUT localhost:8888/facts uid=<uid> type=inner data=<some data>
http PUT localhost:8888/facts uid=<uid> type=stop total=<total number of inner messages> data=<some data>
http GET localhost:8888/facts/<uid>
```

## License

Apache
