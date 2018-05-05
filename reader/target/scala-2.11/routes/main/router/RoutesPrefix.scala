
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/tho-optum/WorkArea/dev/rally_projects/centri-web-poc/reader/conf/routes
// @DATE:Fri May 04 18:38:24 PDT 2018


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
