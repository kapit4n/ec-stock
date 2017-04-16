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
import scalafx.ecstock.models.Category
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableCell, TableColumn, TableView}
import scalafx.util.converter.DefaultStringConverter
import scalafx.scene.control.cell.TextFieldTableCell

/**
 *
 */
class EcStockAddCard extends EcStockExample {

  val productCards: ObservableBuffer[ProductCard] = ObservableBuffer[ProductCard]()
  val products: ObservableBuffer[Product] = ObservableBuffer[Product]()
  val categories: ObservableBuffer[Category] = ObservableBuffer[Category]()
  val productImgWidth = 100
  val productImgHeight = 100
  val categoryImgWidth = 100
  val categoryImgHeight = 60

  val productsGrid = new GridPane {
    hgap = 3
    vgap = 3
    gridLinesVisible = true
    margin = Insets(18)
    style = "-fx-background-color: yellow"
  }

  /*
   * Updates the amount of the product on the card and add if it doensn't exist
   */
  def addAmount(product: String, quantity: Int) = {
    var found = false
    for (card <- productCards.find(_.product.value == product)) {
        card.quantity.value = (card.quantity.value.toInt + 1).toString
        found = true
    }
    if (!found) {
      productCards += new ProductCard(product, "1")
    }
  }

  /**
   * Method to generate product list by category
   */
  def generateCategory(containerGrid: GridPane, category: String) = {
    var found = false
    val maxC = 3
    val maxR = 3
    var column = 0
    var row = 0
    containerGrid.children.clear()
    for (product <- products.filter(_.category.value == category)) {
        val imgAux = new ImageView {
          val filePath =  product.imgSrc.value
          val inputStream = this.getClass.getResourceAsStream(filePath)
          if (inputStream == null) {
            throw new IOException("Unable to locate resource: " + filePath)
          }
          image = new Image(inputStream)
          fitWidth = productImgWidth
          fitHeight = productImgHeight
        }

        val auxData = new Button(product.name.value, imgAux) {
            contentDisplay = ContentDisplay.Top
            onAction = (ae: ActionEvent) => {
              addAmount(product.name.value, 1)              
            }
          }

        GridPane.setConstraints(auxData, column, row, 1, 1)
        containerGrid.children += auxData

        column = column + 1
        column = column % maxC
        if (column == 0) {
          row = row + 1
        }
    }
  }
  /**
   * Method to generate category list
   */
  def generateCategories(containerGrid: GridPane) = {
    var found = false
    val maxC = 3
    val maxR = 3
    var column = 0
    var row = 0
    containerGrid.children.clear()
    for (category <- categories) {
        val imgAux = new ImageView {
          val filePath =  category.imgSrc.value
          val inputStream = this.getClass.getResourceAsStream(filePath)
          if (inputStream == null) {
            throw new IOException("Unable to locate resource: " + filePath)
          }
          image = new Image(inputStream)
          fitWidth = categoryImgWidth
          fitHeight = categoryImgHeight
        }

        val auxData = new Button(category.name.value, imgAux) {
            contentDisplay = ContentDisplay.Top
            onAction = (ae: ActionEvent) => {
              generateCategory(productsGrid, category.name.value)
            }
          }

        GridPane.setConstraints(auxData, column, row, 1, 1)
        containerGrid.children += auxData

        column = column + 1
        column = column % maxC
        if (column == 0) {
          row = row + 1
        }
    }
  }

  def getContent = {

    products += new Product("IPAD", "10", "Vendor 1", "Brand 1", "GADGET", "Description 1", "/scalafx/ecstock/products/ipad.jpg")
    products += new Product("IMAC", "10", "Vendor 1", "Brand 1", "GADGET", "Description 1", "/scalafx/ecstock/products/imac.png")
    products += new Product("TV", "10", "Vendor 1", "Brand 1", "GADGET", "Description 1", "/scalafx/ecstock/products/tv.jpg")
    products += new Product("APPLE", "10", "Vendor 1", "Brand 1", "FRUIT", "Description 1", "/scalafx/ecstock/products/apple.jpg")
    products += new Product("ORANGE", "10", "Vendor 1", "Brand 1", "FRUIT", "Description 1", "/scalafx/ecstock/products/orange.jpg")
    products += new Product("WATERMELON", "10", "Vendor 1", "Brand 1", "FRUIT", "Description 1", "/scalafx/ecstock/products/watermelon.jpg")
    products += new Product("CHOCOLATE", "10", "Vendor 1", "Brand 1", "CANDY", "Description 1", "/scalafx/ecstock/products/chocolate.jpg")
    products += new Product("LOLLIPOPS", "10", "Vendor 1", "Brand 1", "CANDY", "Description 1", "/scalafx/ecstock/products/lollipops.jpg")


    categories += new Category("FRUIT", "Description 1", "/scalafx/ecstock/products/fruits.jpg")
    categories += new Category("CANDY", "Description 2", "/scalafx/ecstock/products/candies.jpg")
    categories += new Category("GADGET", "Description 2", "/scalafx/ecstock/products/gadgets.png")


    val infoCaution = new Label {
      text = "Card Information"
      wrapText = true
    }

    var calculatorImg = new ImageView {
      val filePath = "/scalafx/ecstock/images/scala-logo.png"
      val inputStream = this.getClass.getResourceAsStream(filePath)
      if (inputStream == null) {
        throw new IOException("Unable to locate resource: " + filePath)
      }
      image = new Image(inputStream)
    }

    val detailTable = new TableView[ProductCard](productCards) {
      columns ++= List(
        new TableColumn[ProductCard, String] {
          text = "Product"
          cellValueFactory = { _.value.product }
          prefWidth = 100
        },
        new TableColumn[ProductCard, String]() {
          text = "Quantity"
          cellValueFactory = { _.value.quantity}
          cellFactory = column => new TextFieldTableCell[ProductCard, String] (new DefaultStringConverter())
          prefWidth = 100
        }
      )
      editable = true
    }

    GridPane.setConstraints(detailTable, 0, 0, 1, 1)


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

    productsGrid.setPrefSize(396, 396)
    GridPane.setConstraints(productsGrid, 1, 0, 2, 2)

    val calculator = new Button("Calculator", calculatorImg) {
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
    generateCategory(productsGrid, "GADGET")

    val categoriesGrid = new GridPane {
      hgap = 3
      vgap = 3
      margin = Insets(18)
      style = "-fx-background-color: blue"
    }
    categoriesGrid.setPrefSize(396, 110)
    GridPane.setConstraints(categoriesGrid, 1, 2, 1, 2)

    generateCategories(categoriesGrid)

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