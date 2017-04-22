package scalafx.ecstock.ui.layout

import scalafx.Includes._
import scalafx.ecstock.commons.EcStockExample
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.{Button, Label, Separator, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, RowConstraints, VBox}
import scalafx.ecstock.models.ProductInventory
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableCell, TableColumn, TableView}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafx.ecstock.models.DBManager
import scalafx.ecstock.i18n.Messages

/**
 *
 */
class EcStockListInventory extends EcStockExample {

  def getContent = {
    val infoCaution = new Label {
      text = Messages.data("List")
      wrapText = true
    }

    val invs = ObservableBuffer[ProductInventory](DBManager.getProductInventories())

    val table1 = new TableView[ProductInventory](invs) {
      columns ++= List(
        new TableColumn[ProductInventory, String] {
          text = Messages.data("Name")
          cellValueFactory = { _.value.productProperty }
          prefWidth = 250
        },
        new TableColumn[ProductInventory, String] {
          text = Messages.data("Vendor")
          cellValueFactory = { _.value.vendorProperty }
          prefWidth = 250
        },
        new TableColumn[ProductInventory, String]() {
          text = Messages.data("Quantity")
          cellValueFactory = { _.value.quantityProperty }
          prefWidth = 150
        },
        new TableColumn[ProductInventory, String]() {
          text = Messages.data("Cost")
          cellValueFactory = { _.value.costProperty }
          prefWidth = 150
        }
      )
      prefWidth = 800
    }

    GridPane.setConstraints(table1, 0, 1, 1, 1)

    val infoGrid = new GridPane {
      hgap = 4
      vgap = 6
      margin = Insets(18)
      children ++= Seq(table1)
    }

    new VBox {
      vgrow = Priority.Always
      hgrow = Priority.Always
      spacing = 10
      padding = Insets(20)
      children = List(
        new VBox {children = List(infoCaution, infoGrid)},
        new Separator()
      )
    }
  }
}

object EcStockListInventory {
  val objectName = "List Inventory"
}
