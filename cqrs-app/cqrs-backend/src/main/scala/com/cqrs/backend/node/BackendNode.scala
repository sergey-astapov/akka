package com.cqrs.backend.node

import akka.actor.{ActorSystem, Props}
import com.cqrs.backend.actors.BackendActor
import com.typesafe.config.ConfigFactory

object BackendNode extends App {
  val config = ConfigFactory.load().getConfig("BackendSys")
  implicit val system = ActorSystem("BackendSys", config)
  val remote = system.actorOf(Props[BackendActor], "BackendActor")
}
