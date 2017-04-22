package scalafx.ecstock.ui.layout

import scalafx.Includes._
import scalafx.ecstock.commons.EcStockExample
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.{Button, Label, Separator, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, RowConstraints, VBox}
import scalafx.ecstock.models.Customer
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
class EcStockListCustomer extends EcStockExample {

  def getContent = {
    val infoCaution = new Label {
      text = Messages.data("List")
      wrapText = true
    }

    val customers = ObservableBuffer[Customer](DBManager.getCustomers())

    val table1 = new TableView[Customer](customers) {
      columns ++= List(
        new TableColumn[Customer, String] {
          text = Messages.data("Name")
          cellValueFactory = { _.value.nameProperty }
          prefWidth = 400
        },
        new TableColumn[Customer, String]() {
          text = Messages.data("Address")
          cellValueFactory = { _.value.addressProperty }
          prefWidth = 400
        }
      )
      prefWidth = 800
    }

    GridPane.setConstraints(table1, 0, 1, 1, 1)

    val infoGrid = new GridPane {
      hgap = 4
      vgap = 6
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

object EcStockListCustomer {
  val objectName = "List Customer"
}
