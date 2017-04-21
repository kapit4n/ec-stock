package scalafx.ecstock.models
import javax.persistence._
import scalafx.beans.property.{StringProperty}

@Entity
@Table(name = "productCardItem")
class ProductCardItem(idParam: Int, card: Int, product: Int, quantity: Int, price: Double, totalPrice: Double) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = idParam

  @Transient
  val cardProperty = new StringProperty(this, "cardProperty", card.toString)
  
  @Transient
  val productProperty = new StringProperty(this, "productProperty", product.toString)
  
  @Transient
  val quantityProperty = new StringProperty(this, "quantityProperty", quantity.toString)
  
  @Transient
  val priceProperty = new StringProperty(this, "priceProperty", price.toString)
  
  @Transient
  val totalPriceProperty = new StringProperty(this, "totalPriceProperty", totalPrice.toString)
}
