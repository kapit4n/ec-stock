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
  var totalPriceLbl: Label = _
  var totalPriceTxt: TextField = _
  var totalCostLbl: Label = _
  var totalCostTxt: TextField = _
  var revenueLbl: Label = _
  var revenueTxt: TextField = _

  def calculateTotals(cards:ObservableBuffer[ProductCard]) = {
    var totalPrice = 0.0
    var totalCost = 0.0
    for (card <- cards) {
      totalPrice = totalPrice + card.totalPrice
      totalCost = totalCost + card.totalCost
    }
    totalPriceTxt.setText(totalPrice.toString)
    totalCostTxt.setText(totalCost.toString)
    revenueTxt.setText((totalPrice - totalCost).toString)
  }

  def getContent = {
    val infoCaution = new Label {
      text = Messages.data("List")
      wrapText = true
    }

    productCards = ObservableBuffer[ProductCard](DBManager.getCards(DBManager.TODAY))

    val todayFilter = new Button(Messages.data("today")) {
      onAction = (ae: ActionEvent) => {
        productCards = ObservableBuffer[ProductCard](DBManager.getCards(DBManager.TODAY))
        calculateTotals(productCards)
        containTable.items = productCards
      }
    }
    GridPane.setConstraints(todayFilter, 0, 0)
    
    val lastMonthFilter = new Button(Messages.data("thisMonth")) {
      onAction = (ae: ActionEvent) => {
        productCards = ObservableBuffer[ProductCard](DBManager.getCards(DBManager.LAST_MONTH))
        calculateTotals(productCards)
        containTable.items = productCards
      }
    }
    GridPane.setConstraints(lastMonthFilter, 1, 0)

    val allFilter = new Button(Messages.data("all")) {
      onAction = (ae: ActionEvent) => {
        productCards = ObservableBuffer[ProductCard](DBManager.getCards(DBManager.ALL))
        calculateTotals(productCards)
        containTable.items = productCards
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
        productCards = ObservableBuffer[ProductCard](DBManager.getCards(DBManager.RANGE))
        calculateTotals(productCards)
        containTable.items = productCards
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
    
    GridPane.setConstraints(containTable, 0, 0)

    totalPriceLbl = new Label(Messages.data("totalSellPrice"))
    GridPane.setConstraints(totalPriceLbl, 0, 0)

    totalPriceTxt = new TextField() {
      text = ""
      editable = false
    }
    GridPane.setConstraints(totalPriceTxt, 1, 0)

    totalCostLbl = new Label(Messages.data("totalProductCost"))
    GridPane.setConstraints(totalCostLbl, 2, 0)

    totalCostTxt = new TextField() {
      text = ""
      editable = false
    }
    GridPane.setConstraints(totalCostTxt, 3, 0)

    revenueLbl = new Label(Messages.data("revenue"))
    GridPane.setConstraints(revenueLbl, 4, 0)

    revenueTxt = new TextField() {
      text = ""
      editable = false
    }
    GridPane.setConstraints(revenueTxt, 5, 0)

    val totalsGrid = new GridPane {
      hgap = 6
      vgap = 1
      margin = Insets(18)
      children ++= Seq(totalPriceLbl, totalPriceTxt, totalCostLbl, totalCostTxt, revenueLbl, revenueTxt)
    }
    GridPane.setConstraints(totalsGrid, 0, 1)

    calculateTotals(productCards)

    val infoGrid = new GridPane {
      hgap = 2
      vgap = 2
      margin = Insets(18)
      children ++= Seq(containTable, totalsGrid)
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
