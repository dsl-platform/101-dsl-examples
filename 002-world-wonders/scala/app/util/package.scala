import com.dslplatform.api.client.{ Bootstrap, JsonSerialization }

package object models {
  implicit val locator = Bootstrap.init(getClass.getResourceAsStream("/dsl-project.props"))
  val jsonSerialization = locator.resolve[JsonSerialization]

  /** We're using a cached thread pool executor,
    * which has a growing pool of threads which we reuse for IO requests */
  implicit val executionContext =
    scala.concurrent.ExecutionContext.fromExecutor(
      java.util.concurrent.Executors.newCachedThreadPool
    )
}
