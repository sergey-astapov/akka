package com.cqrs.backend.actors

import akka.actor._
import akka.event.slf4j.SLF4JLogging
import com.cqrs.backend.model.Protocol.{StateResult, GetState}

class BackendActor extends Actor with SLF4JLogging {
  var total: Int= 0

  def receive = {
    case m: GetState =>
      total += 1
      sender() ! StateResult(total)
  }
}