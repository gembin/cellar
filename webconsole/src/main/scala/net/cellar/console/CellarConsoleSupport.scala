package net.cellar.console

import org.osgi.framework.BundleContext
import net.cellar.core.ClusterManager
import net.cellar.core.command.ExecutionContext

/**
 * @author: iocanel
 */

trait CellarConsoleSupport {
  /**
   * Looks up and returns the bundle context.
   */
  def getBundleContext(): BundleContext = {
    WicketApplication.bundleContext
  }

  /**
   * Looks up and returns the ClusterManager.
   */
  def getClusterManager(): ClusterManager = {
    val bundleContext = getBundleContext()
    val serviceReference = bundleContext.getServiceReference("net.cellar.core.ClusterManager")
    val clusterManager = bundleContext.getService(serviceReference).asInstanceOf[ClusterManager]
    bundleContext.ungetService(serviceReference)
    clusterManager
  }

  /**
   * Looks up and returns the ExecutionContext.
   */
  def getExecutionContext(): ExecutionContext = {
    val bundleContext = getBundleContext()
    val serviceReference = bundleContext.getServiceReference("net.cellar.core.command.ExecutionContext")
    val executionContext = bundleContext.getService(serviceReference).asInstanceOf[ExecutionContext]
    bundleContext.ungetService(serviceReference)
    executionContext
  }
}
