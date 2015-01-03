package com.passgen.web;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.passgen.core.actors.SupervisorActor;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import spark.ModelAndView;
import spark.Response;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.passgen.core.model.PasswordResult.withError;
import static java.lang.System.getenv;
import static spark.Spark.get;
import static spark.Spark.setPort;

public class WebNode {
    private final static Logger LOG = LoggerFactory.getLogger(WebNode.class);
    public static final int TIMEOUT = 5000;
    public static final int DEFAULT_PORT = 8888;

    public static void main(String[] args) throws Exception {
        setPort(Optional.ofNullable(getenv("PORT")).map(Integer::parseInt).orElse(DEFAULT_PORT));

        ActorSystem system = ActorSystem.create("PassgenSys", ConfigFactory.load());

        final ActorRef supervisor = system.actorOf(Props.create(SupervisorActor.class), "supervisor");
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();

        get("/", (req, res) -> {
            Map<String, String> map = new HashMap<>();
            map.put("name", "Password Generation Application");
            return new ModelAndView(map, "hello.mst");
        }, new MustacheTemplateEngine());
        get("/generate", "application/json", (req, res) -> generate(supervisor, res, req.body(), TIMEOUT), gson::toJson);
        get("/generate/:length", "application/json", (req, res) -> generate(supervisor, res, req.params("length"), TIMEOUT), gson::toJson);
    }

    private static Object generate(ActorRef master, Response res, Object message, int timeout) {
        try {
            return Await.result(Patterns.ask(master, message, timeout),
                    Duration.create(timeout, TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            LOG.error("Generate failed", e);
            res.status(500);
            return withError(e.getMessage());
        }
    }
}
