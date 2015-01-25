package com.cqrs.core

import scalaj.http.{HttpResponse, Http}
import net.liftweb.json._

object SimpleRun extends App {
  val key = "trnsl.1.1.20150110T222452Z.98e7ceab2765aaf4.a91a234f5d66dd871f06be3486d3034406cca0e0";
  val text = "hi dude"
  val lang = "en-ru"
  private val root = "https://translate.yandex.net/api/v1.5/tr.json/translate"
  val url = "%s?key=%s&text=%s&lang=%s".format(root, key, text, lang)
  println(url)
  val res = Http(root)
    .param("key", key)
    .param("text", text)
    .param("lang", lang).asString
  println(res.code)
  println(res.body)
  val json = parse(res.body)
  val s = compact(render(json \ "text"))
  println(s.replace("""["""", "").replace(""""]""", ""))
}
