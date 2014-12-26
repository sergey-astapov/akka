package com.passgen.core.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestKit;
import akka.testkit.TestProbe;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import static akka.actor.ActorRef.noSender;
import static com.passgen.core.model.GeneratePassword.withLength;

public class MarshallerActorTest extends TestKit {
    private static ActorSystem _system = ActorSystem.create("TestSys", ConfigFactory.load().getConfig("TestSys"));

    public MarshallerActorTest() {
        super(_system);
    }

    @Test
    public void testMarshall() {
        TestProbe probe = new TestProbe(_system);
        ActorRef ref = _system.actorOf(Props.create(MarshallerActor.class, probe.ref()));
        ref.tell("{length:8}", noSender());
        probe.expectMsg(withLength(8));
    }
}
