package com.reactiveapps.seed.actors;

import akka.actor.*;
import akka.camel.AkkaCamelException;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class ProducerSupervisor extends UntypedActor {
    private final LoggingAdapter LOG = Logging.getLogger(getContext().system(), "ProducerSupervisor");

    private final ActorRef producer;

    public ProducerSupervisor(Props producerProps) {
        producer = getContext().actorOf(producerProps, "producer");
    }

    public ProducerSupervisor(ActorRef producer) {
        this.producer = producer;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        LOG.info("Forward message: {}", o);
        producer.forward(o, getContext());
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(2, Duration.apply(5, TimeUnit.SECONDS), (t) -> {
            if (t instanceof UnsupportedOperationException) {
                LOG.info("Stop actor, error: {}", t);
                return SupervisorStrategy.stop();
            } else if (t instanceof AkkaCamelException) {
                LOG.info("Resume actor, error: {}", t);
                return SupervisorStrategy.resume();
            } else {
                LOG.info("Escalate, error: {}", t);
                return SupervisorStrategy.escalate();
            }
        });
    }
}
