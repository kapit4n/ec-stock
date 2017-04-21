package scalafx.ecstock.models
import javax.persistence._

import scalafx.beans.property.{StringProperty, ObjectProperty}

@Entity
@Table(name = "product")
class Product(name: String, retailPrice: Long, vendor: Vendor, brand: Brand, category: Category, description: String, imgSrc: String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = _

  @Transient
  val nameProperty = new StringProperty(this, "nameProperty", name)
  @Transient
  val retailPriceProperty = new StringProperty(this, "retailPriceProperty", retailPrice.toString)
  @Transient
  val vendorProperty = new ObjectProperty(this, "vendorProperty", vendor)
  @Transient
  val brandProperty = new ObjectProperty(this, "brandProperty", brand)
  @Transient
  val categoryProperty = new ObjectProperty(this, "categoryProperty", category)
  @Transient
  val descriptionProperty = new StringProperty(this, "descriptionProperty", description)
  @Transient
  val imgSrcProperty = new StringProperty(this, "imgSrcProperty", imgSrc)
}
