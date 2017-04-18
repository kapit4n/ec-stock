package scalafx.ecstock.ui.layout

import scalafx.Includes._
import scalafx.ecstock.commons.EcStockExample
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.control.{Button, Label, Separator, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, RowConstraints, VBox}
import slick.driver.JdbcProfile
//import scala.slick.driver.MySQLDriver.simple._
import slick.driver.MySQLDriver.api._
import scalafx.collections.ObservableBuffer
import scalafx.ecstock.models.Customer

/**
 *
 */
class EcStockAddCustomer extends EcStockExample {

  def getContent = {
    // infoGrid places the children by specifying the rows and columns in GridPane.setConstraints()
    object Customers extends Table[Customer]("customer"){
      def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
      def name = column[String]("name")
      def address = column[String]("address")
      def contact = column[String]("contact")
      def contact2 = column[String]("contact2")
      def * = (id, name, address, contact, contact2) <> ((Customer.apply _).tupled, Customer.unapply _)
      def forInsert = (name, address, contact, contact2) <> ({t => Customer(None, t._1, t._2, t._3, t._4)}, {(p: Person) => Some(p.name, p.address, p.contact, p.contact2)})
    }

    val customersTableModel = new ObservableBuffer[Customer]
    customersTableModel ++= Query(Customers).list

    val infoCaution = new Label {
      text = "Customer Information"
      wrapText = true
    }

    val nameLbl = new Label("Name:") {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(nameLbl, 0, 0, 1, 1)

    val nameTxt = new TextField {text = "Customer Name"}

    GridPane.setConstraints(nameTxt, 1, 0, 2, 1)

    val addressLbl = new Label("Address:") {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(addressLbl, 0, 1, 1, 1)

    val addressTxt = new TextField() {
      text = "Republica Av, Cochabamba City, BO"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(addressTxt, 1, 1, 5, 1)

    val contactLbl = new Label("Contact:") {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(contactLbl, 0, 2, 1, 1)

    val contactTxt = new TextField() {
      text = "Luis Arce"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(contactTxt, 1, 2, 5, 1)

    val contact2Lbl = new Label("Contact2:") {
      style = "-fx-font-weight:bold"
      alignmentInParent = Pos.BaselineRight
    }
    GridPane.setConstraints(contact2Lbl, 0, 3, 1, 1)

    val contact2Txt = new TextField() {
      text = "Hansel Arce"
      alignmentInParent = Pos.BaselineLeft
    }
    GridPane.setConstraints(contact2Txt, 1, 3 , 5, 1)

    val infoGrid = new GridPane {
      hgap = 4
      vgap = 6
      margin = Insets(18)
      children ++= Seq(nameLbl, nameTxt, addressLbl, addressTxt, contactLbl, contactTxt, contact2Lbl, contact2Txt)
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