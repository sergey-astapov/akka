package com.passgen.core.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.passgen.core.model.GeneratePassword;
import org.apache.commons.lang.RandomStringUtils;

import java.security.SecureRandom;

import static com.passgen.core.model.PasswordResult.withPassword;

public class GeneratePasswordActor extends UntypedActor {
    private final LoggingAdapter LOG = Logging.getLogger(getContext().system(), this);
    public static final int DEFAULT_LENGTH = 8;

    private final ActorRef next;

    public GeneratePasswordActor(ActorRef next) {
        this.next = next;
    }

    @Override
    public void onReceive(Object m) throws Exception {
        if (m instanceof GeneratePassword) {
            LOG.info("Generate password: {}", m);
            GeneratePassword cmd = (GeneratePassword)m;
            int size = cmd.length > 0 ? cmd.length : DEFAULT_LENGTH;
            String pass = RandomStringUtils.random(size, 0, 0, true, true, null, new SecureRandom());
            LOG.info("Password: {}", pass);
            next.tell(withPassword(pass), getSender());
        } else {
            unhandled(m);
        }
    }
}
