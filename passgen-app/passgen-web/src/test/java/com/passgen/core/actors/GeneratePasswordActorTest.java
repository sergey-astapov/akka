package com.passgen.core.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestKit;
import akka.testkit.TestProbe;
import com.passgen.core.model.PasswordResult;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import static akka.actor.ActorRef.noSender;
import static com.passgen.core.model.GeneratePassword.withLength;

public class GeneratePasswordActorTest extends TestKit {
    private static ActorSystem _system = ActorSystem.create("TestSys", ConfigFactory.load().getConfig("TestSys"));

    public GeneratePasswordActorTest() {
        super(_system);
    }

    @Test
    public void test() {
        TestProbe probe = new TestProbe(_system);
        ActorRef ref = _system.actorOf(Props.create(GeneratePasswordActor.class, probe.ref()));
        ref.tell(withLength(8), noSender());
        probe.expectMsgClass(PasswordResult.class);
    }
}
