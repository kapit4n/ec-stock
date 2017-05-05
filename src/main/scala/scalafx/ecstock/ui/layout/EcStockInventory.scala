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
import scalafx.event.ActionEvent
import scalafx.scene.control.DatePicker
import java.util.Locale
import java.time.LocalDate

/**
 *
 */
class EcStockInventory extends EcStockExample {
  var products:ObservableBuffer[Product] = ObservableBuffer[Product]()
  var containTable:TableView[Product] = new TableView[Product]()
  def getContent = {
    val infoCaution = new Label {
      text = Messages.data("List")
      wrapText = true
    }

    products = ObservableBuffer[Product](DBManager.getProducts(DBManager.ALL))

    val searchText = new TextField() {
      
    }
    GridPane.setConstraints(searchText, 0, 0)
    
    val searchBtn = new Button(Messages.data("search")) {
      onAction = (ae: ActionEvent) => {
        DBManager.likeStr = searchText.getText()
        containTable.items = ObservableBuffer[Product](DBManager.getProducts(DBManager.LIKE))
      }
    }
    GridPane.setConstraints(searchBtn, 1, 0)

    val outOfStock = new Button(Messages.data("outOfStock")) {
      onAction = (ae: ActionEvent) => {
        containTable.items = ObservableBuffer[Product](DBManager.getProducts(DBManager.OUT_OF_STOCK))
      }
    }
    GridPane.setConstraints(outOfStock, 2, 0)

    val filtersGrid = new GridPane {
      hgap = 6
      vgap = 1
      margin = Insets(18)
      children ++= Seq(searchText, searchBtn, outOfStock)
    }

    containTable = new TableView[Product](products) {
      columns ++= List(
        new TableColumn[Product, String] {
          text = Messages.data("Name")
          cellValueFactory = { _.value.nameProperty }
          prefWidth = 200
        },
        new TableColumn[Product, String]() {
          text = Messages.data("Retail Price")
          cellValueFactory = { _.value.retailPriceProperty }
          prefWidth = 100
        },
        new TableColumn[Product, String]() {
          text = Messages.data("Vendor")
          cellValueFactory = { _.value.vendorProperty }
          prefWidth = 200
        },
        new TableColumn[Product, String]() {
          text = Messages.data("Total")
          cellValueFactory = { _.value.totalProperty }
          prefWidth = 100
        },
        new TableColumn[Product, String]() {
          text = Messages.data("Limit")
          cellValueFactory = { _.value.limitProperty }
          prefWidth = 100
        }
      )
      prefWidth = 800
    }

    GridPane.setConstraints(containTable, 0, 0)

    val infoGrid = new GridPane {
      hgap = 1
      vgap = 1
      margin = Insets(18)
      children ++= Seq(containTable)
    }

    new VBox {
      vgrow = Priority.Always
      hgrow = Priority.Always
      spacing = 10
      padding = Insets(20)
      children = List(
        new VBox {children = List(infoCaution, filtersGrid, infoGrid)},
        new Separator()
      )
    }
  }
}