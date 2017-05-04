package scalafx.ecstock.ui.layout

import scalafx.Includes._
import scalafx.ecstock.commons.EcStockExample
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.{Button, Label, Separator, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, RowConstraints, VBox}
import scalafx.ecstock.models.Customer
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
class EcStockListCustomer extends EcStockExample {
  
  def updateCustomer(data: Customer) = {
    DBManager.session.beginTransaction();
    DBManager.session.update(data);
    DBManager.session.getTransaction().commit();
  }

  def getContent = {
    val infoCaution = new Label {
      text = Messages.data("List")
      wrapText = true
    }

    val customers = ObservableBuffer[Customer](DBManager.getCustomers())
    val customerTable = new TableView[Customer](customers) {
      columns ++= List(
        new TableColumn[Customer, String] {
          text = Messages.data("Name")
          cellValueFactory = { _.value.nameProperty }
          prefWidth = 400
          cellFactory = column => new TextFieldTableCell[Customer, String] (new DefaultStringConverter())
          onEditCommit = (evt: CellEditEvent[Customer, String]) => {
            evt.rowValue.name = evt.newValue
            evt.rowValue.nameProperty.value = evt.newValue
            updateCustomer(evt.rowValue)
          }
          editable = true
        },
        new TableColumn[Customer, String]() {
          text = Messages.data("Address")
          cellValueFactory = { _.value.addressProperty }
          prefWidth = 400
          cellFactory = column => new TextFieldTableCell[Customer, String] (new DefaultStringConverter())
          onEditCommit = (evt: CellEditEvent[Customer, String]) => {
            evt.rowValue.address = evt.newValue
            evt.rowValue.addressProperty.value = evt.newValue
            updateCustomer(evt.rowValue)
          }
          editable = true
        }
      )
      prefWidth = 800
      editable = true
    }

    GridPane.setConstraints(customerTable, 0, 0)

    val deleteBtn: Button = new Button(Messages.data("delete")) {
      onAction = (ae: ActionEvent) => {
        val selectedItem = customerTable.getSelectionModel().getSelectedItem()
        customerTable.getItems().remove(selectedItem)
        customers.remove(selectedItem)
        DBManager.session.beginTransaction()
        DBManager.session.delete(selectedItem)
        DBManager.session.getTransaction().commit()
      }
    }
    GridPane.setConstraints(deleteBtn, 0, 1)

    val infoGrid = new GridPane {
      hgap = 2
      vgap = 1
      margin = Insets(18)
      children ++= Seq(customerTable, deleteBtn)
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

object EcStockListCustomer {
  val objectName = "List Customer"
}
