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

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import scala.Predef._
import net.cellar.console.CellarConsoleSupport

/**
 * @author iocanel
 */

class BasePage extends WebPage with CellarConsoleSupport {

  def homePageLink = new BookmarkablePageLink("homePageLink", classOf[HomePage])

  def nodesPageLink = new BookmarkablePageLink("nodesPageLink", classOf[NodesPage])

  def handlersPageLink = new BookmarkablePageLink("handlersPageLink", classOf[HandlersPage])

  homePageLink.setOutputMarkupId(true)
  nodesPageLink.setOutputMarkupId(true)
  homePageLink.setOutputMarkupId(true)

  add(homePageLink)
  add(nodesPageLink)
  add(handlersPageLink)
}