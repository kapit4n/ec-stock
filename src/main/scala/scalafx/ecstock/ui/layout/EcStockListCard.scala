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
import scalafx.event.ActionEvent
import scalafx.scene.control.DatePicker
import java.util.Locale
import java.time.LocalDate

/**
 *
 */
class EcStockListCard extends EcStockExample {

  var productCards = ObservableBuffer[ProductCard]()
  var table1 = new TableView[ProductCard]()
  def getContent = {
    val infoCaution = new Label {
      text = Messages.data("List")
      wrapText = true
    }

    productCards = ObservableBuffer[ProductCard](DBManager.getCards())

    table1 = new TableView[ProductCard]() {
      items = productCards
      columns ++= List(
        new TableColumn[ProductCard, String] {
          text = Messages.data("Customer")
          cellValueFactory = { _.value.customerNameProperty }
          prefWidth = 400
        },
        new TableColumn[ProductCard, String]() {
          text = Messages.data("Total Price")
          cellValueFactory = { _.value.totalPriceProperty }
          prefWidth = 200
        }
      )
      prefWidth = 800
    }

    GridPane.setConstraints(table1, 1, 0)

    val infoGrid = new GridPane {
      hgap = 1
      vgap = 1
      margin = Insets(18)
      children ++= Seq(table1)
    }
    val todaySells = new Button("By Today") {
      onAction = (ae: ActionEvent) => {
        table1.items = ObservableBuffer[ProductCard](DBManager.getTodaySell())
      }
    }
    GridPane.setConstraints(todaySells, 0, 0)
    
    val monthSells = new Button("By Month") {
      onAction = (ae: ActionEvent) => {
        table1.items = ObservableBuffer[ProductCard](DBManager.getMonthSell())
      }
    }
    GridPane.setConstraints(monthSells, 1, 0)

    val allSells = new Button("All") {
      onAction = (ae: ActionEvent) => {
        table1.items = ObservableBuffer[ProductCard](DBManager.getCards())
      }
    }
    GridPane.setConstraints(allSells, 2, 0)

    Locale.setDefault(new Locale("es", "ES"));

    val fromPicker = new DatePicker() {        
    }

    GridPane.setConstraints(fromPicker, 3, 0)

    val toPicker = new DatePicker() {        
    }

    GridPane.setConstraints(toPicker, 4, 0)

    val searchRange = new Button("Search Range") {
      onAction = (ae: ActionEvent) => {
        table1.items = ObservableBuffer[ProductCard](DBManager.getCardsByRange(fromPicker.getValue(), toPicker.getValue()))
      }
    }
    GridPane.setConstraints(searchRange, 5, 0)

    val filtersGrid = new GridPane {
      hgap = 6
      vgap = 1
      margin = Insets(18)
      children ++= Seq(todaySells, monthSells, allSells, fromPicker, toPicker, searchRange)
    }

    new VBox {
      vgrow = Priority.Always
      hgrow = Priority.Always
      spacing = 10
      padding = Insets(20)
      children = List(
        new VBox {children = List(infoCaution, filtersGrid, infoGrid)},
        new Separator()
      )
    }
  }
}

object EcStockListCard {
  val objectName = "List Card"
}
