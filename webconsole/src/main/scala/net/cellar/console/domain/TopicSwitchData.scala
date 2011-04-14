package net.cellar.console.domain

/**
 * @author: iocanel
 */

class TopicSwitchData {

  private[this] var name: String = _
  private[this] var status: String = _

  def getName = name

  def setName(name: String) = {
    this.name = name
  }

  def getStatus = status

  def setStatus(status: String) = {
    this.status = status
  }
}