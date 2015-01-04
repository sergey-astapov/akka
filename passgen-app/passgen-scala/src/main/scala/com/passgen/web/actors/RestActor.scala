package com.passgen.web.actors

import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorRef, ActorRefFactory}
import akka.event.slf4j.SLF4JLogging
import akka.pattern.ask
import akka.util.Timeout
import com.passgen.core.model.PasswordResult
import net.liftweb.json.Serialization._
import net.liftweb.json.{DateFormat, Formats}
import spray.http._
import spray.routing._

class RestActor(next: ActorRef) extends Actor with RestService {
  implicit def actorRefFactory: ActorRefFactory = context

  implicit def actorRef: ActorRef = next

  def receive = runRoute(rest)
}

trait RestService extends HttpService with SLF4JLogging {
  implicit def actorRef: ActorRef

  implicit val executionContext = actorRefFactory.dispatcher

  implicit val liftJsonFormats = new Formats {
    val dateFormat = new DateFormat {
      val sdf = new SimpleDateFormat("yyyy-MM-dd")

      def parse(s: String): Option[Date] = try {
        Some(sdf.parse(s))
      } catch {
        case e: Exception => None
      }

      def format(d: Date): String = sdf.format(d)
    }
  }

  implicit val timeout = Timeout(2, TimeUnit.SECONDS)
  implicit val rSettings = RoutingSettings.default(actorRefFactory)

  val rest = respondWithMediaType(MediaTypes.`application/json`) {
    path("") {
      get {
        respondWithMediaType(MediaTypes.`text/html`) {
          complete {
            <html>
              <body>
                <h1>Hello <i>Password Generation Application</i>!</h1>
              </body>
            </html>
          }
        }
      }
    }~
    path("generate") {
      get {
        parameter('length.as[Long]) { length =>
          complete {
            val askFuture = actorRef ? length
            askFuture.map {
              case result: PasswordResult => write(result)
              case result: Any => write(Map("status" -> "Response is unknown", "result" -> result))
            }
          }
        }
      }
    }
  }
}