## Synopsis

This is a simple application based on AKKA/Scalatra to translate and hold unknown words in user profile.

## Code Example

This application processes all requests in reactive manner.

## Motivation

This is a simple POC to study reactive concepts.

## Installation

Application runs standalone as embedded Jetty container.

## Protocol

## API Reference
```
```

## Deploying to Heroku

You need to perform next steps to install application to Heroku:
* start command prompt with ruby
* heroku create
* heroku apps:rename mytranslator
* add Procfile file
* mvn heroku:deploy
* mvn heroku open -a mytranslator

## License

Apache
