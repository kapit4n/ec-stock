package scalafx.ecstock.models
import javax.persistence._
import scalafx.beans.property.{StringProperty}

@Entity
@Table(name = "productInventory")
class ProductInventory(product: String, vendor: String, quantity: String, cost: String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = _

  @Transient
  val productProperty = new StringProperty(this, "productProperty", product)
  @Transient
  val vendorProperty = new StringProperty(this, "vendorProperty", vendor)
  @Transient
  val quantityProperty = new StringProperty(this, "quantityProperty", quantity)
  @Transient
  val costProperty = new StringProperty(this, "costProperty", cost)
}
