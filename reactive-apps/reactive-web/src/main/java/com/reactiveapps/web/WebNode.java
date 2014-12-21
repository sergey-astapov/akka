package com.reactiveapps.web;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import com.google.gson.GsonBuilder;
import com.reactiveapps.core.actors.MasterActor;
import com.reactiveapps.core.protocol.GetContinuousState;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.reactiveapps.core.protocol.GetContinuousState.Result.error;
import static spark.Spark.*;

public class WebNode {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        try(InputStream in = WebNode.class.getResourceAsStream("/app.properties")) {
            props.load(in);
        }
        setPort(Integer.valueOf(props.getProperty("http.port")));

        ActorSystem system = ActorSystem.create("FactSys", ConfigFactory.load());

        final ActorRef master = system.actorOf(Props.create(MasterActor.class), "master");

        get("/", (req, res) -> "Reactive AKKA Web Example");
        get("/facts", (req, res) -> {
            res.status(404);
            return "Not found";
        });
        get("/facts/:uid", "application/json", (req, res) -> {
            try {
                return Await.result(
                        Patterns.ask(master, GetContinuousState.withUid(req.params(":uid")), 5000),
                        Duration.create(5000, TimeUnit.MILLISECONDS));
            } catch (Exception e) {
                return error();
            }
        }, (o) -> new GsonBuilder().setPrettyPrinting().create().toJson(o));
        put("/facts", (req, res) -> {
            master.tell(req.body(), ActorRef.noSender());
            res.status(201);
            return "OK";
        });
    }
}
