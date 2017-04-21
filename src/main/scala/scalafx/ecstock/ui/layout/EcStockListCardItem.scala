package scalafx.ecstock.ui.layout

import scalafx.Includes._
import scalafx.ecstock.commons.EcStockExample
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.{Button, Label, Separator, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, RowConstraints, VBox}
import scalafx.ecstock.models.ProductCardItem
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
class EcStockListCardItem extends EcStockExample {

  def getContent = {
    val infoCaution = new Label {
      text = "ProductCardItem List"
      wrapText = true
    }

    val productCardItems = ObservableBuffer[ProductCardItem](DBManager.getCardItems())

    val table1 = new TableView[ProductCardItem](productCardItems) {
      columns ++= List(
        new TableColumn[ProductCardItem, String] {
          text = "Product"
          cellValueFactory = { _.value.productNameProperty }
          prefWidth = 200
        },
        new TableColumn[ProductCardItem, String]() {
          text = "Quantity"
          cellValueFactory = { _.value.quantityProperty }
          prefWidth = 200
        },
        new TableColumn[ProductCardItem, String]() {
          text = "Price"
          cellValueFactory = { _.value.priceProperty }
          prefWidth = 200
        },
        new TableColumn[ProductCardItem, String]() {
          text = "Total Price"
          cellValueFactory = { _.value.totalPriceProperty }
          prefWidth = 200
        }
      )
    }

    GridPane.setConstraints(table1, 0, 0, 1, 1)

    val infoGrid = new GridPane {
      hgap = 1
      vgap = 1
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