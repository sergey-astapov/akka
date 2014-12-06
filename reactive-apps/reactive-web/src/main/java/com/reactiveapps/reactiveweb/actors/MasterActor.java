package com.reactiveapps.reactiveweb.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.reactiveapps.reactiveweb.facts.ContinuousFact;
import com.reactiveapps.reactiveweb.facts.SingleFact;

public class MasterActor extends UntypedActor {
    private final LoggingAdapter LOG = Logging.getLogger(getContext().system(), "Master");

    private ActorRef marshall = getContext().actorOf(Props.create(MarshallActor.class), "marshall");
    private ActorRef single = getContext().actorOf(Props.create(SingleFactActor.class), "single");
    private ActorRef continuous = getContext().actorOf(Props.create(ContinuousFactActor.class), "continuous");

    @Override
    public void onReceive(Object m) throws Exception {
        if (m instanceof String) {
            LOG.info("Send to deserializer: {}", m);
            marshall.tell(m, self());
        } else if (m instanceof SingleFact) {
            LOG.info("Send to single: {}", m);
            single.tell(m, self());
        } else if (m instanceof ContinuousFact) {
            LOG.info("Send to continuous: {}", m);
            continuous.tell(m, self());
        } else {
            unhandled(m);
        }
    }
}
