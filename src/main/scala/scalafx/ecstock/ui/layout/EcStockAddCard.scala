package scalafx.ecstock.ui.layout

import scalafx.Includes._
import scalafx.ecstock.commons.EcStockExample
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.{Button, Label, Separator, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, RowConstraints, VBox}
import scalafx.event.ActionEvent
import scalafx.scene.control._
import java.io.IOException
import scalafx.ecstock.models.Product
import scalafx.ecstock.models.ProductCard
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableCell, TableColumn, TableView}



/**
 *
 */
class EcStockAddCard extends EcStockExample {

  def addAmount(product: String, quantity: Int, productCards: ObservableBuffer[ProductCard]) = {
    var found = false
    for (card <- productCards.find(_.product.value == product)) {
        card.quantity.value = (card.quantity.value.toInt + 1).toString
        found = true
    }
    if (!found) {
      productCards += new ProductCard(product, "1")
    }
  }

  def getContent = {
    val infoCaution = new Label {
      text = "Card Information"
      wrapText = true
    }

    val img = new ImageView {
      val filePath = "/scalafx/ecstock/images/boat.jpg"
      val inputStream = this.getClass.getResourceAsStream(filePath)
      if (inputStream == null) {
        throw new IOException("Unable to locate resource: " + filePath)
      }
      image = new Image(inputStream)
      fitWidth = 100
      fitHeight = 100
    }
    val img1 = new ImageView {
      val filePath = "/scalafx/ecstock/images/ScalaFX-icon-64x64.png"
      val inputStream = this.getClass.getResourceAsStream(filePath)
      if (inputStream == null) {
        throw new IOException("Unable to locate resource: " + filePath)
      }
      image = new Image(inputStream)
    }
    val img2 = new ImageView {
      val filePath = "/scalafx/ecstock/images/scala-logo.png"
      val inputStream = this.getClass.getResourceAsStream(filePath)
      if (inputStream == null) {
        throw new IOException("Unable to locate resource: " + filePath)
      }
      image = new Image(inputStream)
    }
    val img3 = new ImageView {
      val filePath = "/scalafx/ecstock/images/ScalaFX-icon-64x64.png"
      val inputStream = this.getClass.getResourceAsStream(filePath)
      if (inputStream == null) {
        throw new IOException("Unable to locate resource: " + filePath)
      }
      image = new Image(inputStream)
    }

    val imacImg = new ImageView {
      val filePath = "/scalafx/ecstock/products/imac.png"
      val inputStream = this.getClass.getResourceAsStream(filePath)
      if (inputStream == null) {
        throw new IOException("Unable to locate resource: " + filePath)
      }
      image = new Image(inputStream)
      fitWidth = 100
      fitHeight = 100
    }

    val ipadImg = new ImageView {
      val filePath = "/scalafx/ecstock/products/ipad.jpg"
      val inputStream = this.getClass.getResourceAsStream(filePath)
      if (inputStream == null) {
        throw new IOException("Unable to locate resource: " + filePath)
      }
      image = new Image(inputStream)
      fitWidth = 100
      fitHeight = 100
    }

    val tvImg = new ImageView {
      val filePath = "/scalafx/ecstock/products/tv.jpg"
      val inputStream = this.getClass.getResourceAsStream(filePath)
      if (inputStream == null) {
        throw new IOException("Unable to locate resource: " + filePath)
      }
      image = new Image(inputStream)
      fitWidth = 100
      fitHeight = 100
    }

    val chocolateImg = new ImageView {
      val filePath = "/scalafx/ecstock/products/chocolate.jpg"
      val inputStream = this.getClass.getResourceAsStream(filePath)
      if (inputStream == null) {
        throw new IOException("Unable to locate resource: " + filePath)
      }
      image = new Image(inputStream)
      fitWidth = 100
      fitHeight = 100
    }

    val candiesImg = new ImageView {
      val filePath = "/scalafx/ecstock/products/candies.jpg"
      val inputStream = this.getClass.getResourceAsStream(filePath)
      if (inputStream == null) {
        throw new IOException("Unable to locate resource: " + filePath)
      }
      image = new Image(inputStream)
      fitWidth = 100
      fitHeight = 50
    }

    val gadgetsImg = new ImageView {
      val filePath = "/scalafx/ecstock/products/gadgets.png"
      val inputStream = this.getClass.getResourceAsStream(filePath)
      if (inputStream == null) {
        throw new IOException("Unable to locate resource: " + filePath)
      }
      image = new Image(inputStream)
      fitWidth = 100
      fitHeight = 50
    }

    val fruitsImg = new ImageView {
      val filePath = "/scalafx/ecstock/products/fruits.jpg"
      val inputStream = this.getClass.getResourceAsStream(filePath)
      if (inputStream == null) {
        throw new IOException("Unable to locate resource: " + filePath)
      }
      image = new Image(inputStream)
      fitWidth = 100
      fitHeight = 50
    }

    val productCards = ObservableBuffer[ProductCard]()

    val detailTable = new TableView[ProductCard](productCards) {
      columns ++= List(
        new TableColumn[ProductCard, String] {
          text = "Product"
          cellValueFactory = { _.value.product }
          prefWidth = 100
        },
        new TableColumn[ProductCard, String]() {
          text = "Quantity"
          cellValueFactory = { _.value.quantity }
          prefWidth = 100
        }
      )
    }

    GridPane.setConstraints(detailTable, 0, 0, 1, 1)

    val detail = new Button("Detail", img) {
        prefWidth = 110
        prefHeight = 396
        contentDisplay = ContentDisplay.Top
        styleClass.clear()
        styleClass += "sample-tile"
        onAction = (ae: ActionEvent) => {
          println("Was choosen a product")
        }
      }
    GridPane.setConstraints(detail, 0, 0, 1, 1)
    val detailGrid = new GridPane {
      hgap = 1
      vgap = 1
      gridLinesVisible = true
      margin = Insets(18)
      style = "-fx-background-color: red"
      children ++= Seq(detailTable)
    }
    detailGrid.setPrefSize(110, 396)
    GridPane.setConstraints(detailGrid, 0, 0, 2, 1)

    val imacProduct = new Button("IMAC", imacImg) {
        contentDisplay = ContentDisplay.Top
        onAction = (ae: ActionEvent) => {
          addAmount("IMAC", 1, productCards)
        }
      }
    GridPane.setConstraints(imacProduct, 0, 0, 1, 1)

    val ipadProduct = new Button("IPAD", ipadImg) {
        contentDisplay = ContentDisplay.Top
        onAction = (ae: ActionEvent) => {
          addAmount("IPAD", 1, productCards)
        }
      }
    GridPane.setConstraints(ipadProduct, 1, 0, 1, 1)

    val tvProduct = new Button("TV", tvImg) {
        contentDisplay = ContentDisplay.Top
        onAction = (ae: ActionEvent) => {
          addAmount("TV", 1, productCards)
        }
      }
    GridPane.setConstraints(tvProduct, 2, 0, 1, 1)

    val chocolateProduct = new Button("CHOCOLATE", chocolateImg) {
        contentDisplay = ContentDisplay.Top
        onAction = (ae: ActionEvent) => {
          addAmount("CHOCOLATE", 1, productCards)
        }
      }
    GridPane.setConstraints(chocolateProduct, 0, 1, 1, 1)

    val productsGrid = new GridPane {
      hgap = 3
      vgap = 3
      gridLinesVisible = true
      margin = Insets(18)
      style = "-fx-background-color: yellow"
      children ++= Seq(imacProduct, ipadProduct, tvProduct, chocolateProduct)
    }
    productsGrid.setPrefSize(396, 396)
    GridPane.setConstraints(productsGrid, 1, 0, 2, 2)

    val calculator = new Button("Calculator", img2) {
        prefWidth = 110
        prefHeight = 110
        contentDisplay = ContentDisplay.Top
        alignmentInParent = Pos.BaselineLeft
        styleClass.clear()
        styleClass += "sample-tile"
        onAction = (ae: ActionEvent) => {
          println("Was choosen a product")
        }
      }
    GridPane.setConstraints(calculator, 0, 0, 1, 1)
    val calculatorGrid = new GridPane {
      hgap = 1
      vgap = 1
      gridLinesVisible = true
      margin = Insets(18)
      style = "-fx-background-color: green"
      children ++= Seq(calculator)
    }
    calculatorGrid.setPrefSize(198, 110)
    GridPane.setConstraints(calculatorGrid, 0, 2, 1, 1)


    val candiesProduct = new Button("CANDIES", candiesImg) {
        contentDisplay = ContentDisplay.Top
        onAction = (ae: ActionEvent) => {
          println("Was choosen a CANDIES")
        }
      }
    GridPane.setConstraints(candiesProduct, 0, 0, 1, 1)

    val gadgetsProduct = new Button("GADGETS", gadgetsImg) {
        contentDisplay = ContentDisplay.Top
        onAction = (ae: ActionEvent) => {
          println("Was choosen a IPAD")
        }
      }
    GridPane.setConstraints(gadgetsProduct, 1, 0, 1, 1)

    val fruitsProduct = new Button("FRUITS", fruitsImg) {
        contentDisplay = ContentDisplay.Top
        onAction = (ae: ActionEvent) => {
          println("Was choosen a TV")
        }
      }
    GridPane.setConstraints(fruitsProduct, 2, 0, 1, 1)


    val categoriesGrid = new GridPane {
      hgap = 3
      vgap = 3
      margin = Insets(18)
      style = "-fx-background-color: blue"
      children ++= Seq(candiesProduct, gadgetsProduct, fruitsProduct)
    }
    categoriesGrid.setPrefSize(396, 110)
    GridPane.setConstraints(categoriesGrid, 1, 2, 1, 2)

    val infoGrid = new GridPane {
      hgap = 3
      vgap = 3
      gridLinesVisible = true
      margin = Insets(18)
      style = "-fx-background-color: #336699"
      children ++= Seq(detailGrid, productsGrid, calculatorGrid, categoriesGrid)
    }

    val saveBtn = new Button("SAVE")
    GridPane.setConstraints(saveBtn, 0, 0)
    GridPane.setMargin(saveBtn, Insets(10, 10, 10, 10))
    GridPane.setHalignment(saveBtn, HPos.Center)

    val cancelBtn = new Button("CANCEL")
    GridPane.setConstraints(cancelBtn, 1, 0)
    GridPane.setMargin(cancelBtn, Insets(10, 10, 10, 10))
    GridPane.setHalignment(cancelBtn, HPos.Center)

    val actionCaution = new Label {
      text = "Save Data."
      wrapText = true
    }

    val actionGrid = new GridPane {
      hgap = 4
      vgap = 6
      margin = Insets(18)
      children ++= Seq(saveBtn, cancelBtn)
    }

    new VBox {
      vgrow = Priority.Always
      hgrow = Priority.Always
      spacing = 10
      padding = Insets(20)
      children = List(
        infoGrid
      )
    }
  }
}