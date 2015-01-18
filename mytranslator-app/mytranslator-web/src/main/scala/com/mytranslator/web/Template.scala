package com.mytranslator.web

import scala.xml.Node

object Template {

  def page(title: String, content: Seq[Node], url: String => String = identity _, head: Seq[Node] = Nil, scripts: Seq[String] = Seq.empty, defaultScripts: Seq[String] = Seq("/assets/js/jquery.min.js", "/assets/js/bootstrap.min.js")) = {
    <html lang="en">
      <head>
        <title>{ title }</title>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta name="description" content=""/>
        <meta name="author" content=""/>
        <!-- Le styles -->
        <link href={ url("/assets/css/bootstrap.css") } rel="stylesheet"/>
        <link href={ url("/assets/css/bootstrap-responsive.css") } rel="stylesheet"/>
        <link href={ url("/assets/css/syntax.css") } rel="stylesheet"/>
        <link href={ url("/assets/css/scalatra.css") } rel="stylesheet"/>
        <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
          <![endif]-->
        { head }
      </head>
      <body>
        <div class="navbar navbar-inverse navbar-fixed-top">
          <div class="navbar-inner">
            <div class="container">
              <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </a>
              <a class="brand" href="/">Scalatra Examples</a>
              <div class="nav-collapse collapse">
              </div><!--/.nav-collapse -->
            </div>
          </div>
        </div>
        <div class="container">
          <div class="content">
            <div class="page-header">
              <h1>{ title }</h1>
            </div>
            { content }
            <hr/>
            <a href={ url("/date/2009/12/26") }>date example</a><br/>
            <a href={ url("/form") }>form example</a><br/>
            <a href={ url("/upload") }>upload</a><br/>
            <a href={ url("/") }>hello world</a><br/>
            <a href={ url("/flash-map/form") }>flash scope</a><br/>
            <a href={ url("/login") }>login</a><br/>
            <a href={ url("/logout") }>logout</a><br/>
            <a href={ url("/basic-auth") }>basic auth</a><br/>
            <a href={ url("/filter-example") }>filter example</a><br/>
            <a href={ url("/cookies-example") }>cookies example</a><br/>
            <a href={ url("/atmosphere") }>atmosphere chat demo</a><br/>
          </div><!-- /content -->
        </div><!-- /container -->
        <!-- Le javascript
            ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        {
          (defaultScripts ++ scripts) map { pth =>
            <script type="text/javascript" src={ url(pth) }></script>
          }
        }
      </body>
    </html>
  }
}
