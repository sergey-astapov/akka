package com.reactiveapps.seed;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.camel.Camel;
import akka.camel.CamelExtension;
import com.reactiveapps.seed.actors.ConsumerActor;
import com.reactiveapps.seed.actors.ProducerActor;
import com.reactiveapps.seed.actors.ProducerSupervisor;
import com.typesafe.config.ConfigFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;

import java.io.InputStream;
import java.util.Properties;

public class SeedNode {

    public static final String JMS_URL = "jms.url";
    public static final String JMS_DESTINATION_IN = "jms.destination.in";
    public static final String JMS_DESTINATION_OUT = "jms.destination.out";
    public static final String COMP_NAME_IN = "amq-in";
    public static final String COMP_NAME_OUT = "amq-out";

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        try(InputStream in = SeedNode.class.getResourceAsStream("/app.properties")) {
            props.load(in);
        }

        ActorSystem system = ActorSystem.create("FactSys", ConfigFactory.load());

        setupJms(system, props);

        final Props producerProps = Props.create(ProducerActor.class,
                COMP_NAME_OUT + ":" + props.getProperty(JMS_DESTINATION_OUT));
        final ActorRef producerSupervisor = system.actorOf(Props.create(ProducerSupervisor.class, producerProps),
                "producerSupervisor");
        final ActorRef consumer = system.actorOf(Props.create(ConsumerActor.class,
                COMP_NAME_IN + ":" + props.getProperty(JMS_DESTINATION_IN), producerSupervisor), "consumer");
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
