package com.passgen.core.actors;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.passgen.core.model.PasswordResult;

import static akka.actor.ActorRef.noSender;

public class FinActor extends UntypedActor {
    private final LoggingAdapter LOG = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object m) throws Exception {
        if (m instanceof PasswordResult) {
            LOG.info("Password result: {}", m);
            sender().tell(m, noSender());
        } else {
            unhandled(m);
        }
    }
}
