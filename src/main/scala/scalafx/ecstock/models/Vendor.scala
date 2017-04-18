package scalafx.ecstock.models
import javax.persistence._

import scalafx.beans.property.{ObjectProperty, StringProperty}

@Entity
@Table(name = "vendor")
class Vendor(name : String, address : String, contact : String, contact2 : String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = _

  @Transient
  val nameProperty = new StringProperty(this, "nameProperty", name)
  @Transient
  val addressProperty = new StringProperty(this, "addressProperty", address)
  @Transient
  val contactProperty = new StringProperty(this, "contactProperty", contact)
  @Transient
  val contact2Property = new StringProperty(this, "contact2Property", contact2)
}
