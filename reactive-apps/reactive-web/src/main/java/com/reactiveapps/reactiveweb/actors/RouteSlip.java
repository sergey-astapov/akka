package com.reactiveapps.reactiveweb.actors;

import akka.actor.ActorRef;
import scala.collection.Traversable;

public interface RouteSlip {
    public class Message {
        public final Traversable<ActorRef> slip;
        public final Object message;

        public Message(Traversable<ActorRef> slip, Object message) {
            this.slip = slip;
            this.message = message;
        }
    }

    default public void sendMessageToNext(Traversable<ActorRef> slip, Object m) {
        ActorRef next = slip.head();
        Traversable<ActorRef> newSlip = slip.tail();
        if (newSlip.isEmpty()) {
            next.tell(m, ActorRef.noSender());
        } else {
            next.tell(new Message(newSlip, m), ActorRef.noSender());
        }
    }
}
