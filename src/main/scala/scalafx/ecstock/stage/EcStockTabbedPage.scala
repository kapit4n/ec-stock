/*
 * Copyright (c) 2012-2015, ScalaFX Ensemble Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the ScalaFX Project nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE SCALAFX PROJECT OR ITS CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package scalafx.ecstock.stage

import scalafx.Includes._
import scalafx.ecstock.commons.{ContentFactory, DisplayablePage}
import scalafx.geometry.Pos
import scalafx.scene.Node
import scalafx.scene.control.{Tab, TabPane}
import scalafx.scene.layout.{Priority, StackPane}
import scalafx.ecstock.i18n.Messages

object EcStockTabbedPage {

  def buildTab(ctrlName: String, ctrlgrop: String) = {

    val tabbedPage = new TabPane() {
      hgrow = Priority.Always
      vgrow = Priority.Always
      id = "source-tabs"
      tabs = Seq(
        new Tab() {
          text = "Principal"
          closable = false
        }
      )
    }

    new EcStockTabbedPage(tabbedPage, ctrlName, ctrlgrop)
  }

  def buildTabContent(node: Node) = {
    val demoTabStack = new StackPane {
      alignmentInParent = Pos.TopLeft
      children = List(node)
    }
    demoTabStack
  }
}

class EcStockTabbedPage(tabPane: TabPane, ctrlName: String, ctrlGroup: String)
  extends DisplayablePage {
  def getPage = {
    import scalafx.ecstock.stage.EcStockTabbedPage._
    tabPane.tabs(0).content = buildTabContent(ContentFactory.createContent(ctrlName, ctrlGroup))
    tabPane
  }
}
