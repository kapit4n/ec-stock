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


/**
 * Form to show char customer by total bought
 */
class EcStockReportPriceVsCost extends EcStockExample {

  def getContent = {
    val productsValues = DBManager.getProductReport()

    val customerNames = productsValues.keySet.toList
    val years = ObservableBuffer(customerNames)
    val xAxis = CategoryAxis(years)
    val yAxis = NumberAxis(Messages.data("dolars"), 0.0d, 5000.0d, 100.0d)

    def xyData(ys: Seq[Number]) = ObservableBuffer(years zip ys map (xy => XYChart.Data(xy._1, xy._2)))
    val totalBuf = scala.collection.mutable.ListBuffer.empty[Number]
    val totalCostBuf = scala.collection.mutable.ListBuffer.empty[Number]
    for (customerName <- customerNames) totalBuf += productsValues(customerName)(0)
    for (customerName <- customerNames) totalCostBuf += productsValues(customerName)(1)

    val series = XYChart.Series(Messages.data("prices"), xyData(totalBuf.toList.to[collection.mutable.Seq]))
    val seriesCost = XYChart.Series(Messages.data("costs"), xyData(totalCostBuf.toList.to[collection.mutable.Seq]))

    new BarChart[String, Number](xAxis, yAxis) {
      data = Seq(series, seriesCost)
      categoryGap = 25.0d
    }
  }
}