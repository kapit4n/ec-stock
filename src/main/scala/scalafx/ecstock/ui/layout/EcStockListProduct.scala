package scalafx.ecstock.ui.layout

import scalafx.Includes._
import scalafx.ecstock.commons.EcStockExample
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.{Button, Label, Separator, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, RowConstraints, VBox}
import scalafx.ecstock.models.Category
import scalafx.ecstock.models.Vendor
import scalafx.ecstock.models.Brand
import scalafx.ecstock.models.Product
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableCell, TableColumn, TableView}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle


/**
 *
 */
class EcStockListProduct extends EcStockExample {

  def getContent = {
    val infoCaution = new Label {
      text = "Product List"
      wrapText = true
    }
    val brand1 = new Brand("Brand 1", "description");
    val vendor1 = new Vendor("vendor1", "address", "Contact 1", "Contact2");
    val category1 = new Category("Category1", "Category Description", "img");
    val products = ObservableBuffer[Product](
      new Product("Product 1", 10, vendor1, brand1, category1, "Description 1", "imgSrc")
    )

    val table1 = new TableView[Product](products) {
      columns ++= List(
        new TableColumn[Product, String] {
          text = "Name"
          cellValueFactory = { _.value.nameProperty }
          prefWidth = 100
        },
        new TableColumn[Product, String]() {
          text = "Retail Price"
          cellValueFactory = { _.value.retailPriceProperty }
          prefWidth = 100
        },
        new TableColumn[Product, Vendor]() {
          text = "Vendor"
          cellValueFactory = { _.value.vendorProperty }
          prefWidth = 100
        },
        new TableColumn[Product, Brand]() {
          text = "Brand"
          cellValueFactory = { _.value.brandProperty }
          prefWidth = 100
        },
        new TableColumn[Product, Category]() {
          text = "Category"
          cellValueFactory = { _.value.categoryProperty }
          prefWidth = 100
        },
        new TableColumn[Product, String]() {
          text = "Description"
          cellValueFactory = { _.value.descriptionProperty }
          prefWidth = 100
        }
      )
    }

    GridPane.setConstraints(table1, 0, 0, 1, 1)

    val infoGrid = new GridPane {
      hgap = 1
      vgap = 1
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