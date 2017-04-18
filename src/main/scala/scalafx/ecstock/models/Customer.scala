package scalafx.ecstock.models
import javax.persistence._

import scalafx.beans.property.{ObjectProperty, StringProperty}

@Entity
@Table(name = "customer")
case class Customer(name: String, address: String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = _
  @Transient
  val nameProperty = new StringProperty(this, "nameProperty", name)
  @Transient
  val addressProperty = new StringProperty(this, "addressProperty", address)
}
