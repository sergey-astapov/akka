package com.reactiveapps.seed.actors;

import akka.actor.ActorRef;
import akka.camel.CamelMessage;
import akka.camel.javaapi.UntypedConsumerActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ConsumerActor extends UntypedConsumerActor {
    private final LoggingAdapter LOG = Logging.getLogger(getContext().system(), "Consumer");

    private String uri;
    private ActorRef nextActor;

    public ConsumerActor(String uri, ActorRef nextActor) {
        this.uri = uri;
        this.nextActor = nextActor;
    }

    @Override
    public String getEndpointUri() {
        return uri;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        LOG.info("Receive message: " + message);
        if (message instanceof CamelMessage) {
            String msg = ((CamelMessage) message).getBodyAs(String.class, getCamelContext());
            nextActor.tell(msg, self());
        } else {
            unhandled(message);
        }
    }
}