package com.reactiveapps.reactiveweb;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.reactiveapps.reactiveweb.actors.MasterActor;
import com.typesafe.config.ConfigFactory;

import java.io.InputStream;
import java.util.Properties;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        try(InputStream in = Main.class.getResourceAsStream("/app.properties")) {
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
        get("/facts/:id", (req, res) -> {
            res.status(404);
            return "Not found";
        });
        put("/facts", (req, res) -> {
            master.tell(req.body(), ActorRef.noSender());
            return "OK";
        });
    }
}
