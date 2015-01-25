package com.cqrs.frontend.actors

import java.util.concurrent.TimeUnit

import akka.actor._
import akka.event.slf4j.SLF4JLogging

import scala.concurrent.duration.Duration

class FrontendActor(path: String) extends Actor with SLF4JLogging {
  context.setReceiveTimeout(Duration(3, TimeUnit.SECONDS))
  sendIdentify()

  def sendIdentify(): Unit = {
    val selection = context.actorSelection(path)
    selection ! Identify(path)
  }

  def receive = identify

  def identify: Receive = {
    case ActorIdentity(`path`, Some(actor)) =>
      context.setReceiveTimeout(Duration.Undefined)
      log.info("switching to active state")
      context.become(active(actor))
      context.watch(actor)
    case ActorIdentity(`path`, None) =>
      val text = s"Remote actor with path $path is not available."
      log.error(text)
      sender() ! text
    case ReceiveTimeout =>
      sendIdentify()
    case msg:Any =>
      log.error(s"Ignoring message $msg, not ready yet.")
      sender() ! "Remote actor is not ready yet"
  }

  def active(actor: ActorRef): Receive = {
    case Terminated(actorRef) =>
      log.info("Actor $actorRef terminated.")
      context.become(identify)
      log.info("switching to identify state")
      context.setReceiveTimeout(Duration(3, TimeUnit.SECONDS))
      sendIdentify()
    case msg: Any => actor forward msg
  }
}