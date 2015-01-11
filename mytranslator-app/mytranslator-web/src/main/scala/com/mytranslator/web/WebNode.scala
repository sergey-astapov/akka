package com.mytranslator.web

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener

object WebNode {
  def main(args: Array[String]) {
    val port = if (System.getenv("PORT") != null) System.getenv("PORT").toInt else 8080

    val server = new Server(port)
    val context = new WebAppContext()
    context setContextPath "/"
    context.setResourceBase("src/main/webapp")
    context.setInitParameter(ScalatraListener.LifeCycleKey, "com.mytranslator.web.ScalatraBootstrap")
    context.addEventListener(new ScalatraListener)
    context.addServlet(classOf[MainServlet], "/")

    server.setHandler(context)

    server.start
    server.join
  }
}
