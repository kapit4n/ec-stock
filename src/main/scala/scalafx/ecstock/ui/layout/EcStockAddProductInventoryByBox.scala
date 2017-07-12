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
class EcStockAddProductInventoryByBox extends EcStockExample {
  var unitCostTxt:TextField = _
  var quantityTxt:TextField = _
  var boxCostTxt:TextField = _
  var boxSizeTxt:TextField = _
  var totalCostTxt:TextField = _


  def getContent = {
    // infoGrid places the children by specifying the rows and columns in GridPane.setConstraints()
    val infoCaution = new Label {
      text = Messages.data("Information")
      wrapText = true
    }

    val products = ObservableBuffer(DBManager.getProducts(DBManager.ALL))

    val vendors = ObservableBuffer(DBManager.getVendors())

    val productLbl = new Label(Messages.data("Product:")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }

    GridPane.setConstraints(productLbl, 0, 0)

    val productCb = new ComboBox[Product] {
          maxWidth = 200
          promptText = Messages.data("Make a choice...")
          items = products
          value.onChange {
            boxSizeTxt.setText(value().boxSize.toString)
            unitCostTxt.setText(value().unitCost.toString)
            boxCostTxt.setText(value().boxCost.toString)
          }
        }

    GridPane.setConstraints(productCb, 1, 0)

    val vendorLbl = new Label(Messages.data("Vendor")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }

    GridPane.setConstraints(vendorLbl, 0, 1)

    val vendorCb = new ComboBox[Vendor] {
          maxWidth = 200
          promptText = Messages.data("Make a choice...")
          items = vendors
        };

    GridPane.setConstraints(vendorCb, 1, 1)

    val quantityLbl = new Label(Messages.data("Box Quantity")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(quantityLbl, 0, 2)

    quantityTxt = new TextField() {
      text = "0"
      alignmentInParent = Pos.BaselineLeft
      text.onChange {
        if (!text().matches("\\d*")) {
          quantityTxt.setText(text().replaceAll("[^\\d]", ""))
        }
        if (text().isEmpty) {
          totalCostTxt.setText("0")
        } else {
          totalCostTxt.setText((text().toDouble * boxSizeTxt.getText().toDouble * boxCostTxt.getText().toDouble).toString)
        }
      }
    }
    GridPane.setConstraints(quantityTxt, 1, 2)

    val unitCostLbl = new Label(Messages.data("Unit Cost")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }

    GridPane.setConstraints(unitCostLbl, 0, 3)

    unitCostTxt = new TextField() {
      text = "0"
      alignmentInParent = Pos.BaselineLeft
      editable = false
    }
    GridPane.setConstraints(unitCostTxt, 1, 3)

    val boxSizeLbl = new Label(Messages.data("Box Quantity")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight

    }
    GridPane.setConstraints(boxSizeLbl, 0, 4)

    boxSizeTxt = new TextField() {
      text = "0"
      alignmentInParent = Pos.BaselineLeft
      editable = false
    }

    GridPane.setConstraints(boxSizeTxt, 1, 4)

    val boxCostLbl = new Label(Messages.data("Box Cost")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }

    GridPane.setConstraints(boxCostLbl, 0, 5)

    boxCostTxt = new TextField() {
      text = "0"
      alignmentInParent = Pos.BaselineLeft
      editable = false
    }
    GridPane.setConstraints(boxCostTxt, 1, 5)

    val totalCostLbl = new Label(Messages.data("Total Cost")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }

    GridPane.setConstraints(totalCostLbl, 0, 6)

    totalCostTxt = new TextField() {
      text = "0"
      editable = false
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(totalCostTxt, 1, 6)

    val infoGrid = new GridPane {
      hgap = 4
      vgap = 6
      margin = Insets(18)
      children ++= Seq(productLbl, productCb, vendorLbl, vendorCb, quantityLbl, quantityTxt, unitCostLbl, unitCostTxt, boxSizeLbl, boxSizeTxt, boxCostLbl, boxCostTxt, totalCostLbl, totalCostTxt)
    }

    val saveBtn = new Button(Messages.data("save")) {
      onAction = (ae: ActionEvent) => {
          DBManager.session.beginTransaction()
          val productInv = new ProductInventory(0, productCb.getValue().id, vendorCb.getValue().id, quantityTxt.getText().toLong * boxSizeTxt.getText().toLong, unitCostTxt.getText().toDouble, quantityTxt.getText().toDouble * boxSizeTxt.getText().toDouble * unitCostTxt.getText().toDouble, "", "")
          DBManager.session.save(productInv)
          DBManager.session.getTransaction().commit()
          DBManager.updateProductTotal(quantityTxt.getText().toLong * boxSizeTxt.getText().toLong, productCb.getValue().id)
          EcStock.splitPane.items.remove(0)
            EcStock.splitPane.items.add(0,
              PageDisplayer.choosePage("layout > " + EcStockListInventory.objectName))
      }
    }
    GridPane.setConstraints(saveBtn, 0, 0)
    GridPane.setMargin(saveBtn, Insets(10, 10, 10, 10))
    GridPane.setHalignment(saveBtn, HPos.Center)

    val cancelBtn = new Button(Messages.data("cancel")) {
      onAction = (ae: ActionEvent) => {
        EcStock.splitPane.items.remove(0)
          EcStock.splitPane.items.add(0,
            PageDisplayer.choosePage("layout > " + EcStockListInventory.objectName))
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