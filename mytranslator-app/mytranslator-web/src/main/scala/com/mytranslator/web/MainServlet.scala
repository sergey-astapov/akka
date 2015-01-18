package com.mytranslator.web

import com.mytranslator.core.Translator
import org.scalatra.{AsyncResult, FutureSupport, ScalatraServlet}

import scala.concurrent.ExecutionContext
import scala.xml.Node

class MainServlet extends ScalatraServlet with FutureSupport {
  protected implicit def executor: ExecutionContext = ExecutionContext.global

  private def displayPage(title: String, content: Seq[Node]) = Template.page(title, content, url(_, includeServletPath = false))

  get("/") {
    redirect("/translate")
  }

  get("/translate") {
    displayPage("My Translator: EN -> RU",
      <form action={ url("/result") } method='POST'>
        <br/>Enter:<input name="text" type='text'/>
        <br/><input type='submit'/>
      </form>
    )
  }

  post("/result") {
    val text = params("text")
    new AsyncResult { val is =
      Translator.translate(text).map(result =>
        displayPage("My Translator: Result",
          <p>{text} -> {result}</p>
        ))
    }
  }
}
