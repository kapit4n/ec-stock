package scalafx.ecstock.models
import javax.persistence._
import scalafx.beans.property.{StringProperty}

@Entity
@Table(name = "card")
class ProductCard(idParam: Int,val customer: Int,val totalPrice: Double,val observation: String, cName: String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = idParam

  @Transient
  var customerName = cName

  @Transient
  val customerProperty = new StringProperty(this, "customerProperty", customer.toString)

  @Transient
  val totalPriceProperty = new StringProperty(this, "totalPriceProperty", totalPrice.toString)

  @Transient
  val observationProperty = new StringProperty(this, "observationProperty", observation)

  @Transient
  val customerNameProperty = new StringProperty(this, "customerNameProperty", cName)
}
