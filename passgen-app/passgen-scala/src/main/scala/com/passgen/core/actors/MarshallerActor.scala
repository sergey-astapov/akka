package com.passgen.core.actors

import akka.actor._
import akka.event.slf4j.SLF4JLogging
import com.google.gson.Gson
import com.passgen.core.model.GeneratePassword

class MarshallerActor(next: ActorRef) extends Actor with SLF4JLogging {
  val gson = new Gson()
  def receive = {
    case m: String =>
      log.debug("Unmarshalling message: {}", m)
      next.tell(gson.fromJson(m, classOf[GeneratePassword]), sender())
    case m: Number =>
      log.debug("Unmarshalling message: {}", m)
      next.tell(GeneratePassword(m.intValue()), sender())
  }
}