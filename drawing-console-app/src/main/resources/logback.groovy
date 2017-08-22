import ch.qos.logback.classic.encoder.PatternLayoutEncoder

import static ch.qos.logback.classic.Level.DEBUG

appender("FILE", RollingFileAppender) {
    file = "logs/drawing-console.log"
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "logs/drawing-console.log.%d{yyyy-MM-dd}"
        maxHistory = 3
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{5} - %msg%n"
    }
}

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{5} - %msg%n"
    }
}

root(DEBUG, ["FILE"])