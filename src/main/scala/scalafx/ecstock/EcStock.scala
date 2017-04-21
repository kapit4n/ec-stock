package scalafx.ecstock

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.ecstock.commons.PageDisplayer
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout._
import scalafx.stage.Screen

/** The main ScalaFX EcStock application object. */
object EcStock extends JFXApp {

  //
  // Example selection tree
  //
  var centerPane = PageDisplayer.choosePage("dashBoard")
  val rootTreeItem = new TreeItem[String]("ScalaFX EcStock") {
    expanded = true
    children = EcStockTree.create().getTree
  }

  val screen = Screen.primary
  val controlsView = new TreeView[String]() {
    minWidth = 200
    maxWidth = 200
    editable = true
    root = rootTreeItem
    id = "page-tree"
  }
  controlsView.selectionModel().selectionMode = SelectionMode.Single
  controlsView.selectionModel().selectedItem.onChange {
    (_, _, newItem) => {
      val pageCode = (newItem.isLeaf, Option(newItem.getParent)) match {
        case (true, Some(parent)) => parent.getValue.toLowerCase + " > " + newItem.getValue
        case (false, Some(_))     => "dashBoard - " + newItem.getValue
        case (_, _)               => "dashBoard"
      }
      centerPane = PageDisplayer.choosePage(pageCode)
      splitPane.items.remove(1)
      splitPane.items.add(1, centerPane)
    }
  }

  val scrollPane = new ScrollPane {
    minWidth = 200
    maxWidth = 200
    fitToWidth = true
    fitToHeight = true
    id = "page-tree"
    content = controlsView
  }
  lazy val splitPane = new SplitPane {
    dividerPositions = 0
    id = "page-splitpane"
    items.addAll(scrollPane, centerPane)
  }

  //
  // Layout the main stage
  //
  stage = new PrimaryStage {
    title = "ScalaFX EcStock"
    icons += new Image("/scalafx/ecstock/images/ScalaFX-icon-64x64.png")
    scene = new Scene(1500, 800) {
      stylesheets += this.getClass.getResource("/scalafx/ecstock/css/ecstock.css").toExternalForm
      root = new BorderPane {
        top = new VBox {
          vgrow = Priority.Always
          hgrow = Priority.Always
          children = new ToolBar {
            prefHeight = 76
            maxHeight = 76
            id = "mainToolBar"
            content = List(
              new ImageView {
                image = new Image(
                  this.getClass.getResourceAsStream("/scalafx/ecstock/images/logo.png"))
                margin = Insets(0, 0, 0, 10)
              },
              new Region {
                minWidth = 300
              },
              new Button {
                minWidth = 120
                minHeight = 66
                id = "newButton"
              })
          }
        }
        center = new BorderPane {
          center = splitPane
        }
        styleClass += "application"
      }
    }
  }
}