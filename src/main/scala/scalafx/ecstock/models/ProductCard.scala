package scalafx.ecstock.models
import javax.persistence._
import scalafx.beans.property.{StringProperty}

@Entity
@Table(name = "card")
class ProductCard(idParam: Int, customer: Int, totalPrice: Double, observations: String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = idParam

  @Transient
  val customerProperty = new StringProperty(this, "customerProperty", customer.toString)

  @Transient
  val totalPriceProperty = new StringProperty(this, "totalPriceProperty", totalPrice.toString)

  @Transient
  val observationsProperty = new StringProperty(this, "observationsProperty", observations)
}
