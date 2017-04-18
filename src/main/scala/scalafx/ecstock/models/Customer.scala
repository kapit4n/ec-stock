package scalafx.ecstock.models

import scalafx.beans.property.{ObjectProperty, StringProperty}

case class Customer(id: Option[Int], name : String, address : String, contact : String, contact2 : String) {
  val nameField = new StringProperty(this, "nameField", name)
  val addressField = new StringProperty(this, "addressField", address)
  val contactField = new StringProperty(this, "contactField", contact)
  val contact2Field = new StringProperty(this, "contact2Field", contact2)
}

object Customer {
}