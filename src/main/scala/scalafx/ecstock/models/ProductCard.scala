package scalafx.ecstock.models
import javax.persistence._
import scalafx.beans.property.{StringProperty}

@Entity
@Table(name = "productCard")
class ProductCard(product : String, quantity : String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = _

  @Transient
  val productProperty = new StringProperty(this, "productProperty", product)
  @Transient
  val quantityProperty = new StringProperty(this, "quantityProperty", quantity)
}
