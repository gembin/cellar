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