package scalafx.ecstock.ui.layout

import scalafx.event.ActionEvent
import scalafx.Includes._
import scalafx.ecstock.commons.EcStockExample
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.{Button, Label, Separator, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, RowConstraints, VBox}
import org.hibernate._
import scalafx.ecstock.models.Category
import scalafx.ecstock.models.DBManager
import scalafx.ecstock.i18n.Messages
import scalafx.ecstock.commons.PageDisplayer
import scalafx.ecstock.EcStock

/**
 *
 */
class EcStockAddCategory extends EcStockExample {
  def getContent = {

    // infoGrid places the children by specifying the rows and columns in GridPane.setConstraints()
    val infoCaution = new Label {
      text = Messages.data("Information")
      wrapText = true
    }

    val nameLbl = new Label(Messages.data("Name:")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(nameLbl, 0, 0, 1, 1)

    val nameTxt = new TextField {text = Messages.data("Category")}

    GridPane.setConstraints(nameTxt, 1, 0, 2, 1)

    val descriptionLbl = new Label(Messages.data("Description:")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(descriptionLbl, 0, 1, 1, 1)

    val descriptionTxt = new TextField() {
      text = ""
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(descriptionTxt, 1, 1, 5, 1)

    val infoGrid = new GridPane {
      hgap = 4
      vgap = 6
      margin = Insets(18)
      children ++= Seq(nameLbl, nameTxt, descriptionLbl, descriptionTxt)
    }

    val saveBtn = new Button(Messages.data("save")) {
      onAction = (ae: ActionEvent) => {
          DBManager.session.beginTransaction();
          val category = new Category(0, nameTxt.getText(), descriptionTxt.getText(), "/scalafx/ecstock/products/category.png")
          DBManager.session.save(category);
          DBManager.session.getTransaction().commit();
          DBManager.session.clear()
          EcStock.splitPane.items.remove(0)
            EcStock.splitPane.items.add(0,
              PageDisplayer.choosePage("layout > " + EcStockListCategory.objectName))
      }
    }

    GridPane.setConstraints(saveBtn, 0, 0)
    GridPane.setMargin(saveBtn, Insets(10, 10, 10, 10))
    GridPane.setHalignment(saveBtn, HPos.Center)

    val cancelBtn = new Button(Messages.data("cancel")) {
      onAction = (ae: ActionEvent) => {
        EcStock.splitPane.items.remove(0)
            EcStock.splitPane.items.add(0,
              PageDisplayer.choosePage("layout > " + EcStockListCategory.objectName))
      }
    }
    GridPane.setConstraints(cancelBtn, 1, 0)
    GridPane.setMargin(cancelBtn, Insets(10, 10, 10, 10))
    GridPane.setHalignment(cancelBtn, HPos.Center)

    val actionCaution = new Label {
      text = Messages.data("Save Data.")
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
        new VBox {children = List(infoCaution, infoGrid)},
        new Separator(),
        new VBox {children = List(actionCaution, actionGrid)}
      )
    }
  }
}
