package net.cellar.console

import org.apache.wicket.protocol.http.WebApplication
import org.osgi.framework.{BundleContext, BundleActivator}
import org.apache.wicket.request.target.coding.MixedParamUrlCodingStrategy
import pages.{NodeDetailsPage, HandlersPage, NodesPage, HomePage}

/**
 * @author iocanel
 */

class WicketApplication extends WebApplication with BundleActivator {

  def getHomePage = classOf[HomePage]

  //Mount Bookmarkable Pages
  mountBookmarkablePage("home", classOf[HomePage])
  mountBookmarkablePage("nodes", classOf[NodesPage])
  mountBookmarkablePage("handlers", classOf[HandlersPage])

  var nodeDetailsURLs = new MixedParamUrlCodingStrategy("node", classOf[NodeDetailsPage], Array {
    "id"
  })

  mount(nodeDetailsURLs)

  def stop(context: BundleContext) {
    WicketApplication.bundleContext = context
  }

  def start(context: BundleContext) {
    WicketApplication.bundleContext = context
  }
}

object WicketApplication {
  var bundleContext: BundleContext = _
}
