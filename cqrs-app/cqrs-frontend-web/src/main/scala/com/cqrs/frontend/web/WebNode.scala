package com.cqrs.frontend.web

import com.typesafe.config.ConfigFactory
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener

object WebNode extends App {
  val config = ConfigFactory.load().getConfig("FrontendSys")
  val port = config.getConfig("service").getInt("port")

  val server = new Server(port)
  val context = new WebAppContext()
  context setContextPath "/"
  context.setResourceBase("src/main/webapp")
  context.setInitParameter(ScalatraListener.LifeCycleKey, "com.cqrs.frontend.web.ScalatraBootstrap")
  context.addEventListener(new ScalatraListener)
  context.addServlet(classOf[MainServlet], "/")

  server.setHandler(context)

  server.start()
  server.join()
}
