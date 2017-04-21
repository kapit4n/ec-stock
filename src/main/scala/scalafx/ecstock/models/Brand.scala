package scalafx.ecstock.models
import javax.persistence._

import scalafx.beans.property.{ObjectProperty, StringProperty}

@Entity
@Table(name = "brand")
class Brand(idParam: Int, val name: String, val description: String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = idParam
  def this() {
      this(0, "", "")
  }

  @Transient
  val nameProperty = new StringProperty(this, "nameProperty", name)
  @Transient
  val descriptionProperty = new StringProperty(this, "descriptionProperty", description)
  override def toString = name
}
