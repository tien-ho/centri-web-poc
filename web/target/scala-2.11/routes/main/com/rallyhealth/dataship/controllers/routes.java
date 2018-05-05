
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/tho-optum/WorkArea/dev/rally_projects/centri-web-poc/web/conf/routes
// @DATE:Fri May 04 18:38:12 PDT 2018

package com.rallyhealth.dataship.controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final com.rallyhealth.dataship.controllers.ReverseGreetController GreetController = new com.rallyhealth.dataship.controllers.ReverseGreetController(RoutesPrefix.byNamePrefix());
  public static final com.rallyhealth.dataship.controllers.ReverseHealthCheckController HealthCheckController = new com.rallyhealth.dataship.controllers.ReverseHealthCheckController(RoutesPrefix.byNamePrefix());
  public static final com.rallyhealth.dataship.controllers.ReverseEligibilityController EligibilityController = new com.rallyhealth.dataship.controllers.ReverseEligibilityController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final com.rallyhealth.dataship.controllers.javascript.ReverseGreetController GreetController = new com.rallyhealth.dataship.controllers.javascript.ReverseGreetController(RoutesPrefix.byNamePrefix());
    public static final com.rallyhealth.dataship.controllers.javascript.ReverseHealthCheckController HealthCheckController = new com.rallyhealth.dataship.controllers.javascript.ReverseHealthCheckController(RoutesPrefix.byNamePrefix());
    public static final com.rallyhealth.dataship.controllers.javascript.ReverseEligibilityController EligibilityController = new com.rallyhealth.dataship.controllers.javascript.ReverseEligibilityController(RoutesPrefix.byNamePrefix());
  }

}
