package scalafx.ecstock.models
import javax.persistence._

import scalafx.beans.property.{StringProperty, ObjectProperty}

@Entity
@Table(name = "product")
class Product(idParam: Int,val  name: String,val  retailPrice: Long, val vendor: Int,val brand: Int, val category: Int, val description: String, val imgSrc: String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = idParam

  @Transient
  var vendorName: String = _
  @Transient
  var brandName: String = _
  @Transient
  var categoryName: String = _

  @Transient
  val nameProperty = new StringProperty(this, "nameProperty", name)
  @Transient
  val retailPriceProperty = new StringProperty(this, "retailPriceProperty", retailPrice.toString)
  @Transient
  val vendorProperty = new StringProperty(this, "vendorProperty", vendor.toString)
  @Transient
  val brandProperty = new StringProperty(this, "brandProperty", brand.toString)
  @Transient
  val categoryProperty = new StringProperty(this, "categoryProperty", category.toString)
  @Transient
  val descriptionProperty = new StringProperty(this, "descriptionProperty", description)
  @Transient
  val imgSrcProperty = new StringProperty(this, "imgSrcProperty", imgSrc)
  override def toString = name

}
