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
import org.hibernate.criterion._
import org.hibernate.{ Session, Criteria }
import scalafx.ecstock.models.DBManager
import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import scalafx.ecstock.i18n.Messages
import scalafx.util.converter.DefaultStringConverter
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.event.ActionEvent

/**
 */
class EcStockListCategory extends EcStockExample {

  def updateCategory(data: Category) = {
    DBManager.session.beginTransaction();
    DBManager.session.update(data);
    DBManager.session.getTransaction().commit();
  }

  def getContent = {
    val infoCaution = new Label {
      text = Messages.data("List")
      wrapText = true
    }

    var categories = ObservableBuffer[Category](DBManager.getCategories())
    val categoryTable = new TableView[Category](categories) {
      columns ++= List(
        new TableColumn[Category, String] {
          text = Messages.data("Category")
          cellValueFactory = { _.value.nameProperty }
          prefWidth = 350
          editable = true
          cellFactory = column => new TextFieldTableCell[Category, String] (new DefaultStringConverter())
          onEditCommit = (evt: CellEditEvent[Category, String]) => {
            evt.rowValue.name = evt.newValue
            evt.rowValue.nameProperty.value = evt.newValue
            updateCategory(evt.rowValue)
          }
        },
        new TableColumn[Category, String]() {
          text = Messages.data("Description")
          cellValueFactory = { _.value.descriptionProperty }
          prefWidth = 450
          editable = true
          cellFactory = column => new TextFieldTableCell[Category, String] (new DefaultStringConverter())
          onEditCommit = (evt: CellEditEvent[Category, String]) => {
            evt.rowValue.description = evt.newValue
            evt.rowValue.descriptionProperty.value = evt.newValue
            updateCategory(evt.rowValue)
          }
        }
      )
      editable = true
      prefWidth = 800
    }
    
    GridPane.setConstraints(categoryTable, 0, 0)

    val deleteBtn: Button = new Button(Messages.data("delete")) {
      onAction = (ae: ActionEvent) => {
        val selectedItem = categoryTable.getSelectionModel().getSelectedItem()
        categoryTable.getItems().remove(selectedItem)
        categories.remove(selectedItem)
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
      children ++= Seq(categoryTable, deleteBtn)
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

object EcStockListCategory {
  val objectName = "List Category"
}
