package scalafx.ecstock.ui.layout

import scalafx.Includes._
import scalafx.ecstock.commons.EcStockExample
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.{Button, Label, Separator, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, RowConstraints, VBox}
import scalafx.ecstock.models.Category
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableCell, TableColumn, TableView}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle


/**
 */
class EcStockListCategory extends EcStockExample {

  def getContent = {
    val infoCaution = new Label {
      text = "Category List"
      wrapText = true
    }

    val categories = ObservableBuffer[Category](
      new Category("Category 1", "Description 1", "imgSrc"),
      new Category("Category 2", "Description 2", "imgSrc")
    )

    val table1 = new TableView[Category](categories) {
      columns ++= List(
        new TableColumn[Category, String] {
          text = "Category 1"
          cellValueFactory = { _.value.name }
          prefWidth = 100
        },
        new TableColumn[Category, String]() {
          text = "Description"
          cellValueFactory = { _.value.description }
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