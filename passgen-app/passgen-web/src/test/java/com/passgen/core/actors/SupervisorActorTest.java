package com.passgen.core.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestKit;
import akka.testkit.TestProbe;
import com.passgen.core.model.PasswordResult;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

public class SupervisorActorTest extends TestKit {
    private static ActorSystem _system = ActorSystem.create("TestSys", ConfigFactory.load().getConfig("TestSys"));

    public SupervisorActorTest() {
        super(_system);
    }

    @Test
    public void testString() {
        ActorRef ref = _system.actorOf(Props.create(SupervisorActor.class));
        TestProbe probe = new TestProbe(_system);
        ref.tell("{length:8}", probe.ref());
        probe.expectMsgClass(PasswordResult.class);
    }

    @Test
    public void testInteger() {
        ActorRef ref = _system.actorOf(Props.create(SupervisorActor.class));
        TestProbe probe = new TestProbe(_system);
        ref.tell(8, probe.ref());
        probe.expectMsgClass(PasswordResult.class);
    }

    @Test
    public void testNoMessages() {
        ActorRef ref = _system.actorOf(Props.create(SupervisorActor.class));
        TestProbe probe = new TestProbe(_system);
        ref.tell("error", probe.ref());
        expectNoMsg();
    }
}
