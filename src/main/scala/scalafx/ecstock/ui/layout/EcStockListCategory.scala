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


/**
 */
class EcStockListCategory extends EcStockExample {

  def getContent = {
    val infoCaution = new Label {
      text = "Category List"
      wrapText = true
    }

    /*
    // Source code to get all categories with hibernate but it doen't fill the data
    var categoryData = new ListBuffer[Category]()
    for (name <- DBManager.session.createCriteria(classOf[Category]).list().asScala) {
      categoryData += name.asInstanceOf[Category]
    }
    */

    var categoryData = DBManager.getCategories()

    val categoryObs = ObservableBuffer[Category](categoryData)
    val table1 = new TableView[Category](categoryObs) {
      columns ++= List(
        new TableColumn[Category, String] {
          text = "Category"
          cellValueFactory = { _.value.nameProperty }
          prefWidth = 100
        },
        new TableColumn[Category, String]() {
          text = "Description"
          cellValueFactory = { _.value.descriptionProperty }
          prefWidth = 100
        }
      )
    }

    GridPane.setConstraints(table1, 0, 1, 1, 1)

    val infoGrid = new GridPane {
      hgap = 4
      vgap = 6
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