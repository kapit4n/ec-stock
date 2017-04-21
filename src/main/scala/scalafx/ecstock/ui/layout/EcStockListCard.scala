package scalafx.ecstock.ui.layout

import scalafx.Includes._
import scalafx.ecstock.commons.EcStockExample
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.{Button, Label, Separator, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, RowConstraints, VBox}
import scalafx.ecstock.models.ProductCard
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
class EcStockListCard extends EcStockExample {

  def getContent = {
    val infoCaution = new Label {
      text = "ProductCard List"
      wrapText = true
    }

    val productCards = ObservableBuffer[ProductCard](DBManager.getCards())

    val table1 = new TableView[ProductCard](productCards) {
      columns ++= List(
        new TableColumn[ProductCard, String] {
          text = "Customer"
          cellValueFactory = { _.value.customerNameProperty }
          prefWidth = 100
        },
        new TableColumn[ProductCard, String]() {
          text = "Total Price"
          cellValueFactory = { _.value.totalPriceProperty }
          prefWidth = 100
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

object EcStockListCard {
  val objectName = "List Card"
}
