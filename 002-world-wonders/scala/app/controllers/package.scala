import com.dslplatform.api.client.{ Bootstrap, JsonSerialization }
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext

package object controllers {
  implicit val locator = Bootstrap.init(getClass.getResourceAsStream("/dsl-project.props"))
  val jsonSerialization = locator.resolve[JsonSerialization]

  /**
    * We're using a cached thread pool executor, which has a growing pool
    * of threads which we reused for IO requests
    */
  private val executorService = Executors.newCachedThreadPool
  implicit val executionContext = ExecutionContext.fromExecutor(executorService)
}
