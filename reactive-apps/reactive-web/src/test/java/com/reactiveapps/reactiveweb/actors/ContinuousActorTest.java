package com.reactiveapps.reactiveweb.actors;

import akka.actor.ActorCell;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import akka.testkit.TestKit;
import com.reactiveapps.reactiveweb.protocol.*;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;
import scala.actors.threadpool.Arrays;

import java.util.Collections;

import static akka.actor.ActorRef.noSender;
import static com.reactiveapps.reactiveweb.protocol.GetContinuousState.Result.success;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ContinuousActorTest extends TestKit {
    public static final String UID = "1";
    public static final String DATA = "some-data";
    public static final ContinuousFact[] FACTS = new ContinuousFact[] {
            new StartFact(UID, DATA), new InnerFact(UID, DATA), new StopFact(UID, DATA, 3L)
    };
    public static final ContinuousFact[] CHECK_FACTS = new ContinuousFact[] {
            FACTS[0], FACTS[1]
    };

    private static ActorSystem _system = ActorSystem.create("TestSys", ConfigFactory.load().getConfig("TestSys"));

    public ContinuousActorTest() {
        super(_system);
    }

    @Test
    public void testFacts() {
        TestActorRef<ContinuousActor> ref = TestActorRef.create(_system, Props.create(ContinuousActor.class, UID));
        ref.tell(FACTS[0], noSender());
        ref.tell(FACTS[1], noSender());
        assertThat(ref.underlyingActor().populateFacts(), is(Arrays.asList(CHECK_FACTS)));
    }

    @Test
    public void testTerminated() {
        TestActorRef<ContinuousActor> ref = TestActorRef.create(_system, Props.create(ContinuousActor.class, UID));
        ref.tell(FACTS[0], noSender());
        ref.tell(FACTS[1], noSender());
        ref.tell(FACTS[2], noSender());
        assertTrue(((ActorCell) ref.actorContext()).isTerminated());
    }

    @Test
    public void testEmptyState() {
        ActorRef ref = _system.actorOf(Props.create(ContinuousActor.class, UID));
        ref.tell(GetContinuousState.withUid(UID), super.testActor());
        expectMsg(success(Collections.emptyList()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNotEmptyState() {
        ActorRef ref = _system.actorOf(Props.create(ContinuousActor.class, UID));
        ref.tell(FACTS[0], noSender());
        ref.tell(FACTS[1], noSender());
        ref.tell(GetContinuousState.withUid(UID), super.testActor());
        expectMsg(success(Arrays.asList(CHECK_FACTS)));
    }
}
