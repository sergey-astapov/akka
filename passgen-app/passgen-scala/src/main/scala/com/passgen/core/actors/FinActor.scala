package com.passgen.core.actors

import akka.actor._
import akka.event.slf4j.SLF4JLogging
import com.passgen.core.model.PasswordResult

class FinActor extends Actor with SLF4JLogging {
  def receive = {
    case m: PasswordResult =>
      log.debug("Password result: {}", m)
      sender() ! m
  }
}