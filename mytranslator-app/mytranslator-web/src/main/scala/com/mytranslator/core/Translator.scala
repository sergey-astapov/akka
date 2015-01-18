package com.mytranslator.core

import net.liftweb.json._

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.{Promise, Future}
import scala.util.control.NonFatal
import scalaj.http.Http

object Translator {
  val key = "trnsl.1.1.20150110T222452Z.98e7ceab2765aaf4.a91a234f5d66dd871f06be3486d3034406cca0e0"
  val lang = "en-ru"
  private val root = "https://translate.yandex.net/api/v1.5/tr.json/translate"

  def translate(text: String): Future[String] = {
    val p = Promise[String]
    global.execute(new Runnable {
      def run() = try {
        val res = Http(root)
          .param("key", key)
          .param("text", text)
          .param("lang", lang).asString
        if (res.code == 200) {
          p.success(compact(render(parse(res.body) \ "text")))
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
