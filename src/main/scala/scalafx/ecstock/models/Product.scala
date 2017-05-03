package scalafx.ecstock.models
import javax.persistence._

import scalafx.beans.property.{StringProperty, ObjectProperty}

@Entity
@Table(name = "product")
class Product(idParam: Int, var name: String, var retailPrice: Double, boxPrice: Double, var vendor: Int,val brand: Int, var category: Int, var description: String, var imgSrc: String, var total: Long, var stockLimit: Long, var unitCost: Double, var boxCost: Double, var boxSize: Long) {
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
  val boxPriceProperty = new StringProperty(this, "boxPriceProperty", boxPrice.toString)
  @Transient
  val boxSizeProperty = new StringProperty(this, "boxSizeProperty", boxSize.toString)
  @Transient
  val unitCostProperty = new StringProperty(this, "unitCostProperty", unitCost.toString)
  override def toString = name

}
