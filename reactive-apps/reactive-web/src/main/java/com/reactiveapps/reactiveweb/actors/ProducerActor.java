package com.reactiveapps.reactiveweb.actors;

import akka.camel.javaapi.UntypedProducerActor;

public class ProducerActor extends UntypedProducerActor {
    private String uri;

    public ProducerActor(String uri) {
        this.uri = uri;
    }

    @Override
    public String getEndpointUri() {
        return uri;
    }

    public boolean isOneway() {
        return true;
    }
}