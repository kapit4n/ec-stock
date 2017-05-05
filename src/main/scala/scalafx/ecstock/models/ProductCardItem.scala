package scalafx.ecstock.models
import javax.persistence._
import scalafx.beans.property.{StringProperty}

@Entity
@Table(name = "cardItem")
class ProductCardItem(idParam: Int, var card: Int, val product: Int, var quantity: Int,
  var price: Double, var totalPrice: Double, var unitCost: Double, var totalCost: Double,
  productName: String, boxSizeP: Long, boxPriceP: Double, boxCostP: Double) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = idParam

  @Transient
  var boxSize = boxSizeP

  @Transient
  var boxPrice = boxPriceP

  @Transient
  var boxCost = boxCostP

  @Transient
  val cardProperty = new StringProperty(this, "cardProperty", card.toString)
  
  @Transient
  val productProperty = new StringProperty(this, "productProperty", product.toString)

  @Transient
  val productNameProperty = new StringProperty(this, "productNameProperty", productName)
  
  @Transient
  val quantityProperty = new StringProperty(this, "quantityProperty", quantity.toString)

  @Transient
  val boxQuantityProperty = new StringProperty(this, "quantityProperty", (quantity / boxSizeP).toString)
  
  @Transient
  val priceProperty = new StringProperty(this, "priceProperty", price.toString)
  
  @Transient
  val totalPriceProperty = new StringProperty(this, "totalPriceProperty", totalPrice.toString)

  @Transient
  val unitCostProperty = new StringProperty(this, "unitCostProperty", unitCost.toString)
  
  @Transient
  val totalCostProperty = new StringProperty(this, "totalCostProperty", totalCost.toString)

}
