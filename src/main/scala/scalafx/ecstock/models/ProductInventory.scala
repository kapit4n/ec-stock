package scalafx.ecstock.models
import javax.persistence._
import scalafx.beans.property.{StringProperty}

@Entity
@Table(name = "productInventory")
class ProductInventory(idParam: Int, val product: Int, val vendor: Int, val quantity: Long, val cost: Double) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = idParam

  @Transient
  val productProperty = new StringProperty(this, "productProperty", product.toString)
  @Transient
  val vendorProperty = new StringProperty(this, "vendorProperty", vendor.toString)
  @Transient
  val quantityProperty = new StringProperty(this, "quantityProperty", quantity.toString)
  @Transient
  val costProperty = new StringProperty(this, "costProperty", cost.toString)
  override def toString = product.toString
}
