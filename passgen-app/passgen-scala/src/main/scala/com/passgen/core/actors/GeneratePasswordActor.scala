package com.passgen.core.actors

import java.security.SecureRandom

import akka.actor._
import akka.event.slf4j.SLF4JLogging
import com.passgen.core.model.GeneratePassword
import com.passgen.core.model.PasswordResult._
import org.apache.commons.lang.RandomStringUtils

class GeneratePasswordActor(next: ActorRef) extends Actor with SLF4JLogging {
  val DEFAULT_LENGTH: Int = 8

  def receive = {
    case m: GeneratePassword =>
      log.debug("Generate password: {}", m)
      val size: Int = if (m.length > 0) m.length else DEFAULT_LENGTH
      val pass: String = RandomStringUtils.random(size, 0, 0, true, true, null, new SecureRandom)
      log.debug("Password: {}", pass)
      next.tell(withPassword(pass), sender())
  }
}