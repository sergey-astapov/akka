package com.passgen.core.actors;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class SupervisorActor extends UntypedActor {
    private final LoggingAdapter LOG = Logging.getLogger(getContext().system(), this);

    private final ActorRef marshaller;
    private final ActorRef generator;
    private final ActorRef fin;

    public SupervisorActor() {
        fin = getContext().actorOf(Props.create(FinActor.class), "fin");
        generator = getContext().actorOf(Props.create(GeneratePasswordActor.class, fin), "generator");
        marshaller = getContext().actorOf(Props.create(MarshallerActor.class, generator), "marshaller");
    }

    public SupervisorStrategy supervisorStrategy() {
        return new AllForOneStrategy(2, Duration.apply(5, TimeUnit.SECONDS), (t) -> {
            if (t instanceof NumberFormatException) {
                LOG.info("Restart actors, error: {}", t);
                return SupervisorStrategy.restart();
            } else {
                LOG.info("Escalate, error: {}", t);
                return SupervisorStrategy.escalate();
            }
        });
    }

    @Override
    public void onReceive(Object m) throws Exception {
        LOG.info("Forward message: {}", m);
        marshaller.forward(m, getContext());
    }
}
