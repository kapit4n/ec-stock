package scalafx.ecstock.ui.layout

import scalafx.Includes._
import scalafx.ecstock.commons.EcStockExample
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.BarChart
import scalafx.scene.chart.CategoryAxis
import scalafx.scene.chart.NumberAxis
import scalafx.scene.chart.XYChart
import scalafx.scene.chart.XYChart._
import scalafx.ecstock.models.DBManager
import scala.collection.mutable.HashMap
import scala.collection.mutable.Seq
import scalafx.ecstock.i18n.Messages
import scalafx.scene.layout.{GridPane, Priority, VBox}
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.{Button, Label, Separator, TextField}
import scalafx.scene.control.DatePicker
import java.util.Locale
import java.time.LocalDate
import scalafx.event.ActionEvent

/**
 * Form to show char customer by total bought
 */
class EcStockReportPriceVsCost extends EcStockExample {

  var filtersGrid: GridPane = _
  var childrenVBox: VBox = _

  def buildChart(productsValues: HashMap[String, List[Number]]): BarChart[String, Number] = {
    val productNames = productsValues.keySet.toList
    val years = ObservableBuffer(productNames)
    val xAxis = CategoryAxis(years)
    val yAxis = NumberAxis(Messages.data("dolars"), 0.0d, 5000.0d, 100.0d)

    def xyData(ys: Seq[Number]) = ObservableBuffer(years zip ys map (xy => XYChart.Data(xy._1, xy._2)))
    val totalBuf = scala.collection.mutable.ListBuffer.empty[Number]
    val totalCostBuf = scala.collection.mutable.ListBuffer.empty[Number]
    for (customerName <- productNames) totalBuf += productsValues(customerName)(0)
    for (customerName <- productNames) totalCostBuf += productsValues(customerName)(1)

    val series = XYChart.Series(Messages.data("prices"), xyData(totalBuf.toList.to[collection.mutable.Seq]))
    val seriesCost = XYChart.Series(Messages.data("costs"), xyData(totalCostBuf.toList.to[collection.mutable.Seq]))

    val chart = new BarChart[String, Number](xAxis, yAxis) {
      data = Seq(series, seriesCost)
      categoryGap = 25.0d
    }
    return chart
  }

  def getContent = {
   val todayFilter = new Button(Messages.data("today")) {
      onAction = (ae: ActionEvent) => {
        var chart = buildChart(DBManager.getProductReport(DBManager.TODAY))
        childrenVBox.children = List(filtersGrid, chart)
      }
    }
    GridPane.setConstraints(todayFilter, 0, 0)
    
    val lastMonthFilter = new Button(Messages.data("thisMonth")) {
      onAction = (ae: ActionEvent) => {
	var chart = buildChart(DBManager.getProductReport(DBManager.LAST_MONTH))
        childrenVBox.children = List(filtersGrid, chart)
      }
    }
    GridPane.setConstraints(lastMonthFilter, 1, 0)

    val allFilter = new Button(Messages.data("all")) {
      onAction = (ae: ActionEvent) => {
	var chart = buildChart(DBManager.getProductReport(DBManager.ALL))
        childrenVBox.children = List(filtersGrid, chart)
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
        var chart = buildChart(DBManager.getProductReport(DBManager.RANGE))
        childrenVBox.children = List(filtersGrid, chart)

      }
    }
    GridPane.setConstraints(rangeFilter, 5, 0)

    filtersGrid = new GridPane {
      hgap = 6
      vgap = 1
      margin = Insets(18)
      children ++= Seq(todayFilter, lastMonthFilter, allFilter, fromPicker, toPicker, rangeFilter)
    }
    var chart = buildChart(DBManager.getProductReport(DBManager.TODAY))
    childrenVBox = new VBox {children = List(filtersGrid, chart)}

    new VBox {
      vgrow = Priority.Always
      hgrow = Priority.Always
      spacing = 10
      padding = Insets(20)
      children = List(
        childrenVBox,
        new Separator()
      )
    }    
  }
}
