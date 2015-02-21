package com.cqrs.backend.actors

import akka.actor._
import akka.event.slf4j.SLF4JLogging
import com.cqrs.backend.model.Protocol.{StateResult, GetState}
import kamon.trace.TraceRecorder

class BackendActor extends Actor with SLF4JLogging {
  var total: Int= 0

  def receive = {
    case m: GetState =>
      implicit val system = context.system
      TraceRecorder.withNewTraceContext("sample-trace") {
        total += 1
        log.debug(s"Total=$total")
        sender() ! StateResult(total)
      }
      TraceRecorder.finish()
  }
}