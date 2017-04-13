package scalafx.ecstock.models

import scalafx.beans.property.{ObjectProperty, StringProperty}

class Category(name_ : String, description_ : String) {
  val name = new StringProperty(this, "name", name_)
  val description = new StringProperty(this, "description", description_)
}
