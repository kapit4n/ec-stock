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
import scalafx.ecstock.models._
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableCell, TableColumn, TableView}
import scalafx.util.converter.DefaultStringConverter
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.ecstock.models.DBManager
import scalafx.ecstock.i18n.Messages
import scalafx.ecstock.commons.PageDisplayer
import scalafx.ecstock.EcStock

/**
 *
 */
class EcStockAddCard extends EcStockExample {

  val productCardItems: ObservableBuffer[ProductCardItem] = ObservableBuffer[ProductCardItem]()
  val products: ObservableBuffer[Product] = ObservableBuffer[Product](DBManager.getProducts())
  val categories: ObservableBuffer[Category] = ObservableBuffer[Category](DBManager.getCategories())
  val customers = ObservableBuffer(DBManager.getCustomers())

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
  def addAmount(product: Int, quantity: Int) = {
    var found = false
    for (card <- productCardItems.filter(_.product == product)) {
      card.quantityProperty.value = (card.quantityProperty.value.toInt + quantity).toString
      card.quantity = card.quantity + quantity
      card.totalPrice = card.price * card.quantity
      found = true
    }
    if (!found) {
      val items = products.filter(_.id == product)
      productCardItems += new ProductCardItem(0, 0, items(0).id, 1, items(0).retailPrice, items(0).retailPrice, items(0).name)
    }
  }

  /**
   * Method to generate product list by category
   */
  def generateCategory(containerGrid: GridPane, categoryId: Int) = {
    var found = false
    val maxC = 3
    val maxR = 3
    var column = 0
    var row = 0
    containerGrid.children.clear()
    for (product <- products.filter(_.category == categoryId)) {
        val imgAux = new ImageView {
          val filePath =  product.imgSrc
          val inputStream = this.getClass.getResourceAsStream(filePath)
          if (inputStream == null) {
            throw new IOException("Unable to locate resource: " + filePath)
          }
          image = new Image(inputStream)
          fitWidth = productImgWidth
          fitHeight = productImgHeight
        }

        val auxData = new Button(product.name, imgAux) {
            contentDisplay = ContentDisplay.Top
            onAction = (ae: ActionEvent) => {
              addAmount(product.id, 1)
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
          val filePath =  category.imgSrc
          val inputStream = this.getClass.getResourceAsStream(filePath)
          if (inputStream == null) {
            throw new IOException("Unable to locate resource: " + filePath)
          }
          image = new Image(inputStream)
          fitWidth = categoryImgWidth
          fitHeight = categoryImgHeight
        }

        val auxData = new Button(category.name, imgAux) {
            contentDisplay = ContentDisplay.Top
            onAction = (ae: ActionEvent) => {
              generateCategory(productsGrid, category.id)
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

    //products += new Product("IPAD", "10", "Vendor 1", "Brand 1", "GADGET", "Description 1", "/scalafx/ecstock/products/ipad.jpg")
    //products += new Product("IMAC", "10", "Vendor 1", "Brand 1", "GADGET", "Description 1", "/scalafx/ecstock/products/imac.png")
    //products += new Product("TV", "10", "Vendor 1", "Brand 1", "GADGET", "Description 1", "/scalafx/ecstock/products/tv.jpg")
    //products += new Product("APPLE", "10", "Vendor 1", "Brand 1", "FRUIT", "Description 1", "/scalafx/ecstock/products/apple.jpg")
    //products += new Product("ORANGE", "10", "Vendor 1", "Brand 1", "FRUIT", "Description 1", "/scalafx/ecstock/products/orange.jpg")
    //products += new Product("WATERMELON", "10", "Vendor 1", "Brand 1", "FRUIT", "Description 1", "/scalafx/ecstock/products/watermelon.jpg")
    //products += new Product("CHOCOLATE", "10", "Vendor 1", "Brand 1", "CANDY", "Description 1", "/scalafx/ecstock/products/chocolate.jpg")
    //products += new Product("LOLLIPOPS", "10", "Vendor 1", "Brand 1", "CANDY", "Description 1", "/scalafx/ecstock/products/lollipops.jpg")


    //categories += new Category("FRUIT", "Description 1", "/scalafx/ecstock/products/fruits.jpg")
    //categories += new Category("CANDY", "Description 2", "/scalafx/ecstock/products/candies.jpg")
    //categories += new Category("GADGET", "Description 2", "/scalafx/ecstock/products/gadgets.png")


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

    val detailTable = new TableView[ProductCardItem](productCardItems) {
      columns ++= List(
        new TableColumn[ProductCardItem, String] {
          text = "Product"
          cellValueFactory = { _.value.productNameProperty }
          prefWidth = 180
        },
        new TableColumn[ProductCardItem, String]() {
          text = "Quantity"
          cellValueFactory = { _.value.quantityProperty}
          cellFactory = column => new TextFieldTableCell[ProductCardItem, String] (new DefaultStringConverter())
          prefWidth = 120
        }
      )
      prefWidth = 300
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
    generateCategory(productsGrid, categories(0).id)

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
      prefWidth = 800
    }

    val customerLbl = new Label("Customer:") {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }

    GridPane.setConstraints(customerLbl, 0, 1)

    val customerCb = new ComboBox[Customer] {
          maxWidth = 200
          promptText = "Make a choice..."
          items = customers
        };

    GridPane.setConstraints(customerCb, 1, 1)

    val saveBtn = new Button(Messages.data("save")) {
      onAction = (ae: ActionEvent) => {

          var totalPrice: Double = 0
          for (cardItem <- productCardItems) {
            totalPrice = totalPrice + cardItem.totalPrice
          }
          val card = new ProductCard(0, customerCb.getValue().id, totalPrice, "Observations", "")
          DBManager.session.beginTransaction()
          DBManager.session.save(card)
          DBManager.session.getTransaction().commit()
          println(card.id)
          DBManager.session.beginTransaction()
          for (cardItem <- productCardItems) {
            cardItem.card = card.id
            DBManager.session.save(cardItem)
          }
          DBManager.session.getTransaction().commit()

          for (cardItem <- productCardItems) {
            DBManager.updateProductTotal(cardItem.quantity * -1, cardItem.product)
          }
      }
    }
    GridPane.setConstraints(saveBtn, 0, 0)
    GridPane.setMargin(saveBtn, Insets(10, 10, 10, 10))
    GridPane.setHalignment(saveBtn, HPos.Center)

    val cancelBtn = new Button(Messages.data("cancel")) {
      onAction = (ae: ActionEvent) => {
        EcStock.splitPane.items.remove(1)
            EcStock.splitPane.items.add(1,
              PageDisplayer.choosePage("layout > " + EcStockListCard.objectName))
      }
    }
    GridPane.setConstraints(cancelBtn, 1, 0)
    GridPane.setMargin(cancelBtn, Insets(10, 10, 10, 10))
    GridPane.setHalignment(cancelBtn, HPos.Center)



    val actionCaution = new Label {
      text = "Save Data"
      wrapText = true
    }

    val actionGrid = new GridPane {
      hgap = 4
      vgap = 6
      margin = Insets(18)
      children ++= Seq(saveBtn, cancelBtn, customerLbl, customerCb)
    }

    new VBox {
      vgrow = Priority.Always
      hgrow = Priority.Always
      spacing = 10
      padding = Insets(20)
      children = List(
        infoGrid,
        new Separator(),
        new VBox {children = List(actionCaution, actionGrid)}
      )
    }
  }
}