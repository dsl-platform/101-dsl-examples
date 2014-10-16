package com.dslplatform

import com.dslplatform.api.client.{ Bootstrap, HttpClient }
import java.util.concurrent.{ Executors, ExecutorService }
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext

/**
  * A couple implicits that will be used in CRUD operations (create(), update(), delete())
  */
package object examples {

  implicit val locator = Bootstrap.init(getClass.getResourceAsStream("/dsl-project.props"))
  /**
    * We're using a cached thread pool executor, which has a growing pool
    * of threads which we reused for IO requests
    */
  implicit val executionContext = ExecutionContext.fromExecutor(Executors.newCachedThreadPool)

  /**
    *  Default request timeout
    */
  implicit val defaultDuration = 30.seconds
}
