package com.reactiveapps.reactiveweb.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestKit;
import com.google.gson.Gson;
import com.reactiveapps.reactiveweb.protocol.SingleFact;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MarshallActorTest extends TestKit {
    public static final String UID = "1";
    public static final String DATA = "some-data";
    public static final SingleFact SINGLE_FACT = new SingleFact(UID, DATA);
    private static ActorSystem _system = ActorSystem.create("TestSys", ConfigFactory.load().getConfig("TestSys"));

    public MarshallActorTest() {
        super(_system);
    }

    @Test
    public void testMarshall() {
        ActorRef ref = _system.actorOf(Props.create(MarshallActor.class));
        Map<String, String> map = new HashMap<>();
        map.put("uid", UID);
        map.put("type", "single");
        map.put("data", DATA);
        String msg = new Gson().toJson(map);
        ref.tell(msg, testActor());
        expectMsg(SINGLE_FACT);
    }

    @Test
    public void testUnsupported() {
        ActorRef ref = _system.actorOf(Props.create(MarshallActor.class));
        Map<String, String> map = new HashMap<>();
        map.put("type", "unsupported");
        String msg = new Gson().toJson(map);
        ref.tell(msg, testActor());
        expectNoMsg();
    }
}
