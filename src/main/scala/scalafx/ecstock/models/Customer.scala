package scalafx.ecstock.models
import javax.persistence._

import scalafx.beans.property.{ObjectProperty, StringProperty}

@Entity
@Table(name = "customer")
class Customer(idParam: Int, val name: String,val address: String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = idParam

  def this() {
      this(0, "", "")
  }
  
  @Transient
  val nameProperty = new StringProperty(this, "nameProperty", name)
  @Transient
  val addressProperty = new StringProperty(this, "addressProperty", address)

  override def toString = name
}
