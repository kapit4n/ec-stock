package scalafx.ecstock.models
import javax.persistence._
import scalafx.beans.property.{StringProperty}

@Entity
@Table(name = "cardItem")
class ProductCardItem(idParam: Int, var card: Int, val product: Int, var quantity: Int, var price: Double, var totalPrice: Double, productName: String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = idParam

  @Transient
  val cardProperty = new StringProperty(this, "cardProperty", card.toString)
  
  @Transient
  val productProperty = new StringProperty(this, "productProperty", product.toString)

  @Transient
  val productNameProperty = new StringProperty(this, "productNameProperty", productName)
  
  @Transient
  val quantityProperty = new StringProperty(this, "quantityProperty", quantity.toString)
  
  @Transient
  val priceProperty = new StringProperty(this, "priceProperty", price.toString)
  
  @Transient
  val totalPriceProperty = new StringProperty(this, "totalPriceProperty", totalPrice.toString)

}
