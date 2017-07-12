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
class EcStockAddProduct extends EcStockExample {

  def getContent = {
    // infoGrid places the children by specifying the rows and columns in GridPane.setConstraints()
    val infoCaution = new Label {
      text = Messages.data("Information")
      wrapText = true
    }

    val categories = ObservableBuffer(DBManager.getCategories())

    val vendors = ObservableBuffer(DBManager.getVendors())

    val brands = ObservableBuffer(DBManager.getBrands())

    val nameLbl = new Label(Messages.data("Name:")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(nameLbl, 0, 0)

    val nameTxt = new TextField {text = "Product"}

    GridPane.setConstraints(nameTxt, 1, 0)

    val retailPriceLbl = new Label(Messages.data("Retail Price")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(retailPriceLbl, 0, 1)

    val retailPriceTxt = new TextField() {
      text = "0"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(retailPriceTxt, 1, 1)

    val boxPriceLbl = new Label(Messages.data("Box Price")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(boxPriceLbl, 0, 2)

    val boxPriceTxt = new TextField() {
      text = "0"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(boxPriceTxt, 1, 2)

    val unitCostLbl = new Label(Messages.data("Unit Cost")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(unitCostLbl, 0, 3)

    val unitCostTxt = new TextField() {
      text = "0"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(unitCostTxt, 1, 3)

    val boxSizeLbl = new Label(Messages.data("Box Size")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(boxSizeLbl, 0, 4)

    val boxSizeTxt = new TextField() {
      text = "0"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(boxSizeTxt, 1, 4)

    val boxCostLbl = new Label(Messages.data("Box Cost")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(boxCostLbl, 0, 5)

    val boxCostTxt = new TextField() {
      text = "0"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(boxCostTxt, 1, 5)

    val vendorLbl = new Label(Messages.data("Vendor")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(vendorLbl, 0, 6)

    val vendorCb = new ComboBox[Vendor] {
          maxWidth = 200
          promptText = Messages.data("Make a choice...")
          items = vendors
        };
    GridPane.setConstraints(vendorCb, 1, 6)

    val brandLbl = new Label(Messages.data("Brand")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(brandLbl, 0, 7)

    val brandCb = new ComboBox[Brand] {
          maxWidth = 200
          promptText = Messages.data("Make a choice...")
          items = brands
        };
    GridPane.setConstraints(brandCb, 1, 7)

    val categoryLbl = new Label(Messages.data("Category")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(categoryLbl, 0, 8)

    val categoryCb = new ComboBox[Category] {
          maxWidth = 200
          promptText = Messages.data("Make a choice...")
          items = categories
        };
    GridPane.setConstraints(categoryCb, 1, 8)

    val descriptionLbl = new Label(Messages.data("Description")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(descriptionLbl, 0, 9)

    val descriptionTxt = new TextField() {
      text = "Description"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(descriptionTxt, 1, 9)

    val totalLbl = new Label(Messages.data("Total")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(totalLbl, 0, 10)

    val totalTxt = new TextField() {
      text = "0"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(totalTxt, 1, 10)

    val limitLbl = new Label(Messages.data("Limit")) {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(limitLbl, 0, 11)

    val limitTxt = new TextField() {
      text = "0"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(limitTxt, 1, 11)

    val infoGrid = new GridPane {
      hgap = 2
      vgap = 10
      margin = Insets(18)
      children ++= Seq(nameLbl, nameTxt, retailPriceLbl, retailPriceTxt, boxPriceLbl, boxPriceTxt, unitCostLbl, unitCostTxt, boxCostLbl, boxCostTxt, boxSizeLbl, boxSizeTxt, vendorLbl, vendorCb, brandLbl, brandCb, categoryLbl, categoryCb, descriptionLbl, descriptionTxt, totalLbl, totalTxt, limitLbl, limitTxt)
    }

    val saveBtn = new Button(Messages.data("save")) {
      onAction = (ae: ActionEvent) => {
          DBManager.session.beginTransaction();
          val product = new Product(0, nameTxt.getText(), retailPriceTxt.getText().toDouble, boxPriceTxt.getText().toDouble, vendorCb.getValue().id, brandCb.getValue().id, categoryCb.getValue().id, descriptionTxt.getText(), "/scalafx/ecstock/products/product.png", totalTxt.getText().toLong, limitTxt.getText().toLong, unitCostTxt.getText().toDouble, boxCostTxt.getText().toDouble, boxSizeTxt.getText().toLong)
          DBManager.session.save(product);
          DBManager.session.getTransaction().commit();
          DBManager.session.clear()
          EcStock.splitPane.items.remove(1)
            EcStock.splitPane.items.add(1,
              PageDisplayer.choosePage("layout > " + EcStockListProduct.objectName))
      }
    }

    GridPane.setConstraints(saveBtn, 0, 0)
    GridPane.setMargin(saveBtn, Insets(10, 10, 10, 10))
    GridPane.setHalignment(saveBtn, HPos.Center)

    val cancelBtn = new Button(Messages.data("cancel")) {
      onAction = (ae: ActionEvent) => {
        EcStock.splitPane.items.remove(0)
            EcStock.splitPane.items.add(0,
              PageDisplayer.choosePage("layout > " + EcStockListProduct.objectName))
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