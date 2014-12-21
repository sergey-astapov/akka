package com.reactiveapps.reactiveweb.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class JmsConsumerRun {
    private final static Logger LOG = LoggerFactory.getLogger(JmsConsumerRun.class);

    public static void main(String args[]) throws Exception {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(new ActiveMQConnectionFactory("tcp://localhost:61616"));
        container.setDestination(new ActiveMQQueue("q.out"));
        container.setMessageListener((MessageListener) (m) -> {
            try {
                LOG.info("Receive message: {}", ((TextMessage)m).getText());
            } catch (JMSException e) {}
        });
        container.setReceiveTimeout(-1);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.afterPropertiesSet();
        container.start();
    }
}
