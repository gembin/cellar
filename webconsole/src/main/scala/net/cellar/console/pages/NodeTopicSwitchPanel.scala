/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.cellar.console.pages

import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.html.list.{ListItem, ListView}
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton
import org.apache.wicket.model.{LoadableDetachableModel, CompoundPropertyModel, Model, IModel}
import java.util.{Set, List, LinkedList, Arrays}
import net.cellar.core.Node
import net.cellar.console.CellarConsoleSupport
import net.cellar.console.domain.TopicSwitchData
import net.cellar.core.control._

/**
 * @author: iocanel
 */

class NodeTopicSwitchPanel(var wicketId: String, var model: IModel[String]) extends Panel(wicketId, model) with CellarConsoleSupport {

  def this(wicketId: String) = this (wicketId, new Model[String]())

  var id = model.getObject
  val form = new Form("form")
  val container = new WebMarkupContainer("container")

  val topicControlListView = new ListView[TopicSwitchData]("topicSwitchList", createHandlersModel) {
    def populateItem(item: ListItem[TopicSwitchData]) {
      item.setModel(new CompoundPropertyModel(item.getModel))
      item.add(new Label("name"))
      item.add(new Label("status"))
      item.add(new AjaxFallbackButton("on", form) {
        def onSubmit(target: AjaxRequestTarget, form: Form[_]) {
          if ("Producer".equals(item.getModelObject.getName)) {
            switchProducer(id, true)
          } else switchConsumer(id, true)
          container.detach
          target.addComponent(container)
        }
      })
      item.add(new AjaxFallbackButton("off", form) {
        def onSubmit(target: AjaxRequestTarget, form: Form[_]) {
          if ("Producer".equals(item.getModelObject.getName)) {
            switchProducer(id, false)
          } else switchConsumer(id, false)
          container.detach
          target.addComponent(container)
        }
      })
    }
  }

  container.setOutputMarkupId(true)

  form.add(container)
  container.add(topicControlListView)
  add(form)


  def switchProducer(nodeId: String, status: Boolean) {
    var switchCommand = new ProducerSwitchCommand(getClusterManager.generateId)
    switchCommand.setDestination(getClusterManager.listNodes(Arrays.asList(nodeId)))
    val switchStatus = if (status) SwitchStatus.ON else SwitchStatus.OFF
    switchCommand.setStatus(switchStatus)
    executeProducerCommand(switchCommand, nodeId)
  }

  def switchConsumer(nodeId: String, status: Boolean) {
    var switchCommand = new ConsumerSwitchCommand(getClusterManager.generateId)
    switchCommand.setDestination(getClusterManager.listNodes(Arrays.asList(nodeId)))
    val switchStatus = if (status) SwitchStatus.ON else SwitchStatus.OFF
    switchCommand.setStatus(switchStatus)
    executeConsumerCommand(switchCommand, nodeId)
  }

  /**
   * Returns a Loadable detachable model of Handlers
   */
  def createHandlersModel = {
    new LoadableDetachableModel[List[TopicSwitchData]]() {
      def load() = {
        var list = new LinkedList[TopicSwitchData]()
        var producerCommand = new ProducerSwitchCommand(getClusterManager.generateId)
        var consumerCommand = new ConsumerSwitchCommand(getClusterManager.generateId)
        var producerData = executeProducerCommand(producerCommand, id)
        var consumerData = executeConsumerCommand(consumerCommand, id)

        if (producerData != null)
          list.add(producerData)
        if (consumerData != null)
          list.add(consumerData)
        list
      }
    }
  }

  /**
   * Sends a consumer command to a node with the specified nodeId.
   */
  def executeConsumerCommand(consumerCommand: ConsumerSwitchCommand, nodeIds: String): TopicSwitchData = {
    var topicSwitchData: TopicSwitchData = null
    var targetNodes: Set[Node] = getClusterManager.listNodes(Arrays.asList(nodeIds))

    if (targetNodes != null && !targetNodes.isEmpty) {
      var node = targetNodes.toArray(new Array[Node](0))(0)
      consumerCommand.setDestination(targetNodes)
      var map = getExecutionContext.execute[ConsumerSwitchResult, ConsumerSwitchCommand](consumerCommand)
      if (map != null || !map.isEmpty) {
        var consumerSwitchResult = map.get(node)
        if (consumerSwitchResult != null) {
          topicSwitchData = new TopicSwitchData()
          topicSwitchData.setName("Consumer")
          topicSwitchData.setStatus(consumerSwitchResult.getStatus.toString)
        }
      }
    }
    topicSwitchData
  }

  /**
   * Sends a producer command to a node with the specified nodeId.
   */
  def executeProducerCommand(producerCommand: ProducerSwitchCommand, nodeIds: String): TopicSwitchData = {
    var topicSwitchData: TopicSwitchData = null
    var targetNodes: Set[Node] = getClusterManager.listNodes(Arrays.asList(nodeIds))

    if (targetNodes != null && !targetNodes.isEmpty) {
      var node = targetNodes.toArray(new Array[Node](0))(0)
      producerCommand.setDestination(targetNodes)
      var map = getExecutionContext.execute[ProducerSwitchResult, ProducerSwitchCommand](producerCommand)
      if (map != null || !map.isEmpty) {
        var consumerSwitchResult = map.get(node)
        if (consumerSwitchResult != null) {
          topicSwitchData = new TopicSwitchData()
          topicSwitchData.setName("Producer")
          topicSwitchData.setStatus(consumerSwitchResult.getStatus.toString)
        }
      }
    }
    topicSwitchData
  }
}