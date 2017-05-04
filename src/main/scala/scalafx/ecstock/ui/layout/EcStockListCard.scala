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
  var containTable = new TableView[ProductCard]()
  def getContent = {
    val infoCaution = new Label {
      text = Messages.data("List")
      wrapText = true
    }

    productCards = ObservableBuffer[ProductCard](DBManager.getCards(DBManager.TODAY))

    val todayFilter = new Button(Messages.data("today")) {
      onAction = (ae: ActionEvent) => {
        containTable.items = ObservableBuffer[ProductCard](DBManager.getCards(DBManager.TODAY))
      }
    }
    GridPane.setConstraints(todayFilter, 0, 0)
    
    val lastMonthFilter = new Button(Messages.data("thisMonth")) {
      onAction = (ae: ActionEvent) => {
        containTable.items = ObservableBuffer[ProductCard](DBManager.getCards(DBManager.LAST_MONTH))
      }
    }
    GridPane.setConstraints(lastMonthFilter, 1, 0)

    val allFilter = new Button(Messages.data("all")) {
      onAction = (ae: ActionEvent) => {
        containTable.items = ObservableBuffer[ProductCard](DBManager.getCards(DBManager.ALL))
      }
    }
    GridPane.setConstraints(allFilter, 2, 0)

    Locale.setDefault(new Locale("es", "ES"));

    val fromPicker = new DatePicker() {        
    }

    GridPane.setConstraints(fromPicker, 3, 0)

    val toPicker = new DatePicker() {        
    }

    GridPane.setConstraints(toPicker, 4, 0)

    val rangeFilter = new Button(Messages.data("searchRange")) {
      onAction = (ae: ActionEvent) => {
        DBManager.fromDate = fromPicker.getValue()
        DBManager.toDate = toPicker.getValue()
        containTable.items = ObservableBuffer[ProductCard](DBManager.getCards(DBManager.RANGE))
      }
    }
    GridPane.setConstraints(rangeFilter, 5, 0)

    val filtersGrid = new GridPane {
      hgap = 6
      vgap = 1
      margin = Insets(18)
      children ++= Seq(todayFilter, lastMonthFilter, allFilter, fromPicker, toPicker, rangeFilter)
    }

    containTable = new TableView[ProductCard]() {
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
    
    GridPane.setConstraints(containTable, 1, 0)

    val infoGrid = new GridPane {
      hgap = 1
      vgap = 1
      margin = Insets(18)
      children ++= Seq(containTable)
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
