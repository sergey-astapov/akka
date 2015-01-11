package com.mytranslator.core

import java.util.concurrent.TimeUnit

import akka.event.slf4j.SLF4JLogging
import net.liftweb.json._

import scala.concurrent.duration._
import scala.concurrent.{Await, Promise, ExecutionContext, Future}
import scala.util.control.NonFatal
import scala.util.{Failure, Success}
import scalaj.http.Http
import ExecutionContext.Implicits.global

object FutureRun extends App with SLF4JLogging {
  val key = "trnsl.1.1.20150110T222452Z.98e7ceab2765aaf4.a91a234f5d66dd871f06be3486d3034406cca0e0"
  val text = "hi dude"
  val lang = "en-ru"
  private val root = "https://translate.yandex.net/api/v1.5/tr.json/translate"

  val f = sendRequestWithPromise

//  f foreach {
//    case body => log.info("body: {}", body)
//  }
//
//  f.failed foreach {
//    case e => log.info("error: {}", e)
//  }

//  f onComplete {
//    case Success(body) => log.info("body: {}", body)
//    case Failure(e) => log.info("error: {}", e)
//  }

  f.map(body => compact(render(parse(body) \ "text"))) onComplete {
    case Success(s) => log.info("translate: {}", s)
    case Failure(e) => log.info("error: {}", e)
  }

//  val s = Await.result(f, Duration(5, TimeUnit.SECONDS))
//  log.info(s)
  Thread.sleep(5000)

  def sendRequestWithFuture: Future[String] = {
    Future[String] {
      val res = Http(root)
        .param("key", key)
        .param("text", text)
        .param("lang", lang).asString
      if (res.code != 200) {
        throw new RuntimeException("Code: " + res.code)
      }
      res.body
    }
  }

  def sendRequestWithPromise: Future[String] = {
    val p = Promise[String]
    global.execute(new Runnable {
      def run() = try {
        val res = Http(root)
          .param("key", key)
          .param("text", text)
          .param("lang", lang).asString
        if (res.code == 200) {
          p.success(res.body)
        } else {
          p.failure(new RuntimeException("Code: " + res.code))
        }
      } catch {
        case NonFatal(e) => p.failure(e)
      }
    })
    p.future
  }
}
