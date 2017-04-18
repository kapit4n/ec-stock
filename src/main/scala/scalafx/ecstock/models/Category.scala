package scalafx.ecstock.models
import javax.persistence._

import scalafx.beans.property.{ObjectProperty, StringProperty}

@Entity
@Table(name = "category")
class Category(val name : String, val description : String, val imgSrc : String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = _

  @Transient
  val nameProperty = new StringProperty(this, "nameProperty", name)
  @Transient
  val descriptionProperty = new StringProperty(this, "descriptionProperty", description)
  @Transient
  val imgSrcProperty = new StringProperty(this, "imgSrcProperty", imgSrc)

  override def toString = id + ")" + name
}
