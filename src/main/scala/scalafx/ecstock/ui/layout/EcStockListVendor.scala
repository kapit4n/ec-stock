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
import scalafx.ecstock.models.DBManager
import scalafx.ecstock.i18n.Messages
import scalafx.util.converter.DefaultStringConverter
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.event.ActionEvent

/**
 *
 */
class EcStockListVendor extends EcStockExample {

  def updateVendor(data: Vendor) = {
    DBManager.session.beginTransaction();
    DBManager.session.update(data);
    DBManager.session.getTransaction().commit();
  }

  def getContent = {
    val infoCaution = new Label {
      text = Messages.data("List")
      wrapText = true
    }

    val vendors = ObservableBuffer[Vendor](DBManager.getVendors())

    val vendorTable = new TableView[Vendor](vendors) {
      columns ++= List(
        new TableColumn[Vendor, String] {
          text = Messages.data("Vendor")
          cellValueFactory = { _.value.nameProperty }
          prefWidth = 200
          cellFactory = column => new TextFieldTableCell[Vendor, String] (new DefaultStringConverter())
          onEditCommit = (evt: CellEditEvent[Vendor, String]) => {
            evt.rowValue.name = evt.newValue
            evt.rowValue.nameProperty.value = evt.newValue
            updateVendor(evt.rowValue)
          }
          editable = true
        },
        new TableColumn[Vendor, String]() {
          text = Messages.data("Address")
          cellValueFactory = { _.value.addressProperty }
          prefWidth = 200
          cellFactory = column => new TextFieldTableCell[Vendor, String] (new DefaultStringConverter())
          onEditCommit = (evt: CellEditEvent[Vendor, String]) => {
            evt.rowValue.address = evt.newValue
            evt.rowValue.addressProperty.value = evt.newValue
            updateVendor(evt.rowValue)
          }
          editable = true
        },
        new TableColumn[Vendor, String]() {
          text = Messages.data("Contact")
          cellValueFactory = { _.value.contactProperty }
          prefWidth = 200
          cellFactory = column => new TextFieldTableCell[Vendor, String] (new DefaultStringConverter())
          onEditCommit = (evt: CellEditEvent[Vendor, String]) => {
            evt.rowValue.contact = evt.newValue
            evt.rowValue.contactProperty.value = evt.newValue
            updateVendor(evt.rowValue)
          }
          editable = true
        },
        new TableColumn[Vendor, String]() {
          text = Messages.data("Contact2")
          cellValueFactory = { _.value.contact2Property }
          prefWidth = 200
          cellFactory = column => new TextFieldTableCell[Vendor, String] (new DefaultStringConverter())
          onEditCommit = (evt: CellEditEvent[Vendor, String]) => {
            evt.rowValue.contact2 = evt.newValue
            evt.rowValue.contact2Property.value = evt.newValue
            updateVendor(evt.rowValue)
          }
          editable = true
        }
      )
      prefWidth = 800
      editable = true
    }

    GridPane.setConstraints(vendorTable, 0, 0)

    val deleteBtn: Button = new Button(Messages.data("delete")) {
      onAction = (ae: ActionEvent) => {
        val selectedItem = vendorTable.getSelectionModel().getSelectedItem()
        vendorTable.getItems().remove(selectedItem)
        vendors.remove(selectedItem)
        DBManager.session.beginTransaction()
        DBManager.session.delete(selectedItem)
        DBManager.session.getTransaction().commit()
      }
    }
    GridPane.setConstraints(deleteBtn, 0, 1)

    val infoGrid = new GridPane {
      hgap = 4
      vgap = 6
      margin = Insets(18)
      children ++= Seq(vendorTable, deleteBtn)
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

object EcStockListVendor {
  val objectName = "List Vendor"
}
