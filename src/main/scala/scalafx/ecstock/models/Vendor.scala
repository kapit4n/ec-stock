package scalafx.ecstock.models
import javax.persistence._

import scalafx.beans.property.{ObjectProperty, StringProperty}

@Entity
@Table(name = "vendor")
class Vendor(idParam: Int, var name: String, var address: String, var contact: String, var contact2: String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = idParam

  def this() {
      this(0, "", "", "", "")
  }

  @Transient
  val nameProperty = new StringProperty(this, "nameProperty", name)
  @Transient
  val addressProperty = new StringProperty(this, "addressProperty", address)
  @Transient
  val contactProperty = new StringProperty(this, "contactProperty", contact)
  @Transient
  val contact2Property = new StringProperty(this, "contact2Property", contact2)

  override def toString = name
}
