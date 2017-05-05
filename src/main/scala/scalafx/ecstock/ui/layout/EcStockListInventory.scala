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
import scalafx.event.ActionEvent
import scalafx.scene.control.DatePicker
import java.util.Locale
import java.time.LocalDate

/**
 *
 */
class EcStockListInventory extends EcStockExample {

  var buys: ObservableBuffer[ProductInventory] = ObservableBuffer[ProductInventory]()
  var containTable:TableView[ProductInventory] = new TableView[ProductInventory]()

  def getContent = {
    val infoCaution = new Label {
      text = Messages.data("List")
      wrapText = true
    }

    buys = ObservableBuffer[ProductInventory](DBManager.getProductInventories(DBManager.TODAY))

    val todayFilter = new Button(Messages.data("today")) {
      onAction = (ae: ActionEvent) => {
        containTable.items = ObservableBuffer[ProductInventory](DBManager.getProductInventories(DBManager.TODAY))
      }
    }
    GridPane.setConstraints(todayFilter, 0, 0)

    val lastMonthFilter = new Button(Messages.data("thisMonth")) {
      onAction = (ae: ActionEvent) => {
        containTable.items = ObservableBuffer[ProductInventory](DBManager.getProductInventories(DBManager.LAST_MONTH))
      }
    }
    GridPane.setConstraints(lastMonthFilter, 1, 0)

    val allFilter = new Button(Messages.data("all")) {
      onAction = (ae: ActionEvent) => {
        containTable.items = ObservableBuffer[ProductInventory](DBManager.getProductInventories(DBManager.ALL))
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
        containTable.items = ObservableBuffer[ProductInventory](DBManager.getProductInventories(DBManager.RANGE))
      }
    }
    GridPane.setConstraints(rangeFilter, 5, 0)

    val filtersGrid = new GridPane {
      hgap = 6
      vgap = 1
      margin = Insets(18)
      children ++= Seq(todayFilter, lastMonthFilter, allFilter, fromPicker, toPicker, rangeFilter)
    }

    containTable = new TableView[ProductInventory](buys) {
      columns ++= List(
        new TableColumn[ProductInventory, String] {
          text = Messages.data("Product")
          cellValueFactory = { _.value.productNameProperty }
          prefWidth = 250
        },
        new TableColumn[ProductInventory, String] {
          text = Messages.data("Vendor")
          cellValueFactory = { _.value.vendorNameProperty }
          prefWidth = 250
        },
        new TableColumn[ProductInventory, String]() {
          text = Messages.data("Quantity")
          cellValueFactory = { _.value.quantityProperty }
          prefWidth = 100
        },
        new TableColumn[ProductInventory, String]() {
          text = Messages.data("Cost")
          cellValueFactory = { _.value.unitCostProperty }
          prefWidth = 100
        },
        new TableColumn[ProductInventory, String]() {
          text = "Total Cost"
          cellValueFactory = { _.value.totalCostProperty }
          prefWidth = 100
        }
      )
      prefWidth = 800
    }

    GridPane.setConstraints(containTable, 1, 0)

    val infoGrid = new GridPane {
      hgap = 4
      vgap = 6
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

object EcStockListInventory {
  val objectName = "List Inventory"
}
