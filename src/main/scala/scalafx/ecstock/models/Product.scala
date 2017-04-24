package scalafx.ecstock.models
import javax.persistence._

import scalafx.beans.property.{StringProperty, ObjectProperty}

@Entity
@Table(name = "product")
class Product(idParam: Int, val name: String, val retailPrice: Double, val vendor: Int,val brand: Int, val category: Int, val description: String, val imgSrc: String, val total: Long, val stockLimit: Long, val unitCost: Double, val boxCost: Double, val boxSize: Long) {
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
  @Transient
  val totalProperty = new StringProperty(this, "totalProperty", total.toString)
  @Transient
  val limitProperty = new StringProperty(this, "limitProperty", stockLimit.toString)
  @Transient
  val boxCostProperty = new StringProperty(this, "boxCostProperty", boxCost.toString)
  @Transient
  val boxSizeProperty = new StringProperty(this, "boxSizeProperty", boxSize.toString)
  @Transient
  val unitCostProperty = new StringProperty(this, "unitCostProperty", unitCost.toString)
  override def toString = name

}
