package com.reactiveapps.reactiveweb.actors;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.reactiveapps.reactiveweb.protocol.SingleFact;

public class SingleFactActor extends UntypedActor {
    private final LoggingAdapter LOG = Logging.getLogger(getContext().system(), "Single");
    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof SingleFact) {
            LOG.debug("Process fact: [{}]", o);
        } else {
            unhandled(o);
        }
    }
}
