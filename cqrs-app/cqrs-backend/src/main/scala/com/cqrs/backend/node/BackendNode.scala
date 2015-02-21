package com.cqrs.backend.node

import akka.actor.{ActorSystem, Props}
import com.cqrs.backend.actors.BackendActor
import com.typesafe.config.ConfigFactory

object BackendNode extends App {
  checkAspectj

  val config = ConfigFactory.load().getConfig("BackendSys")
  implicit val system = ActorSystem("BackendSys", config)
  val remote = system.actorOf(Props[BackendActor], "BackendActor")

  def checkAspectj():Unit = {
    try {
      org.aspectj.weaver.loadtime.Agent.getInstrumentation
    } catch {
      case e: Throwable => println(s"No AspectJ error: $e")
    }
  }
}
