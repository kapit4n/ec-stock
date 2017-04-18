package scalafx.ecstock.models
import javax.persistence._

import scalafx.beans.property.{StringProperty}

@Entity
@Table(name = "product")
class Product(name : String, retailPrice : String, vendor : String, brand : String, category : String, description : String, imgSrc : String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = _

  @Transient
  val nameProperty = new StringProperty(this, "nameProperty", name)
  @Transient
  val retailPriceProperty = new StringProperty(this, "retailPriceProperty", retailPrice)
  @Transient
  val vendorProperty = new StringProperty(this, "vendorProperty", vendor)
  @Transient
  val brandProperty = new StringProperty(this, "brandProperty", brand)
  @Transient
  val categoryProperty = new StringProperty(this, "categoryProperty", category)
  @Transient
  val descriptionProperty = new StringProperty(this, "descriptionProperty", description)
  @Transient
  val imgSrcProperty = new StringProperty(this, "imgSrcProperty", imgSrc)
}
