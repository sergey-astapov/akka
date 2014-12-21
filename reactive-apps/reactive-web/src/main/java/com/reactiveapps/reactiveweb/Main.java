package com.reactiveapps.reactiveweb;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.camel.Camel;
import akka.camel.CamelExtension;
import akka.pattern.Patterns;
import com.google.gson.GsonBuilder;
import com.reactiveapps.reactiveweb.actors.ConsumerActor;
import com.reactiveapps.reactiveweb.actors.MasterActor;
import com.reactiveapps.reactiveweb.actors.ProducerActor;
import com.reactiveapps.reactiveweb.actors.ProducerSupervisor;
import com.reactiveapps.reactiveweb.protocol.GetContinuousState;
import com.typesafe.config.ConfigFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.reactiveapps.reactiveweb.protocol.GetContinuousState.Result.error;
import static spark.Spark.*;

public class Main {

    public static final String JMS_URL = "jms.url";
    public static final String JMS_DESTINATION_IN = "jms.destination.in";
    public static final String JMS_DESTINATION_OUT = "jms.destination.out";
    public static final String COMP_NAME_IN = "amq-in";
    public static final String COMP_NAME_OUT = "amq-out";

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        try(InputStream in = Main.class.getResourceAsStream("/app.properties")) {
            props.load(in);
        }
        setPort(Integer.valueOf(props.getProperty("http.port")));

        ActorSystem system = ActorSystem.create("FactSys", ConfigFactory.load());

        setupJms(system, props);

        final ActorRef master = system.actorOf(Props.create(MasterActor.class), "master");
        final Props producerProps = Props.create(ProducerActor.class,
                COMP_NAME_OUT + ":" + props.getProperty(JMS_DESTINATION_OUT));
        final ActorRef producerSupervisor = system.actorOf(Props.create(ProducerSupervisor.class, producerProps),
                "producerSupervisor");
        final ActorRef consumer = system.actorOf(Props.create(ConsumerActor.class,
                COMP_NAME_IN + ":" + props.getProperty(JMS_DESTINATION_IN), producerSupervisor), "consumer");

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

    public static void setupJms(ActorSystem system, Properties props) throws Exception {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        cf.setBrokerURL(props.getProperty(JMS_URL));
        Camel camel = CamelExtension.get(system);
        CamelContext camelContext = camel.context();
        JmsComponent in = JmsComponent.jmsComponent(cf);
        in.setDestinationResolver((s, d, p) -> new ActiveMQQueue(props.getProperty(JMS_DESTINATION_IN)));
        camelContext.addComponent(COMP_NAME_IN, in);
        JmsComponent out = JmsComponent.jmsComponent(cf);
        out.setDestinationResolver((s, d, p) -> new ActiveMQQueue(props.getProperty(JMS_DESTINATION_OUT)));
        camelContext.addComponent(COMP_NAME_OUT, out);
    }
}
