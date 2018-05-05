
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/tho-optum/WorkArea/dev/rally_projects/centri-web-poc/reader/conf/routes
// @DATE:Fri May 04 18:38:24 PDT 2018

package com.rallyhealth.dataship.reader.controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final com.rallyhealth.dataship.reader.controllers.ReverseKafkaReaderController KafkaReaderController = new com.rallyhealth.dataship.reader.controllers.ReverseKafkaReaderController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final com.rallyhealth.dataship.reader.controllers.javascript.ReverseKafkaReaderController KafkaReaderController = new com.rallyhealth.dataship.reader.controllers.javascript.ReverseKafkaReaderController(RoutesPrefix.byNamePrefix());
  }

}
