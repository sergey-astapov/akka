package com.reactiveapps.reactiveweb.actors;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.reactiveapps.reactiveweb.facts.InnerFact;
import com.reactiveapps.reactiveweb.facts.StartFact;
import com.reactiveapps.reactiveweb.facts.StopFact;

public class ContinuousFactActor extends UntypedActor {
    private final LoggingAdapter LOG = Logging.getLogger(getContext().system(), "Continuous");
    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof StartFact) {
            LOG.info("Process start fact: [{}]", o);
        } else if (o instanceof InnerFact) {
            LOG.info("Process inner fact: [{}]", o);
        } else if (o instanceof StopFact) {
            LOG.info("Process stop fact: [{}]", o);
        } else {
            unhandled(o);
        }
    }
}
