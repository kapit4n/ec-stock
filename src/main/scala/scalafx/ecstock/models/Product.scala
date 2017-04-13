package scalafx.ecstock.models

import scalafx.beans.property.{ObjectProperty, StringProperty, LongProperty, DoubleProperty}

class Product(name_ : String, description_ : String, retailPrice_ : Double, vendor_ : Long, brand_ : Long, category_ : Long) {
  val name = new StringProperty(this, "name", name_)
  val description = new StringProperty(this, "description", description_)
  val retailPrice = new DoubleProperty(this, "retailPrice", retailPrice_)
  val vendor = new LongProperty(this, "vendor", vendor_)
  val brand = new LongProperty(this, "brand", brand_)
  val category = new LongProperty(this, "category", category_)
}
