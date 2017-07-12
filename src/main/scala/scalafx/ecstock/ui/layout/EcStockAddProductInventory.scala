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

  var unitCostTxt: TextField = _
  var totalCostTxt: TextField = _
  var quantityTxt: TextField = _
  var productSearchTxt: TextField = _

  def calTotalCost() = {
    totalCostTxt.setText((quantityTxt.getText().toDouble * unitCostTxt.getText().toDouble).toString)
  }

  def getContent = {
    // infoGrid places the children by specifying the rows and columns in GridPane.setConstraints()
    val infoCaution = new Label {
      text = Messages.data("Information")
      wrapText = true
    }

    var products = ObservableBuffer(DBManager.getProducts(DBManager.ALL))

    val vendors = ObservableBuffer(DBManager.getVendors())

    val productLbl = new Label(Messages.data("Product")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }

    GridPane.setConstraints(productLbl, 0, 0)

    val productCb = new ComboBox[Product] {
        maxWidth = 200
        promptText = Messages.data("Make a choice...")
        items = products
        value.onChange {
          try {
            unitCostTxt.setText(value().unitCost.toString)
            calTotalCost()
          } catch {
            case e: Exception => {}
          }
        }
      }

    GridPane.setConstraints(productCb, 1, 0)

    productSearchTxt = new TextField() {
      text = ""
      alignmentInParent = Pos.BaselineLeft
      text.onChange {
        DBManager.likeStr = text()
        products = ObservableBuffer(DBManager.getProducts(DBManager.LIKE))
        productCb.items = products
      }
    }
    GridPane.setConstraints(productSearchTxt, 3, 0)
    
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

    val quantityLbl = new Label(Messages.data("Quantity")) {
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
        calTotalCost()
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

    val totalCostLbl = new Label(Messages.data("Total Cost")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }

    GridPane.setConstraints(totalCostLbl, 0, 4)

    totalCostTxt = new TextField() {
      text = "0"
      alignmentInParent = Pos.BaselineLeft
      editable = false
    }
    GridPane.setConstraints(totalCostTxt, 1, 4)

    val infoGrid = new GridPane {
      hgap = 4
      vgap = 6
      margin = Insets(18)
      children ++= Seq(productLbl, productCb, productSearchTxt, vendorLbl, vendorCb, quantityLbl, quantityTxt, unitCostLbl, unitCostTxt, totalCostLbl, totalCostTxt)
    }

    val saveBtn = new Button(Messages.data("save")) {
      onAction = (ae: ActionEvent) => {
          DBManager.session.beginTransaction();
          val productInv = new ProductInventory(0, productCb.getValue().id, vendorCb.getValue().id, quantityTxt.getText().toLong, unitCostTxt.getText().toDouble, totalCostTxt.getText().toDouble, "", "")
          DBManager.session.save(productInv);
          DBManager.session.getTransaction().commit();
          DBManager.updateProductTotal(quantityTxt.getText().toLong, productCb.getValue().id)
          DBManager.session.clear()
          EcStock.splitPane.items.remove(1)
            EcStock.splitPane.items.add(1,
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