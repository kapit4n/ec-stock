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
 * Form to show char product by total bought
 */
class EcStockReportFinance extends EcStockExample {

  def getContent = {
    val revenueValues = DBManager.getFinanceReport()

    val productNames = revenueValues.keySet.toList
    val years = ObservableBuffer(productNames)
    val xAxis = CategoryAxis(years)
    val yAxis = NumberAxis(Messages.data("dolars"), 0.0d, 5000.0d, 100.0d)

    def xyData(ys: Seq[Number]) = ObservableBuffer(years zip ys map (xy => XYChart.Data(xy._1, xy._2)))
    val totalBuf = scala.collection.mutable.ListBuffer.empty[Number]
    val totalCostBuf = scala.collection.mutable.ListBuffer.empty[Number]
    for (productName <- productNames) totalBuf += revenueValues(productName)
    val series = XYChart.Series(Messages.data("revenues"), xyData(totalBuf.toList.to[collection.mutable.Seq]))

    new BarChart[String, Number](xAxis, yAxis) {
      data = Seq(series)
      categoryGap = 25.0d
    }
  }
}