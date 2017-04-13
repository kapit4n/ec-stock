package scalafx.ecstock.models

import scalafx.beans.property.{StringProperty}

class Product(name_ : String, retailPrice_ : String, vendor_ : String, brand_ : String, category_ : String, description_ : String) {
  val name = new StringProperty(this, "name", name_)
  val retailPrice = new StringProperty(this, "retailPrice", retailPrice_)
  val vendor = new StringProperty(this, "vendor", vendor_)
  val brand = new StringProperty(this, "brand", brand_)
  val category = new StringProperty(this, "category", category_)
  val description = new StringProperty(this, "description", description_)
}
