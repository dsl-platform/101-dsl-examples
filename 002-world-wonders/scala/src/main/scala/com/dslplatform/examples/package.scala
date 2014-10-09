package com.dslplatform

import akka.actor.ActorSystem
import com.dslplatform.api.client.Bootstrap
import com.dslplatform.api.client.HttpClient
import com.dslplatform.api.patterns.ServiceLocator
import java.util.concurrent.Executors
import java.util.concurrent.ExecutorService
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import org.slf4j.Logger

package object examples {
  implicit val locator = Bootstrap.init(getClass.getResourceAsStream("/dsl-project.props"))

  /**
    * We're using a cached thread pool executor, which has a growing pool
    * of threads which we reused for IO requests
    */
  private val executorService = Executors.newCachedThreadPool
  implicit val executionContext = ExecutionContext.fromExecutor(executorService)

  /**
    * Initialize ActorSystem
    */
  val system = ActorSystem()

  /**
    *  Default request timeout
    */
  implicit val defaultDuration = 30.seconds

  /**
    * ExecutorService will keep on working up to a minute after program has
    * finished. This method is an example how one could quickly exit the
    * program and can be called to speed up application ending.
    *
    * Alternatively, you can also invoke sys.exit()
    */
  def shutdown(): Unit = {
    system.shutdown()
    executorService.shutdown()
    locator.resolve[HttpClient].shutdown()
    locator.resolve[ExecutorService].shutdown()
  }
}
