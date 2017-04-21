package scalafx.ecstock.models
import javax.persistence._

import scalafx.beans.property.{ObjectProperty, StringProperty}

@Entity
@Table(name = "customer")
case class Customer(val name: String,val address: String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = _
  def this() {
      this("", "")
  }
  @Transient
  val nameProperty = new StringProperty(this, "nameProperty", name)
  @Transient
  val addressProperty = new StringProperty(this, "addressProperty", address)
}
