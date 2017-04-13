package scalafx.ecstock.models

import scalafx.beans.property.{StringProperty}

class ProductInventory(product_ : String, vendor_ : String, quantity_ : String, cost_ : String) {
  val product = new StringProperty(this, "product", product_)
  val vendor = new StringProperty(this, "vendor", vendor_)
  val quantity = new StringProperty(this, "quantity", quantity_)
  val cost = new StringProperty(this, "cost", cost_)
}
