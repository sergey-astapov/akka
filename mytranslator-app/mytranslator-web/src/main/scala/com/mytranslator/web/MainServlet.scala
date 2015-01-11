package com.mytranslator.web

import org.scalatra.ScalatraServlet

class MainServlet extends ScalatraServlet {
  get("/") {
    <html>
      Hello to MyTranslator
    </html>
  }

  get("/json-page") {
    "<json-response>Hello to MyTranslator</json-resonse>"
  }
}
