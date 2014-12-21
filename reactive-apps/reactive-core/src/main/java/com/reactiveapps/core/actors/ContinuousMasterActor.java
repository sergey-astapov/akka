package com.reactiveapps.core.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.reactiveapps.core.protocol.GetContinuousState;
import com.reactiveapps.core.protocol.ContinuousFact;

import static com.reactiveapps.core.protocol.GetContinuousState.Result.notFound;

public class ContinuousMasterActor extends UntypedActor {
    private final LoggingAdapter LOG = Logging.getLogger(getContext().system(), "ContinuousMaster");

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof ContinuousFact) {
            LOG.info("Process start fact: [{}]", o);
            ContinuousFact f = (ContinuousFact)o;
            ActorRef actorRef = getContext().getChild(f.getUid());
            if (actorRef == null) {
                actorRef = getContext().actorOf(Props.create(ContinuousActor.class, f.getUid()), f.getUid());
            }
            actorRef.tell(f, self());
        } else if (o instanceof GetContinuousState) {
            GetContinuousState c = (GetContinuousState)o;
            ActorRef actorRef = getContext().getChild(c.uid);
            if (actorRef == null) {
                getSender().tell(notFound(), ActorRef.noSender());
            } else {
                actorRef.tell(c, getSender());
            }
        } else {
            unhandled(o);
        }
    }
}
