
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/tho-optum/WorkArea/dev/rally_projects/centri-web-poc/reader/conf/routes
// @DATE:Fri May 04 18:38:24 PDT 2018

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  KafkaReaderController_0: com.rallyhealth.dataship.reader.controllers.KafkaReaderController,
  // @LINE:8
  opsStatic_Routes_0: opsStatic.Routes,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    KafkaReaderController_0: com.rallyhealth.dataship.reader.controllers.KafkaReaderController,
    // @LINE:8
    opsStatic_Routes_0: opsStatic.Routes
  ) = this(errorHandler, KafkaReaderController_0, opsStatic_Routes_0, "/")

  import ReverseRouteContext.empty

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, KafkaReaderController_0, opsStatic_Routes_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """offset""", """com.rallyhealth.dataship.reader.controllers.KafkaReaderController.getOffset"""),
    prefixed_opsStatic_Routes_0_1.router.documentation,
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val com_rallyhealth_dataship_reader_controllers_KafkaReaderController_getOffset0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("offset")))
  )
  private[this] lazy val com_rallyhealth_dataship_reader_controllers_KafkaReaderController_getOffset0_invoker = createInvoker(
    KafkaReaderController_0.getOffset,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "com.rallyhealth.dataship.reader.controllers.KafkaReaderController",
      "getOffset",
      Nil,
      "GET",
      """ An Kafka read controller to get the latest offset read so far""",
      this.prefix + """offset"""
    )
  )

  // @LINE:8
  private[this] val prefixed_opsStatic_Routes_0_1 = Include(opsStatic_Routes_0.withPrefix(this.prefix + (if (this.prefix.endsWith("/")) "" else "/") + "rest/ops"))


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case com_rallyhealth_dataship_reader_controllers_KafkaReaderController_getOffset0_route(params) =>
      call { 
        com_rallyhealth_dataship_reader_controllers_KafkaReaderController_getOffset0_invoker.call(KafkaReaderController_0.getOffset)
      }
  
    // @LINE:8
    case prefixed_opsStatic_Routes_0_1(handler) => handler
  }
}
