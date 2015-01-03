import static ch.qos.logback.classic.Level.*

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%date{ISO8601} %-5level %-4relative %logger{5} [%thread] - %msg%n"
    }
}

logger("org.eclipse.jetty", INFO)
logger("akka", DEBUG)

root(DEBUG, ["CONSOLE"])