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


/**
 *
 */
class EcStockListInventory extends EcStockExample {

  def getContent = {
    val infoCaution = new Label {
      text = "ProductInventory List"
      wrapText = true
    }

    val products = ObservableBuffer[ProductInventory](
      new ProductInventory("1","100", "20.0"),
      new ProductInventory("2", "200", "50.0")
    )

    val table1 = new TableView[ProductInventory](products) {
      columns ++= List(
        new TableColumn[ProductInventory, String] {
          text = "ProductInventory Name"
          cellValueFactory = { _.value.product }
          prefWidth = 100
        },
        new TableColumn[ProductInventory, String]() {
          text = "Quantity"
          cellValueFactory = { _.value.quantity }
          prefWidth = 100
        },
        new TableColumn[ProductInventory, String]() {
          text = "Cost 1"
          cellValueFactory = { _.value.cost }
          prefWidth = 100
        }
      )
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