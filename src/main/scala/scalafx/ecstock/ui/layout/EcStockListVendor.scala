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


/**
 * An example of a GridPane layout. There is more than one approach to using a
 * GridPane. First, the code can specify which rows and/or columns should
 * contain the content. Second, the code can alter the constraints of the
 * rows and/or columns themselves, either by specifying the preferred minimum
 * or  maximum heights or widths, or by specifying the percentage of the
 * GridPane that belongs to certain rows or columns.
 *
 * @see scalafx.scene.layout.GridPane
 * @resource /scalafx/ecstock/images/icon-48x48.png
 */
class EcStockListVendor extends EcStockExample {

  def getContent = {
    // infoGrid places the children by specifying the rows and columns in GridPane.setConstraints()
    val infoCaution = new Label {
      text = "Vendor List"
      wrapText = true
    }



    val vendors = ObservableBuffer[Vendor](
      new Vendor("Vendor 1", "Address 1", "Contact 1", "Contact 2"),
      new Vendor("Vendor 2", "Address 2", "Contact 1", "Contact 2")
    )

    val table1 = new TableView[Vendor](vendors) {
      columns ++= List(
        new TableColumn[Vendor, String] {
          text = "Vendor Name"
          cellValueFactory = { _.value.name }
          prefWidth = 100
        },
        new TableColumn[Vendor, String]() {
          text = "Address"
          cellValueFactory = { _.value.address }
          prefWidth = 100
        },
        new TableColumn[Vendor, String]() {
          text = "Contact 1"
          cellValueFactory = { _.value.contact }
          prefWidth = 100
        },
        new TableColumn[Vendor, String]() {
          text = "Contact 2"
          cellValueFactory = { _.value.contact2 }
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