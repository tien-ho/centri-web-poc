
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/tho-optum/WorkArea/dev/rally_projects/centri-web-poc/web/conf/routes
// @DATE:Fri May 04 18:38:12 PDT 2018

import play.api.routing.JavaScriptReverseRoute
import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset

// @LINE:6
package com.rallyhealth.dataship.controllers.javascript {
  import ReverseRouteContext.empty

  // @LINE:6
  class ReverseGreetController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def greet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "com.rallyhealth.dataship.controllers.GreetController.greet",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "greet"})
        }
      """
    )
  
  }

  // @LINE:14
  class ReverseHealthCheckController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:14
    def healthStatus: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "com.rallyhealth.dataship.controllers.HealthCheckController.healthStatus",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "healthStatus"})
        }
      """
    )
  
  }

  // @LINE:8
  class ReverseEligibilityController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:8
    def getEligibility: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "com.rallyhealth.dataship.controllers.EligibilityController.getEligibility",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "dataship/v1/eligibility"})
        }
      """
    )
  
  }


}
