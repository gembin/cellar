package net.cellar.console.pages

import org.apache.wicket.behavior.SimpleAttributeModifier


/**
 * @author iocanel
 */

class HomePage extends BasePage {

  homePageLink.add(new SimpleAttributeModifier("class", "current"))

}