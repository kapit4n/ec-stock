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

  var productCardItems: ObservableBuffer[ProductCardItem] = ObservableBuffer[ProductCardItem]()
  var products: ObservableBuffer[Product] = ObservableBuffer[Product]()
  var categories: ObservableBuffer[Category] = ObservableBuffer[Category]()
  var customers: ObservableBuffer[Customer] = ObservableBuffer[Customer]()

  val productImgWidth = 45
  val productImgHeight = 45
  val categoryImgWidth = 45
  val categoryImgHeight = 45

  var totalLbl:Label = _
  var totalPriceTxt:TextField = _

  val productsGrid = new GridPane {
    hgap = 10
    vgap = 10
    gridLinesVisible = true
    margin = Insets(5)
    //style = "-fx-background-color: yellow"
  }

  def recalculateCardTotalPrice() = {
    var totalPrice = 0.0
    for (card <- productCardItems) {
      totalPrice = totalPrice + card.totalPrice
    }
    totalPriceTxt.setText(totalPrice.toString)
  }

  /*
   * Updates the amount of the product on the card and add if it doensn't exist
   */
  def addProduct(productId: Int, quantity: Int) = {
    var found = false
    for (card <- productCardItems.filter(_.product == productId)) {
      for (product <- products.filter(_.id == productId)) {
        if (product.total >= (card.quantity + quantity)) {
          card.quantityProperty.value = (card.quantityProperty.value.toInt + quantity).toString
          card.quantity = card.quantity + quantity
          card.totalPrice = card.price * card.quantity
          card.totalCost = card.unitCost * card.quantity
          card.totalPriceProperty.value = card.totalPrice.toString
          card.totalCostProperty.value = card.totalCost.toString
        }
        found = true
      }
    }
    if (!found) {
      val items = products.filter(_.id == productId)
      if (items(0).total >= quantity) {
        productCardItems += new ProductCardItem(0, 0, items(0).id, 1, items(0).retailPrice,
          items(0).retailPrice, items(0).unitCost, items(0).unitCost, items(0).name, items(0).boxSize,
          items(0).boxPrice, items(0).boxCost)
      }
    }
    recalculateCardTotalPrice()
  }

  /*
   * Updates the amount of the product on the card and add if it doensn't exist
   */
  def updateTotalByQuantity(productId: Int, quantity: Int) = {
    for (card <- productCardItems.filter(_.product == productId)) {
      for (product <- products.filter(_.id == productId)) {
        if (product.total >= quantity) {
          card.quantity = quantity
        }
        card.totalPrice = card.price * card.quantity
        card.totalCost = card.unitCost * card.quantity
        card.quantityProperty.value = card.quantity.toString
        card.totalPriceProperty.value = card.totalPrice.toString
        card.totalCostProperty.value = card.totalCost.toString
      }
    }
    recalculateCardTotalPrice()
  }

  /**
   * Method to generate product list by category
   */
  def generateCategory(containerGrid: GridPane, categoryId: Int) = {
    var found = false
    val maxC = 10
    val maxR = 3
    var column = 0
    var row = 0
    containerGrid.children.clear()
    for (product <- products.filter(_.category == categoryId)) {
        val auxImage = new ImageView {
          val filePath =  product.imgSrc
          val inputStream = this.getClass.getResourceAsStream(filePath)
          if (inputStream == null) {
            throw new IOException("Unable to locate resource: " + filePath)
          }
          image = new Image(inputStream)
          fitWidth = productImgWidth
          fitHeight = productImgHeight
        }
        var pName = product.name
        if (product.name.length > 7 && product.total < 10) pName = product.name.substring(0, 7)
        else if (product.name.length > 6 && product.total > 9 && product.total < 100) pName = product.name.substring(0, 6)
        else if (product.name.length > 5 && product.total > 99 && product.total < 1000) pName = product.name.substring(0, 5)
        val auxButton = new Button(pName + "(" + product.total + ")", auxImage) {
            contentDisplay = ContentDisplay.Top
            onAction = (ae: ActionEvent) => {
              addProduct(product.id, 1)
            }
          }
        auxButton.setStyle("-fx-font: 9 arial; -fx-base: #b6e7c9;");

        GridPane.setConstraints(auxButton, column, row, 1, 1)
        containerGrid.children += auxButton

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
    val maxC = 8
    val maxR = 3
    var column = 0
    var row = 0
    containerGrid.children.clear()
    for (category <- categories) {
        val auxImage = new ImageView {
          val filePath =  category.imgSrc
          val inputStream = this.getClass.getResourceAsStream(filePath)
          if (inputStream == null) {
            throw new IOException("Unable to locate resource: " + filePath)
          }
          image = new Image(inputStream)
          fitWidth = categoryImgWidth
          fitHeight = categoryImgHeight
        }

        val auxButton = new Button(category.name, auxImage) {
            contentDisplay = ContentDisplay.Top
            onAction = (ae: ActionEvent) => {
              generateCategory(productsGrid, category.id)
            }
          }

        GridPane.setConstraints(auxButton, column, row, 1, 1)
        containerGrid.children += auxButton

        column = column + 1
        column = column % maxC
        if (column == 0) {
          row = row + 1
        }
    }
  }

  def getContent = {
    products = ObservableBuffer[Product](DBManager.getProducts(DBManager.ALL))
    products = products.filter(p => p.total > 0)
    categories = ObservableBuffer[Category](DBManager.getCategories())
    customers = ObservableBuffer[Customer](DBManager.getCustomers())

    val infoCaution = new Label {
      text = Messages.data("Information")
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
          text = Messages.data("Product")
          cellValueFactory = { _.value.productNameProperty }
          prefWidth = 150
        },
        new TableColumn[ProductCardItem, String]() {
          text = Messages.data("Quantity")
          cellValueFactory = { _.value.quantityProperty}
          cellFactory = column => new TextFieldTableCell[ProductCardItem, String] (new DefaultStringConverter())
          prefWidth = 66
          onEditCommit = (evt: CellEditEvent[ProductCardItem, String]) => {
            val rowOldValue = evt.rowValue
            val rowNewValue = evt.newValue
            updateTotalByQuantity(rowOldValue.product, rowNewValue.toInt)
          }
          editable = true
        },
        new TableColumn[ProductCardItem, String]() {
          text = Messages.data("Price")
          cellValueFactory = { _.value.priceProperty}
          cellFactory = column => new TextFieldTableCell[ProductCardItem, String] (new DefaultStringConverter())
          prefWidth = 66
        },
        new TableColumn[ProductCardItem, String]() {
          text = Messages.data("Total Price")
          cellValueFactory = { _.value.totalPriceProperty}
          cellFactory = column => new TextFieldTableCell[ProductCardItem, String] (new DefaultStringConverter())
          prefWidth = 66
        }
      )
      editable = true
    }
    GridPane.setConstraints(detailTable, 0, 0, 1, 1)

    val deleteCardItemBtn: Button = new Button(Messages.data("delete")) {
      onAction = (ae: ActionEvent) => {
        val selectedItem = detailTable.getSelectionModel().getSelectedItem()
        detailTable.getItems().remove(selectedItem)
        recalculateCardTotalPrice()
      }
    }
    GridPane.setConstraints(deleteCardItemBtn, 0, 1)

    val detailGrid = new GridPane {
      hgap = 2
      vgap = 2
      gridLinesVisible = true
      margin = Insets(18)
      //style = "-fx-background-color: red"
      children ++= Seq(detailTable, deleteCardItemBtn)
    }

    detailGrid.setPrefSize(350, 450)
    GridPane.setConstraints(detailGrid, 0, 0, 2, 1)

    productsGrid.setPrefSize(750, 450)
    GridPane.setConstraints(productsGrid, 1, 0, 2, 2)

    val calculator = new Button("Calculator", calculatorImg) {
        contentDisplay = ContentDisplay.Top
        alignmentInParent = Pos.BaselineLeft
        styleClass.clear()
        styleClass += "sample-tile"
        onAction = (ae: ActionEvent) => {
          println("Was choosen a product")
        }
      }
    GridPane.setConstraints(calculator, 0, 0, 1, 1)

    totalLbl = new Label(Messages.data("Total:")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(totalLbl, 0, 0, 1, 1)

    totalPriceTxt = new TextField {
      text = "0"
      editable = false
    }

    GridPane.setConstraints(totalPriceTxt, 1, 0, 2, 1)

    val calculatorGrid = new GridPane {
      hgap = 2
      vgap = 2
      gridLinesVisible = true
      margin = Insets(18)
      //style = "-fx-background-color: green"
      children ++= Seq(totalLbl, totalPriceTxt)
    }
    calculatorGrid.setPrefSize(350, 110)
    GridPane.setConstraints(calculatorGrid, 0, 2, 1, 1)
    generateCategory(productsGrid, categories(0).id)

    val categoriesGrid = new GridPane {
      hgap = 3
      vgap = 3
      margin = Insets(18)
      //style = "-fx-background-color: blue"
    }
    categoriesGrid.setPrefSize(750, 110)
    GridPane.setConstraints(categoriesGrid, 1, 2, 1, 2)

    generateCategories(categoriesGrid)

    val infoGrid = new GridPane {
      hgap = 3
      vgap = 3
      gridLinesVisible = true
      margin = Insets(18)
      //style = "-fx-background-color: #336699"
      children ++= Seq(detailGrid, productsGrid, calculatorGrid, categoriesGrid)
    }

    infoGrid.setPrefSize(1000, 850)

    val customerLbl = new Label(Messages.data("Customer:")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }

    GridPane.setConstraints(customerLbl, 0, 1)

    val customerCb = new ComboBox[Customer] {
          maxWidth = 200
          promptText = Messages.data("Make a choice...")
          items = customers
        };

    GridPane.setConstraints(customerCb, 1, 1)

    val saveBtn = new Button(Messages.data("save")) {
      onAction = (ae: ActionEvent) => {

          var totalPrice: Double = 0
          var totalCost: Double = 0
          for (cardItem <- productCardItems) {
            totalPrice = totalPrice + cardItem.totalPrice
            totalCost = totalCost + cardItem.totalCost
          }
          val card = new ProductCard(0, customerCb.getValue().id, totalPrice, totalCost, "Observations", "")
          DBManager.session.beginTransaction()
          DBManager.session.save(card)
          DBManager.session.getTransaction().commit()
          DBManager.session.beginTransaction()
          for (cardItem <- productCardItems) {
            cardItem.card = card.id
            DBManager.session.save(cardItem)
          }
          DBManager.session.getTransaction().commit()

          for (cardItem <- productCardItems) {
            DBManager.updateProductTotal(cardItem.quantity * -1, cardItem.product)
          }
          DBManager.session.clear()
          EcStock.splitPane.items.remove(0)
            EcStock.splitPane.items.add(0,
              PageDisplayer.choosePage("layout > " + EcStockListCard.objectName))

      }
    }
    GridPane.setConstraints(saveBtn, 0, 0)
    GridPane.setMargin(saveBtn, Insets(10, 10, 10, 10))
    GridPane.setHalignment(saveBtn, HPos.Center)

    val cancelBtn = new Button(Messages.data("cancel")) {
      onAction = (ae: ActionEvent) => {
        EcStock.splitPane.items.remove(0)
            EcStock.splitPane.items.add(0,
              PageDisplayer.choosePage("layout > " + EcStockListCard.objectName))
      }
    }
    GridPane.setConstraints(cancelBtn, 1, 0)
    GridPane.setMargin(cancelBtn, Insets(10, 10, 10, 10))
    GridPane.setHalignment(cancelBtn, HPos.Center)



    val actionCaution = new Label {
      text = Messages.data("Save Data")
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