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
