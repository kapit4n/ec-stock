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
import scalafx.ecstock.i18n.Messages

import scalafx.ecstock.utils.PDF
import scalafx.ecstock.utils.PDF._
import java.util.Date
import scalafx.event.ActionEvent

/** The main ScalaFX EcStock application object. */
object EcStock extends JFXApp {

  /*
  new PDF {
    file("/tmp/HelloWorld.pdf")
    paragraph(s"Hello World ${new Date()}!")
    
    paragraph {
      phrase {
        chunk("This is initial text. ")
        chunk {
          text("testing text element ")
          font(style = BOLD, color = rgb"0x929083")
          background(rgb"ffe400")
        }
        for (i <- 1 to 10) chunk(s"Chunk number $i ")
      }
      phrase("Second Phrase. ")
      chunk("Chunk in the middle ")
      phrase("Third Phrase. ")
    }
    close()
  }*/

  //
  // Example selection tree
  //

  def goToPage(pagePath: String) = {
    centerPane = PageDisplayer.choosePage("layout > " + pagePath)
    splitPane.items.remove(0)
    splitPane.items.add(0, centerPane)
  }


  var centerPane = PageDisplayer.choosePage("layout > Add Card")

  val screen = Screen.primary

  lazy val splitPane = new SplitPane {
    dividerPositions = 0
    id = "page-splitpane"
    items.addAll(centerPane)
  }


  var menuBar = new MenuBar {
    maxWidth = 1200
    maxHeight = 35
    menus = List(
      new Menu("Ventas") {
        graphic = new ImageView {
          image = new Image(this.getClass.getResourceAsStream("/scalafx/ecstock/images/crumb-selected-focused.png"))
          margin = Insets(0, 0, 0, 5)
        }
        items = List(
          new MenuItem("Add Card") {
            onAction = (ae: ActionEvent) => {
              goToPage("Add Card")
            }
          },
          new MenuItem("Add Card By Box") {
            onAction = (ae: ActionEvent) => {
              goToPage("Add Card By Box")
            }
          },
          new MenuItem("List Card") {
            onAction = (ae: ActionEvent) => {
              goToPage("List Card")
            }
          }
        )
      },
      new Menu("Compras") {
        graphic = new ImageView {
          image = new Image(this.getClass.getResourceAsStream("/scalafx/ecstock/images/crumb-selected-focused.png"))
          margin = Insets(0, 0, 0, 5)
        }
        items = List(
          new MenuItem("Add Product Inventory") {
            onAction = (ae: ActionEvent) => {
              goToPage("Add Product Inventory")
            }
          },
          new MenuItem("Add Product Inventory By Box") {
            onAction = (ae: ActionEvent) => {
              goToPage("Add Product Inventory By Box")
            }
          },
          new MenuItem("Inventory") {
            onAction = (ae: ActionEvent) => {
              goToPage("Inventory")
            }
          }
        )
      },
      new Menu("Products") {
        graphic = new ImageView {
          image = new Image(this.getClass.getResourceAsStream("/scalafx/ecstock/images/crumb-selected-focused.png"))
          margin = Insets(0, 0, 0, 5)
        }
        items = List(
          new MenuItem("Add Product") {
            onAction = (ae: ActionEvent) => {
              goToPage("Add Product")
            }
          },
          new MenuItem("Add Brand") {
            onAction = (ae: ActionEvent) => {
              goToPage("Add Brand")
            }
          },
          new MenuItem("List Product") {
            onAction = (ae: ActionEvent) => {
              goToPage("List Product")
            }
          },
          new MenuItem("List Brands") {
            onAction = (ae: ActionEvent) => {
              goToPage("List Brand")
            }
          }
        )
      },
      new Menu("Customers") {
        graphic = new ImageView {
          image = new Image(this.getClass.getResourceAsStream("/scalafx/ecstock/images/crumb-selected-focused.png"))
          margin = Insets(0, 0, 0, 5)
        }
        items = List(
          new MenuItem("Add Customer") {
            onAction = (ae: ActionEvent) => {
              goToPage("Add Customer")
            }
          },
          new MenuItem("List Customer") {
            onAction = (ae: ActionEvent) => {
              goToPage("List Customer")
            }
          }
        )
      },
      new Menu("Categories") {
        graphic = new ImageView {
          image = new Image(this.getClass.getResourceAsStream("/scalafx/ecstock/images/crumb-selected-focused.png"))
          margin = Insets(0, 0, 0, 5)
        }
        items = List(
          new MenuItem("Add Category") {
            onAction = (ae: ActionEvent) => {
              goToPage("Add Category")
            }
          },
          new MenuItem("List Category") {
            onAction = (ae: ActionEvent) => {
              goToPage("List Category")
            }
          }
        )
      },
      new Menu("Reports") {
        graphic = new ImageView {
          image = new Image(this.getClass.getResourceAsStream("/scalafx/ecstock/images/crumb-selected-focused.png"))
          margin = Insets(0, 0, 0, 5)
        }
        items = List(
          new MenuItem("Sales") {
            onAction = (ae: ActionEvent) => {
              goToPage("List Card")
            }
          },
          new MenuItem("Buys") {
            onAction = (ae: ActionEvent) => {
              goToPage("List Inventory")
            }
          },
          new MenuItem("Inventory") {
            onAction = (ae: ActionEvent) => {
              goToPage("Inventory")
            }
          }
        )
      },
      new Menu("Graphics") {
        graphic = new ImageView {
          image = new Image(this.getClass.getResourceAsStream("/scalafx/ecstock/images/crumb-selected-focused.png"))
          margin = Insets(0, 0, 0, 5)
        }
        items = List(
          new MenuItem("Sales By Customer") {
            onAction = (ae: ActionEvent) => {
              goToPage("Report Customer")
            }
          },
          new MenuItem("Report Price Vs Cost") {
            onAction = (ae: ActionEvent) => {
              goToPage("Report Price Vs Cost")
            }
          },
          new MenuItem("Finance Report") {
            onAction = (ae: ActionEvent) => {
              goToPage("Report Finance")
            }
          }
        )
      }
      )
  }

  //
  // Layout the main stage
  //
  stage = new PrimaryStage {
    title = "DYAMSOFT EcStock"
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
              menuBar
            )
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