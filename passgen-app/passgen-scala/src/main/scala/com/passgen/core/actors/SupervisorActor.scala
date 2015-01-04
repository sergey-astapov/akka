package com.passgen.core.actors

import java.util.concurrent.TimeUnit

import akka.actor.SupervisorStrategy.{Escalate, Restart}
import akka.actor._
import akka.event.slf4j.SLF4JLogging

import scala.concurrent.duration.Duration

class SupervisorActor extends Actor with SLF4JLogging {
  val fin: ActorRef = context.actorOf(Props.create(classOf[FinActor]), "fin")
  val generator: ActorRef = context.actorOf(Props.create(classOf[GeneratePasswordActor], fin), "generator")
  val marshaller: ActorRef = context.actorOf(Props.create(classOf[MarshallerActor], generator), "marshaller")

  def receive = {
    case m =>
      log.debug("Forward message: {}", m)
      marshaller forward m
  }

  override def supervisorStrategy = AllForOneStrategy(2, Duration.apply(5, TimeUnit.SECONDS)) {
    case e: NumberFormatException =>
      log.info("Restart actors, error: {}", e)
      Restart
    case other =>
      log.info("Escalate, error: {}", other)
      Escalate
  }
}