package com.cqrs.frontend.web

import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
import com.cqrs.backend.model.Protocol._
import org.scalatra.ScalatraServlet

import scala.concurrent.Await
import scala.concurrent.duration.{FiniteDuration, Duration}

class MainServlet(system: ActorSystem, nextActor: ActorRef) extends ScalatraServlet {
  get("/") {
    redirect("/remote/state")
  }

  get("/remote/state") {
    contentType = "text/html"
    val duration: FiniteDuration = Duration(5, TimeUnit.SECONDS)
    implicit val timeout = Timeout(duration)
    val result = Await.result(nextActor ? GetState(), duration)
    log(s"result - $result")
    html.remote(result)
  }
}