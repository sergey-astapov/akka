package com.reactiveapps.core.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestKit;
import com.google.gson.Gson;
import com.reactiveapps.core.protocol.GetContinuousState;
import com.reactiveapps.core.protocol.SingleFact;
import com.reactiveapps.core.protocol.StartFact;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static akka.actor.ActorRef.noSender;

public class MasterActorTest extends TestKit {
    public static final String UID = "1";
    public static final String DATA = "some-data";
    public static final SingleFact SINGLE_FACT = new SingleFact(UID, DATA);
    public static final StartFact START_FACT = new StartFact(UID, DATA);
    private static ActorSystem _system = ActorSystem.create("TestSys", ConfigFactory.load().getConfig("TestSys"));

    public MasterActorTest() {
        super(_system);
    }

    @Test
    public void testMarshall() {
        ActorRef ref = _system.actorOf(Props.create(MasterActor.class, testActor(), noSender(), noSender()));
        Map<String, String> map = new HashMap<>();
        map.put("uid", UID);
        map.put("type", "single");
        map.put("data", DATA);
        String msg = new Gson().toJson(map);
        ref.tell(msg, noSender());
        expectMsg(msg);
    }

    @Test
    public void testSingle() {
        ActorRef ref = _system.actorOf(Props.create(MasterActor.class, noSender(), testActor(), noSender()));
        ref.tell(SINGLE_FACT, noSender());
        expectMsg(SINGLE_FACT);
    }

    @Test
    public void testStart() {
        ActorRef ref = _system.actorOf(Props.create(MasterActor.class, noSender(), noSender(), testActor()));
        ref.tell(START_FACT, noSender());
        expectMsg(START_FACT);
    }

    @Test
    public void testGetStart() {
        ActorRef ref = _system.actorOf(Props.create(MasterActor.class, noSender(), noSender(), testActor()));
        GetContinuousState msg = GetContinuousState.withUid(UID);
        ref.tell(msg, noSender());
        expectMsg(msg);
    }

    @Test
    public void testNoMessages() {
        ActorRef ref = _system.actorOf(Props.create(MasterActor.class, noSender(), noSender(), noSender()));
        ref.tell(1, noSender());
        expectNoMsg();
    }
}
