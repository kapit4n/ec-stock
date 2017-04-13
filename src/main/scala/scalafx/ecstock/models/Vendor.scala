package scalafx.ecstock.models

import scalafx.beans.property.{ObjectProperty, StringProperty}

class Vendor(name_ : String, address_ : String, contact_ : String, contact2_ : String) {
  val name = new StringProperty(this, "name", name_)
  val address = new StringProperty(this, "address", address_)
  val contact = new StringProperty(this, "contact", contact_)
  val contact2 = new StringProperty(this, "contact2", contact2_)
}
