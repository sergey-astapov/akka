package com.reactiveapps.core.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.reactiveapps.core.protocol.ContinuousFact;
import com.reactiveapps.core.protocol.GetContinuousState;
import com.reactiveapps.core.protocol.SingleFact;

public class MasterActor extends UntypedActor {
    private final LoggingAdapter LOG = Logging.getLogger(getContext().system(), "Master");

    private final ActorRef marshall;
    private final ActorRef single;
    private final ActorRef continuousMaster;

    public MasterActor() {
        marshall = getContext().actorOf(Props.create(MarshallActor.class), "marshall");
        single = getContext().actorOf(Props.create(SingleFactActor.class), "single");
        continuousMaster = getContext().actorOf(Props.create(ContinuousMasterActor.class), "continuousMaster");
    }

    public MasterActor(ActorRef marshall, ActorRef single, ActorRef continuousMaster) {
        this.marshall = marshall;
        this.single = single;
        this.continuousMaster = continuousMaster;
    }

    @Override
    public void onReceive(Object m) throws Exception {
        if (m instanceof String) {
            LOG.info("Send to deserializer: {}", m);
            marshall.tell(m, self());
        } else if (m instanceof SingleFact) {
            LOG.info("Send to single: {}", m);
            single.tell(m, self());
        } else if (m instanceof ContinuousFact) {
            LOG.info("Send to continuousMaster: {}", m);
            continuousMaster.tell(m, self());
        } else if (m instanceof GetContinuousState) {
            LOG.info("Send to continuousMaster: {}", m);
            continuousMaster.tell(m, getSender());
        } else {
            unhandled(m);
        }
    }
}
