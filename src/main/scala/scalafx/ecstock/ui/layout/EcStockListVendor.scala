package scalafx.ecstock.ui.layout

import scalafx.Includes._
import scalafx.ecstock.commons.EcStockExample
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.{Button, Label, Separator, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, RowConstraints, VBox}
import scalafx.ecstock.models.Vendor
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
class EcStockListVendor extends EcStockExample {

  def getContent = {
    val infoCaution = new Label {
      text = "Vendor List"
      wrapText = true
    }

    val vendors = ObservableBuffer[Vendor](DBManager.getVendors())

    val table1 = new TableView[Vendor](vendors) {
      columns ++= List(
        new TableColumn[Vendor, String] {
          text = "Vendor Name"
          cellValueFactory = { _.value.nameProperty }
          prefWidth = 100
        },
        new TableColumn[Vendor, String]() {
          text = "Address"
          cellValueFactory = { _.value.addressProperty }
          prefWidth = 100
        },
        new TableColumn[Vendor, String]() {
          text = "Contact 1"
          cellValueFactory = { _.value.contactProperty }
          prefWidth = 100
        },
        new TableColumn[Vendor, String]() {
          text = "Contact 2"
          cellValueFactory = { _.value.contact2Property }
          prefWidth = 100
        }
      )
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

object EcStockListVendor {
  val objectName = "List Vendor"
}
