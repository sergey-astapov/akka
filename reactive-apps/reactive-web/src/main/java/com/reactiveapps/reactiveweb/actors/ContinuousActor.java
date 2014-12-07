package com.reactiveapps.reactiveweb.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.reactiveapps.reactiveweb.protocol.GetContinuousState;
import static com.reactiveapps.reactiveweb.protocol.GetContinuousState.Result.*;
import com.reactiveapps.reactiveweb.protocol.ContinuousFact;
import com.reactiveapps.reactiveweb.protocol.InnerFact;
import com.reactiveapps.reactiveweb.protocol.StartFact;
import com.reactiveapps.reactiveweb.protocol.StopFact;

import java.util.*;

public class ContinuousActor extends UntypedActor {
    private final LoggingAdapter LOG = Logging.getLogger(getContext().system(), "Continuous");

    private final String uid;
    private Optional<StartFact> start = Optional.empty();
    private final Set<InnerFact> inners = new HashSet<>();
    private Optional<StopFact> stop = Optional.empty();

    public ContinuousActor(String uid) {
        this.uid = uid;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof ContinuousFact) {
            ContinuousFact f = (ContinuousFact) o;
            checkUid(f.getUid());
            if (f instanceof StartFact) {
                start((StartFact) f);
            } else if (f instanceof InnerFact) {
                inner((InnerFact) f);
            } else if (f instanceof StopFact) {
                stop((StopFact) f);
            }
            afterPropertiesSet();
        } else if (o instanceof GetContinuousState) {
            GetContinuousState f = (GetContinuousState) o;
            checkUid(f.uid);
            getSender().tell(success(populateFacts()), ActorRef.noSender());
        } else {
            unhandled(o);
        }
    }

    public List<ContinuousFact> populateFacts() {
        List<ContinuousFact> result = new ArrayList<>();
        if (start.isPresent()) {
            result.add(start.get());
        }
        result.addAll(inners);
        if (stop.isPresent()) {
            result.add(stop.get());
        }
        return result;
    }

    private void checkUid(String factUid) {
        if (!uid.equals(factUid)) {
            LOG.error("Wrong fact uid: {}, need: {}", factUid, uid);
            throw new WrongFactUidException();
        }
    }

    private void stop(StopFact f) {
        LOG.info("Process stop f: [{}]", f);
        if (stop.isPresent()) {
            LOG.error("Stop already set, stop: [{}], new: [{}]", stop.get(), f);
            throw new DuplicateFactException();
        }
        stop = Optional.of(f);
    }

    private void inner(InnerFact f) {
        LOG.info("Process inner f: [{}]", f);
        if (inners.contains(f)) {
            LOG.error("Already have this fact: [{}]", f);
            throw new DuplicateFactException();
        }
        inners.add(f);
    }

    private void start(StartFact f) {
        LOG.info("Process start f: [{}]", f);
        if (start.isPresent()) {
            LOG.error("Start already set, start: [{}], new: [{}]", start.get(), f);
            throw new DuplicateFactException();
        }
        start = Optional.of(f);
    }

    public void afterPropertiesSet() {
        if (!start.isPresent() || !stop.isPresent()) {
            return;
        }
        int total = calculateTotal();
        LOG.info("Check total, current: {}, need: {}", total, stop.get().total);
        if (total < stop.get().total) {
            return;
        }
        if (total > stop.get().total) {
            throw new TotalFactException();
        }
        LOG.info("Processed all messages: {}. Kill self", total);
        self().tell(PoisonPill.getInstance(), ActorRef.noSender());
    }

    private int calculateTotal() {
        return inners.size() + 2;
    }

    public class TotalFactException extends RuntimeException {}
    public class WrongFactUidException extends RuntimeException {}
    public class DuplicateFactException extends RuntimeException {}
}
