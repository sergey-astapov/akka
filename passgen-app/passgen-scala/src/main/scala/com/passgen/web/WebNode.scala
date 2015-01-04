package com.passgen.web

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import com.passgen.core.actors.SupervisorActor
import com.passgen.web.actors.RestActor
import com.typesafe.config.ConfigFactory
import spray.can.Http
import util.Try

object WebNode extends App {
    val config = ConfigFactory.load().getConfig("PassgenSys")
    implicit val system = ActorSystem("PassgenSys", config)
    val supervisor = system.actorOf(Props[SupervisorActor], "supervisor")
    val actorRef = system.actorOf(Props(new RestActor(supervisor)), "rest")

    lazy val serviceHost = Try(config.getString("service.host")).getOrElse("localhost")

    /** Port to start service on. */
    lazy val servicePort = Try(config.getInt("service.port")).getOrElse(8080)
    IO(Http) ! Http.Bind(actorRef, serviceHost, servicePort)
}
