package scalafx.ecstock.ui.layout

import scalafx.Includes._
import scalafx.ecstock.commons.EcStockExample
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.{Button, Label, Separator, TextField, ComboBox}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, RowConstraints, VBox}
import scalafx.collections.ObservableBuffer

/**
 *
 */
class EcStockAddProduct extends EcStockExample {

  def getContent = {
    // infoGrid places the children by specifying the rows and columns in GridPane.setConstraints()
    val infoCaution = new Label {
      text = "Product Information"
      wrapText = true
    }

    val categories = ObservableBuffer(
      "Category 1", "Category 2", "Category 3",
      "Category 4", "Category 5", "Category 6",
      "Longer ComboBox item",
      "Category 7", "Category 8", "Category 9",
      "Category 10", "Category 12")

    val vendors = ObservableBuffer(
      "Vendor 1", "Vendor 2", "Vendor 3",
      "Vendor 4", "Vendor 5", "Vendor 6",
      "Longer ComboBox item",
      "Vendor 7", "Vendor 8", "Vendor 9",
      "Vendor 10", "Vendor 12")

    val brands = ObservableBuffer(
      "Brand 1", "Brand 2", "Brand 3",
      "Brand 4", "Brand 5", "Brand 6",
      "Longer ComboBox item",
      "Brand 7", "Brand 8", "Brand 9",
      "Brand 10", "Brand 12")

    val nameLbl = new Label("Name:") {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(nameLbl, 0, 0, 1, 1)

    val nameTxt = new TextField {text = "Product Name"}

    GridPane.setConstraints(nameTxt, 1, 0, 2, 1)

    val retailPriceLbl = new Label("Retail Price:") {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(retailPriceLbl, 0, 1, 1, 1)

    val retailPriceTxt = new TextField() {
      text = "100 Bs"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(retailPriceTxt, 1, 1, 5, 1)

    val vendorLbl = new Label("Vendor:") {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(vendorLbl, 0, 2, 1, 1)

    val vendorCb = new ComboBox[String] {
          maxWidth = 200
          promptText = "Make a choice..."
          items = vendors
        };
    GridPane.setConstraints(vendorCb, 1, 2, 5, 1)

    val brandLbl = new Label("Brand:") {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(brandLbl, 0, 3, 1, 1)

    val brandCb = new ComboBox[String] {
          maxWidth = 200
          promptText = "Make a choice..."
          items = brands
        };
    GridPane.setConstraints(brandCb, 1, 3 , 5, 1)

    val categoryLbl = new Label("Category:") {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(categoryLbl, 0, 4, 1, 1)

    val categoryCb = new ComboBox[String] {
          maxWidth = 200
          promptText = "Make a choice..."
          items = categories
        };
    GridPane.setConstraints(categoryCb, 1, 4 , 6, 1)

    val descriptionLbl = new Label("Description:") {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(descriptionLbl, 0, 5, 1, 1)

    val descriptionTxt = new TextField() {
      text = "100 Bs"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(descriptionTxt, 1, 5, 7, 1)



    val infoGrid = new GridPane {
      hgap = 4
      vgap = 6
      margin = Insets(18)
      children ++= Seq(nameLbl, nameTxt, retailPriceLbl, retailPriceTxt, vendorLbl, vendorCb, brandLbl, brandCb, categoryLbl, categoryCb, descriptionLbl, descriptionTxt)
    }

    val saveBtn = new Button("SAVE")
    GridPane.setConstraints(saveBtn, 0, 0)
    GridPane.setMargin(saveBtn, Insets(10, 10, 10, 10))
    GridPane.setHalignment(saveBtn, HPos.Center)

    val cancelBtn = new Button("CANCEL")
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