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
import scalafx.ecstock.models.DBManager
import scalafx.ecstock.i18n.Messages
import scalafx.util.converter.DefaultStringConverter
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.event.ActionEvent

/**
 *
 */
class EcStockListProduct extends EcStockExample {
  
  def updateProduct(data: Product) = {
    DBManager.session.beginTransaction();
    DBManager.session.update(data);
    DBManager.session.getTransaction().commit();
  }
  
  def getContent = {
    val infoCaution = new Label {
      text = Messages.data("List")
      wrapText = true
    }

    val products = ObservableBuffer[Product](DBManager.getProducts(""))

    val productTable = new TableView[Product](products) {
      columns ++= List(
        new TableColumn[Product, String] {
          text = Messages.data("Name")
          cellValueFactory = { _.value.nameProperty }
          prefWidth = 150
          cellFactory = column => new TextFieldTableCell[Product, String] (new DefaultStringConverter())
          onEditCommit = (evt: CellEditEvent[Product, String]) => {
            evt.rowValue.name = evt.newValue
            evt.rowValue.nameProperty.value = evt.newValue
            updateProduct(evt.rowValue)
          }
          editable = true
        },
        new TableColumn[Product, String]() {
          text = Messages.data("Vendor")
          cellValueFactory = { _.value.vendorProperty }
          prefWidth = 100
        },
        new TableColumn[Product, String]() {
          text = Messages.data("Brand")
          cellValueFactory = { _.value.brandProperty }
          prefWidth = 100
        },
        new TableColumn[Product, String]() {
          text = Messages.data("Category")
          cellValueFactory = { _.value.categoryProperty }
          prefWidth = 100
        },
        new TableColumn[Product, String]() {
          text = Messages.data("Retail Price")
          cellValueFactory = { _.value.retailPriceProperty }
          prefWidth = 100
          cellFactory = column => new TextFieldTableCell[Product, String] (new DefaultStringConverter())
          onEditCommit = (evt: CellEditEvent[Product, String]) => {
            evt.rowValue.retailPrice = evt.newValue.toDouble
            evt.rowValue.retailPriceProperty.value = evt.newValue
            updateProduct(evt.rowValue)
          }
          editable = true
        },
        new TableColumn[Product, String]() {
          text = "Unit Cost"
          cellValueFactory = { _.value.unitCostProperty }
          prefWidth = 80
          cellFactory = column => new TextFieldTableCell[Product, String] (new DefaultStringConverter())
          onEditCommit = (evt: CellEditEvent[Product, String]) => {
            evt.rowValue.unitCost = evt.newValue.toDouble
            evt.rowValue.unitCostProperty.value = evt.newValue
            updateProduct(evt.rowValue)
          }
          editable = true
        },
        new TableColumn[Product, String]() {
          text = "Box Cost"
          cellValueFactory = { _.value.boxCostProperty }
          prefWidth = 80
          cellFactory = column => new TextFieldTableCell[Product, String] (new DefaultStringConverter())
          onEditCommit = (evt: CellEditEvent[Product, String]) => {
            evt.rowValue.boxCost = evt.newValue.toDouble
            evt.rowValue.boxCostProperty.value = evt.newValue
            updateProduct(evt.rowValue)
          }
          editable = true
        },
        new TableColumn[Product, String]() {
          text = "Box Size"
          cellValueFactory = { _.value.boxSizeProperty }
          prefWidth = 80
          cellFactory = column => new TextFieldTableCell[Product, String] (new DefaultStringConverter())
          onEditCommit = (evt: CellEditEvent[Product, String]) => {
            evt.rowValue.boxSize = evt.newValue.toLong
            evt.rowValue.boxSizeProperty.value = evt.newValue
            updateProduct(evt.rowValue)
          }
          editable = true
        }
      )
      editable = true
      prefWidth = 800
    }

    GridPane.setConstraints(productTable, 0, 0)

    val deleteBtn: Button = new Button(Messages.data("delete")) {
      onAction = (ae: ActionEvent) => {
        val selectedItem = productTable.getSelectionModel().getSelectedItem()
        productTable.getItems().remove(selectedItem)
        products.remove(selectedItem)
        DBManager.session.beginTransaction()
        DBManager.session.delete(selectedItem)
        DBManager.session.getTransaction().commit()
      }
    }
    GridPane.setConstraints(deleteBtn, 0, 1)

    val infoGrid = new GridPane {
      hgap = 1
      vgap = 1
      margin = Insets(18)
      children ++= Seq(productTable, deleteBtn)
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

object EcStockListProduct {
  val objectName = "List Product"
}
