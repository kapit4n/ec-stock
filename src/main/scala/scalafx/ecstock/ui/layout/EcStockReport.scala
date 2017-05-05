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


/**
 * Form to show char customer by total buy
 */
class EcStockReport extends EcStockExample {

  def getContent = {
    val customerTotal = DBManager.getCustomerReport()

    val years = ObservableBuffer(customerTotal.keySet.toList)
    val xAxis = CategoryAxis(years)
    val yAxis = NumberAxis("Units Sold", 0.0d, 5000.0d, 1000.0d)

    def xyData(ys: Seq[Number]) = ObservableBuffer(years zip ys map (xy => XYChart.Data(xy._1, xy._2)))

    val series = XYChart.Series("Customers", xyData(customerTotal.values.to[collection.mutable.Seq]))

    new BarChart[String, Number](xAxis, yAxis) {
      data = Seq(series)
      categoryGap = 25.0d
    }
  }
}