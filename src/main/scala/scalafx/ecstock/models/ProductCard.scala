package scalafx.ecstock.models

import scalafx.beans.property.{StringProperty}

class ProductCard(product_ : String, quantity_ : String) {
  val product = new StringProperty(this, "product", product_)
  val quantity = new StringProperty(this, "quantity", quantity_)
}
