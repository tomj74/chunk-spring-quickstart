Spring MVC & Chunk Templates
================
[<img align="right" src="http://www.x5software.com/chunk/docs/img/chunk_logo_500x500.png" width="250" />][1]
[<img align="right" src="http://www.lessonslab.com/wp-content/uploads/2014/11/spring-logo.png" />][2]

Sample web application with [Spring MVC][3] (Controller layer) and [Chunk Templates][1] (View layer)

<h2>About</h2>
This simple project is a blueprint for dropping in Chunk Templates as your template engine for Spring MVC.

<h2>Request lifecycle</h2>
1. Dispatcher Servlet<br/>
2. Handler Mapping<br/>
3. Controller method<br/>
4. ModelAndView <br/>
5. <b>ViewResolver</b> (ChunkTemplateView)<br/>
6. Composed View using Chunk Templates.

<h2>How can I run it?</h2>
Just clone the project and then run: <br/>
a) <i>mvn tomcat7:run</i> (Tomcat 7 servlet container), or  <br/>
b) <i>mvn jetty:run</i> (Jetty servlet container)

<h2>Credits</h2>
Based on Andrzej Cichon's [work][4] with Facelets.

![Analytics](https://ga-beacon.appspot.com/UA-18933152-2/tomj74/chunk-spring-mvc)

  [1]: http://www.x5software.com/chunk/
  [2]: http://www.x5software.com/chunk/wiki/Spring_MVC
  [3]: http://projects.spring.io/spring-framework/
  [4]: https://github.com/acichon89/springmvcfacelets
