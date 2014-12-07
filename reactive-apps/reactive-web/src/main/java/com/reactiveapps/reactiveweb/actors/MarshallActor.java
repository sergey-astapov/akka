package com.reactiveapps.reactiveweb.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.google.gson.Gson;
import com.reactiveapps.reactiveweb.protocol.*;

import java.util.Properties;

public class MarshallActor extends UntypedActor {
    private final LoggingAdapter LOG = Logging.getLogger(getContext().system(), "Marshall");

    private Gson gson = new Gson();

    @Override
    public void onReceive(Object m) throws Exception {
        if (m instanceof String) {
            Fact fact = deserializeFact((String) m);
            LOG.info("Receive fact: [{}]", fact);
            getSender().tell(fact, ActorRef.noSender());
        } else {
            unhandled(m);
        }
    }

    private Fact deserializeFact(String m) {
        Properties data = gson.fromJson(m, Properties.class);
        Fact.Type type = Fact.Type.valueOf(data.getProperty("type", "single").toUpperCase());
        switch(type) {
            case SINGLE:    return gson.fromJson(m, SingleFact.class);
            case START:     return gson.fromJson(m, StartFact.class);
            case STOP:      return gson.fromJson(m, StopFact.class);
            case INNER:     return gson.fromJson(m, InnerFact.class);
        }
        throw new RuntimeException("Unsupported message type: " + type);
    }
}
