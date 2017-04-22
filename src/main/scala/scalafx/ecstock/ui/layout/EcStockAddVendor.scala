package scalafx.ecstock.ui.layout

import scalafx.Includes._
import scalafx.ecstock.commons.EcStockExample
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.{Button, Label, Separator, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, RowConstraints, VBox}
import scalafx.ecstock.models.Vendor
import scalafx.ecstock.models.DBManager
import scalafx.event.ActionEvent
import scalafx.ecstock.i18n.Messages
import scalafx.ecstock.commons.PageDisplayer
import scalafx.ecstock.EcStock

/**
 *
 */
class EcStockAddVendor extends EcStockExample {

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

    val nameTxt = new TextField {text = "Name"}

    GridPane.setConstraints(nameTxt, 1, 0, 2, 1)

    val addressLbl = new Label(Messages.data("Address:")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(addressLbl, 0, 1, 1, 1)

    val addressTxt = new TextField() {
      text = "Address"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(addressTxt, 1, 1, 5, 1)

    val contactLbl = new Label(Messages.data("Contact:")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(contactLbl, 0, 2, 1, 1)

    val contactTxt = new TextField() {
      text = "Contact"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(contactTxt, 1, 2, 5, 1)

    val contact2Lbl = new Label(Messages.data("Contact2:")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(contact2Lbl, 0, 3, 1, 1)

    val contact2Txt = new TextField() {
      text = "Contact2"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(contact2Txt, 1, 3 , 5, 1)

    val infoGrid = new GridPane {
      hgap = 4
      vgap = 6
      margin = Insets(18)
      children ++= Seq(nameLbl, nameTxt, addressLbl, addressTxt, contactLbl, contactTxt, contact2Lbl, contact2Txt)
    }

    val saveBtn = new Button(Messages.data("save")) {
      onAction = (ae: ActionEvent) => {
          DBManager.session.beginTransaction();
          val vendor = new Vendor(0, nameTxt.getText(), addressTxt.getText(), contactTxt.getText(), contact2Txt.getText())
          DBManager.session.save(vendor);
          DBManager.session.getTransaction().commit();
          EcStock.splitPane.items.remove(1)
            EcStock.splitPane.items.add(1,
              PageDisplayer.choosePage("layout > " + EcStockListVendor.objectName))
      }
    }

    GridPane.setConstraints(saveBtn, 0, 0)
    GridPane.setMargin(saveBtn, Insets(10, 10, 10, 10))
    GridPane.setHalignment(saveBtn, HPos.Center)

    val cancelBtn = new Button(Messages.data("cancel")) {
      onAction = (ae: ActionEvent) => {
        EcStock.splitPane.items.remove(1)
            EcStock.splitPane.items.add(1,
              PageDisplayer.choosePage("layout > " + EcStockListVendor.objectName))
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