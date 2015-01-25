package com.cqrs.frontend.web

import javax.servlet.ServletContext

import akka.actor.{Props, ActorSystem}
import com.cqrs.frontend.actors.FrontendActor
import com.typesafe.config.ConfigFactory
import org.scalatra.LifeCycle

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    val config = ConfigFactory.load().getConfig("FrontendSys")
    val system = ActorSystem("FrontendSys", config)
    val path: String = "akka.tcp://BackendSys@0.0.0.0:5150/user/BackendActor"
    val local = system.actorOf(Props(classOf[FrontendActor], path), "FrontendActor")
    context.mount(new MainServlet(system, local), "/*")
  }
}