package scalafx.ecstock.models
import org.hibernate._
import java.sql.{Connection,DriverManager}
import scalafx.Includes._
import scala.collection.mutable.ListBuffer
import java.time.LocalDate

object DBManager {
  val ALL = 0
  val LAST_MONTH = 1
  val TODAY = 2
  val RANGE = 3
  val OUT_OF_STOCK = 4
  val LIKE = 5
  var toDate:LocalDate = _
  var fromDate:LocalDate = _
  var likeStr:String = _

  val session: Session = HibernateUtil.getSessionFactory.openSession()
  val url = "jdbc:mysql://localhost:3306/ecstock"
  val driver = "com.mysql.jdbc.Driver"
  val username = "root"
  val password = "root"
  Class.forName(driver)
  var connection = DriverManager.getConnection(url, username, password)
  
  def closeConnection() = {
    connection.close
  }

  def getCategories(): List[Category] = {
    var results = new ListBuffer[Category]()
    try {
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT id, name, description, imgSrc FROM category")
      while (rs.next) {
        val id = rs.getString("id").toInt
        val name = rs.getString("name")
        val description = rs.getString("description")
        val imgSrc = rs.getString("imgSrc")
        results += new Category(id, name, description, imgSrc)
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    return results.toList
  }

  def getCustomers(): List[Customer] = {
    var results = new ListBuffer[Customer]()
    try {
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT id, name, address FROM customer")
      while (rs.next) {
        val id = rs.getString("id").toInt
        val name = rs.getString("name")
        val address = rs.getString("address")
        results += new Customer(id, name, address)
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    return results.toList
  }

  def getVendors(): List[Vendor] = {
    var results = new ListBuffer[Vendor]()
    try {
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT id, name, address, contact, contact2 FROM vendor")
      while (rs.next) {
        val id = rs.getString("id").toInt
        val name = rs.getString("name")
        val address = rs.getString("address")
        val contact = rs.getString("contact")
        val contact2 = rs.getString("contact2")
        results += new Vendor(id, name, address, contact, contact2)
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    return results.toList
  }

  def getBrands(): List[Brand] = {
    var results = new ListBuffer[Brand]()
    try {
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT id, name, description FROM brand")
      while (rs.next) {
        val id = rs.getString("id").toInt
        val name = rs.getString("name")
        val description = rs.getString("description")
        results += new Brand(id, name, description)
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    return results.toList
  }

  def getProducts(filterValue: Int): List[Product] = {
    var results = new ListBuffer[Product]()
    try {
      val statement = connection.createStatement
      val where = getFilter(filterValue, "")
      val fields = "id, name, retailPrice, boxPrice, vendor, brand, category, description, total, stockLimit, boxCost, unitCost, boxSize, imgSrc"
      val fromJoin = "FROM product"
      val rs = statement.executeQuery(f"SELECT $fields $fromJoin $where")
      while (rs.next) {
        results += new Product(rs.getString("id").toInt, rs.getString("name"), rs.getString("retailPrice").toDouble, 
          rs.getString("boxPrice").toDouble,
          rs.getString("vendor").toInt, rs.getString("brand").toInt, rs.getString("category").toInt,
          rs.getString("description"), rs.getString("imgSrc"), rs.getString("total").toLong,
          rs.getString("stockLimit").toLong, rs.getString("unitCost").toDouble, rs.getString("boxCost").toDouble,
          rs.getString("boxSize").toLong)
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    return results.toList
  }

  def getCards(filterValue:Int): List[ProductCard] = {
    var results = new ListBuffer[ProductCard]()
    try {
      val statement = connection.createStatement
      val where = getFilter(filterValue, "c.")
      val fields = "c.id, customer, totalPrice, observation, cs.name as customerName"
      val orderBy = "order by c.createdAt DESC"
      val fromJoin = "FROM card AS c STRAIGHT_JOIN customer AS cs ON c.customer = cs.id"
      val rs = statement.executeQuery(f"SELECT $fields $fromJoin $where $orderBy")
      while (rs.next) {
        val id = rs.getString("id").toInt
        val customer = rs.getString("customer").toInt
        val totalPrice = rs.getString("totalPrice").toDouble
        val observation = rs.getString("observation")
        val customerName = rs.getString("customerName")
        results += new ProductCard(id, customer, totalPrice, observation, customerName)
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    return results.toList
  }

  def getCardItems(): List[ProductCardItem] = {
    var results = new ListBuffer[ProductCardItem]()
    try {
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT p.id, card, product, quantity, price, totalPrice, p.name as productName FROM cardItem AS ci STRAIGHT_JOIN product AS p ON ci.product = p.id order by ci.createdAt DESC")
      while (rs.next) {
        val id = rs.getString("id").toInt
        val card = rs.getString("card").toInt
        val product = rs.getString("product").toInt
        val quantity = rs.getString("quantity").toInt
        val price = rs.getString("price").toDouble
        val totalPrice = rs.getString("totalPrice").toDouble
        val productName = rs.getString("productName")
        results += new ProductCardItem(id, card, product, quantity, price, totalPrice, productName)
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    return results.toList
  }

  def getFilter(filterValue:Int, prefix: String) : String = {
    var filter = ""
    val dateStr = prefix + "createdAt"

    if (filterValue == TODAY)
      filter = f"WHERE DATE($dateStr) = CURDATE() "

    if (filterValue == LAST_MONTH)
      filter = f"WHERE YEAR($dateStr) = YEAR(CURDATE()) AND MONTH($dateStr) = MONTH(CURDATE()) "

    if (filterValue == RANGE)
      filter = f"WHERE DATE($dateStr) >= '" + DBManager.fromDate.getYear() + "-"  + DBManager.fromDate.getMonthValue() + "-" + DBManager.fromDate.getDayOfMonth() + f"' AND DATE($dateStr) <= '" + DBManager.toDate.getYear() + "-"  + DBManager.toDate.getMonthValue() + "-" + DBManager.toDate.getDayOfMonth() + "'"

    if (filterValue == OUT_OF_STOCK)
      filter = f"WHERE $prefix%sstockLimit > $prefix%stotal"

    if (filterValue == LIKE)
      filter = f" WHERE $prefix%sname like '%%$likeStr%s%%'"

    return filter
  }

  /**
   * Get all product inventories
  */
  def getProductInventories(filterValue:Int): List[ProductInventory] = {
    
    var results = new ListBuffer[ProductInventory]()
    try {
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT pi.id, product, pi.vendor, quantity, cost, pi.totalCost, p.name as productName, v.name as vendorName FROM productInventory as pi STRAIGHT_JOIN product AS p ON pi.product = p.id STRAIGHT_JOIN vendor AS v ON pi.vendor = v.id " + getFilter(filterValue, "pi.") )
      while (rs.next) {
        val id = rs.getString("id").toInt
        val product = rs.getString("product").toInt
        val vendor = rs.getString("vendor").toInt
        val quantity = rs.getString("quantity").toLong
        val cost = rs.getString("cost").toDouble
        val totalCost = rs.getString("totalCost").toDouble
        val productName = rs.getString("productName")
        val vendorName = rs.getString("vendorName")
        results += new ProductInventory(id, product, vendor, quantity, cost, totalCost, productName, vendorName)
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    return results.toList
  }

  def updateProductTotal(amount: Long, productId: Int) = {
    try {
      val statement = connection.createStatement
      val rs = statement.executeUpdate("UPDATE product SET total = total + " + amount + "  WHERE id = " + productId)
    } catch {
      case e: Exception => e.printStackTrace
    }
  }

}
