package com.reactiveapps.seed.jms;

import com.google.gson.Gson;
import com.reactiveapps.core.protocol.SingleFact;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

public class JmsProducerRun {
    public static void main(String args[]) throws Exception {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
        JmsTemplate temp = new JmsTemplate(cf);
        temp.convertAndSend("q.in", new Gson().toJson(new SingleFact("1", "some-data")));
    }
}
