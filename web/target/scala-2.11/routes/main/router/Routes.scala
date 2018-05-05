
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/tho-optum/WorkArea/dev/rally_projects/centri-web-poc/web/conf/routes
// @DATE:Fri May 04 18:38:12 PDT 2018

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  GreetController_0: com.rallyhealth.dataship.controllers.GreetController,
  // @LINE:8
  EligibilityController_1: com.rallyhealth.dataship.controllers.EligibilityController,
  // @LINE:11
  opsStatic_Routes_0: opsStatic.Routes,
  // @LINE:14
  HealthCheckController_2: com.rallyhealth.dataship.controllers.HealthCheckController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    GreetController_0: com.rallyhealth.dataship.controllers.GreetController,
    // @LINE:8
    EligibilityController_1: com.rallyhealth.dataship.controllers.EligibilityController,
    // @LINE:11
    opsStatic_Routes_0: opsStatic.Routes,
    // @LINE:14
    HealthCheckController_2: com.rallyhealth.dataship.controllers.HealthCheckController
  ) = this(errorHandler, GreetController_0, EligibilityController_1, opsStatic_Routes_0, HealthCheckController_2, "/")

  import ReverseRouteContext.empty

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, GreetController_0, EligibilityController_1, opsStatic_Routes_0, HealthCheckController_2, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """greet""", """com.rallyhealth.dataship.controllers.GreetController.greet"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """dataship/v1/eligibility""", """com.rallyhealth.dataship.controllers.EligibilityController.getEligibility"""),
    prefixed_opsStatic_Routes_0_2.router.documentation,
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """healthStatus""", """com.rallyhealth.dataship.controllers.HealthCheckController.healthStatus"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val com_rallyhealth_dataship_controllers_GreetController_greet0_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("greet")))
  )
  private[this] lazy val com_rallyhealth_dataship_controllers_GreetController_greet0_invoker = createInvoker(
    GreetController_0.greet,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "com.rallyhealth.dataship.controllers.GreetController",
      "greet",
      Nil,
      "POST",
      """ An example controller showing a sample home page""",
      this.prefix + """greet"""
    )
  )

  // @LINE:8
  private[this] lazy val com_rallyhealth_dataship_controllers_EligibilityController_getEligibility1_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("dataship/v1/eligibility")))
  )
  private[this] lazy val com_rallyhealth_dataship_controllers_EligibilityController_getEligibility1_invoker = createInvoker(
    EligibilityController_1.getEligibility,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "com.rallyhealth.dataship.controllers.EligibilityController",
      "getEligibility",
      Nil,
      "POST",
      """""",
      this.prefix + """dataship/v1/eligibility"""
    )
  )

  // @LINE:11
  private[this] val prefixed_opsStatic_Routes_0_2 = Include(opsStatic_Routes_0.withPrefix(this.prefix + (if (this.prefix.endsWith("/")) "" else "/") + "rest/ops"))

  // @LINE:14
  private[this] lazy val com_rallyhealth_dataship_controllers_HealthCheckController_healthStatus3_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("healthStatus")))
  )
  private[this] lazy val com_rallyhealth_dataship_controllers_HealthCheckController_healthStatus3_invoker = createInvoker(
    HealthCheckController_2.healthStatus,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "com.rallyhealth.dataship.controllers.HealthCheckController",
      "healthStatus",
      Nil,
      "POST",
      """""",
      this.prefix + """healthStatus"""
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case com_rallyhealth_dataship_controllers_GreetController_greet0_route(params) =>
      call { 
        com_rallyhealth_dataship_controllers_GreetController_greet0_invoker.call(GreetController_0.greet)
      }
  
    // @LINE:8
    case com_rallyhealth_dataship_controllers_EligibilityController_getEligibility1_route(params) =>
      call { 
        com_rallyhealth_dataship_controllers_EligibilityController_getEligibility1_invoker.call(EligibilityController_1.getEligibility)
      }
  
    // @LINE:11
    case prefixed_opsStatic_Routes_0_2(handler) => handler
  
    // @LINE:14
    case com_rallyhealth_dataship_controllers_HealthCheckController_healthStatus3_route(params) =>
      call { 
        com_rallyhealth_dataship_controllers_HealthCheckController_healthStatus3_invoker.call(HealthCheckController_2.healthStatus)
      }
  }
}
