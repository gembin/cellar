package net.cellar.console.pages


import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.model.{LoadableDetachableModel, Model}
import net.cellar.core.control.{ManageHandlersResult, ManageHandlersCommand}
import java.util.{LinkedList, List}

/**
 * @author iocanel
 */

class HandlersPage extends BasePage {
  handlersPageLink.add(new AttributeAppender("class", new Model[String]("current"), ""))

  /**
   * Returns a Loadable detachable model of Handlers
   */
  def createHandlersModel = {
    new LoadableDetachableModel[List[ManageHandlersResult]]() {
      def load() = {
        val clusterManager = getClusterManager
        val executionContext = getExecutionContext
        var handlersCommand = new ManageHandlersCommand(clusterManager.generateId)
        var map = executionContext.execute[ManageHandlersResult, ManageHandlersCommand](handlersCommand)
        new LinkedList(map.values)
      }
    }
  }
}

