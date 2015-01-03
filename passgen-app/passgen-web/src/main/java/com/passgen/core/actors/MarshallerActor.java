package com.passgen.core.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.passgen.core.model.GeneratePassword;

import static com.passgen.core.model.GeneratePassword.withLength;

public class MarshallerActor extends UntypedActor {
    private final LoggingAdapter LOG = Logging.getLogger(getContext().system(), this);

    private final ActorRef next;
    private final Gson gson = new Gson();

    public MarshallerActor(ActorRef next) {
        this.next = next;
    }

    @Override
    public void onReceive(Object m) throws Exception {
        if (m instanceof String) {
            LOG.debug("Unmarshalling message: {}", m);
            try {
                next.tell(gson.fromJson((String) m, GeneratePassword.class), getSender());
            } catch (JsonSyntaxException e) {
                getSelf().tell(Integer.parseInt((String) m), getSender());
            }
        } else if (m instanceof Integer) {
            next.tell(withLength((Integer) m), getSender());
        } else {
            unhandled(m);
        }
    }
}
