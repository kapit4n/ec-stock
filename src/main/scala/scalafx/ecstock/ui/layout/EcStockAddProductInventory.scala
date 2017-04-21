package scalafx.ecstock.ui.layout

import scalafx.Includes._
import scalafx.ecstock.commons.EcStockExample
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.{Button, Label, Separator, TextField, ComboBox}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, RowConstraints, VBox}
import scalafx.collections.ObservableBuffer
import scalafx.ecstock.models.DBManager
import scalafx.ecstock.models._
import scalafx.event.ActionEvent
import scalafx.ecstock.i18n.Messages
import scalafx.ecstock.commons.PageDisplayer
import scalafx.ecstock.EcStock
/**
 *
 */
class EcStockAddProductInventory extends EcStockExample {

  def getContent = {
    // infoGrid places the children by specifying the rows and columns in GridPane.setConstraints()
    val infoCaution = new Label {
      text = "ProductInventory Information"
      wrapText = true
    }

    val products = ObservableBuffer(DBManager.getProducts())

    val vendors = ObservableBuffer(DBManager.getVendors())


    val productLbl = new Label("Product:") {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }

    GridPane.setConstraints(productLbl, 0, 0, 1, 1)

    val productCb = new ComboBox[Product] {
          maxWidth = 200
          promptText = "Make a choice..."
          items = products
        };

    GridPane.setConstraints(productCb, 1, 0, 2, 1)

    val vendorLbl = new Label("Vendor:") {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }

    GridPane.setConstraints(vendorLbl, 0, 1, 1, 1)

    val vendorCb = new ComboBox[Vendor] {
          maxWidth = 200
          promptText = "Make a choice..."
          items = vendors
        };

    GridPane.setConstraints(vendorCb, 1, 1, 3, 1)

    val quantityLbl = new Label("Quantity:") {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(quantityLbl, 0, 2, 1, 1)

    val quantityTxt = new TextField() {
      text = "100"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(quantityTxt, 1, 2, 4, 1)

    val costLbl = new Label("Cost:") {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(costLbl, 0, 3, 1, 1)

    val costTxt = new TextField() {
      text = "20"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(costTxt, 1, 3, 5, 1)

    val infoGrid = new GridPane {
      hgap = 4
      vgap = 6
      margin = Insets(18)
      children ++= Seq(productLbl, productCb, vendorLbl, vendorCb, quantityLbl, quantityTxt, costLbl, costTxt)
    }

    val saveBtn = new Button(Messages.data("save")) {
      onAction = (ae: ActionEvent) => {
          DBManager.session.beginTransaction();
          val productInv = new ProductInventory(0, productCb.getValue().id, vendorCb.getValue().id, quantityTxt.getText().toLong, costTxt.getText().toDouble)
          DBManager.session.save(productInv);
          DBManager.session.getTransaction().commit();
          DBManager.updateProductTotal(quantityTxt.getText().toLong, productCb.getValue().id)
      }
    }
    GridPane.setConstraints(saveBtn, 0, 0)
    GridPane.setMargin(saveBtn, Insets(10, 10, 10, 10))
    GridPane.setHalignment(saveBtn, HPos.Center)

    val cancelBtn = new Button(Messages.data("cancel")) {
      onAction = (ae: ActionEvent) => {
        EcStock.splitPane.items.remove(1)
            EcStock.splitPane.items.add(1,
              PageDisplayer.choosePage("layout > " + EcStockListInventory.objectName))
      }
    }
    GridPane.setConstraints(cancelBtn, 1, 0)
    GridPane.setMargin(cancelBtn, Insets(10, 10, 10, 10))
    GridPane.setHalignment(cancelBtn, HPos.Center)

    val actionCaution = new Label {
      text = "Save Data."
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