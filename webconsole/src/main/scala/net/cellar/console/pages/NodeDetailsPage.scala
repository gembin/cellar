package net.cellar.console.pages

import org.apache.wicket.PageParameters
import org.apache.wicket.model.Model

/**
 * @author: iocanel
 */

class NodeDetailsPage(var params: PageParameters) extends BasePage {

  var id = params.getString("id")
  add(new ListNodeHandlersPanel("nodeHandlersPanel", new Model[String](id)))
  add(new NodeTopicSwitchPanel("nodeTopicSwitchPanel", new Model[String](id)))


}