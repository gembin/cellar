package net.cellar.console.pages

import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.markup.html.list.{ListItem, ListView}
import net.cellar.core.Node
import org.apache.wicket.model.{CompoundPropertyModel, LoadableDetachableModel, Model}
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.PageParameters
import org.apache.wicket.markup.html.form.{Form, Button}
import java.util.{LinkedList, List}

/**
 * @author iocanel
 */

class NodesPage extends BasePage {

  nodesPageLink.add(new AttributeAppender("class", new Model[String]("current"), ""))

  val form = new Form("form")

  val nodeListView = new ListView[Node]("nodeList", createNodeListModel) {
    def populateItem(item: ListItem[Node]) {
      item.setModel(new CompoundPropertyModel(item.getModel))
      val params = new PageParameters();
      params.put("id", item.getModelObject.getId)
      item.add(new Label("host"))
      item.add(new Label("port"))
      item.add(new Label("id"))
      item.add(new Button("manage") {
        override def onSubmit() {
          setResponsePage(new NodeDetailsPage(params))
        }
      })
    }
  }

  add(form)
  form.add(nodeListView)

  /**
   * Creates a Loadable detachble model for List[Node]
   */
  def createNodeListModel = {
    new LoadableDetachableModel[List[Node]]() {
      def load() = {
        var clusterManager = getClusterManager()
        new LinkedList(clusterManager.listNodes())
      }
    }
  }
}