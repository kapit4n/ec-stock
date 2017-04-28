package scalafx.ecstock.models
import javax.persistence._

import scalafx.beans.property.{ObjectProperty, StringProperty}

@Entity
@Table(name = "category")
	class Category(idParam: Int, var name: String, var description: String, val imgSrc: String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = idParam

  def this() {
      this(0, "", "", "")
  }

  @Transient
  val nameProperty = new StringProperty(this, "nameProperty", name)
  @Transient
  val descriptionProperty = new StringProperty(this, "descriptionProperty", description)
  @Transient
  val imgSrcProperty = new StringProperty(this, "imgSrcProperty", imgSrc)

  override def toString = name
}
