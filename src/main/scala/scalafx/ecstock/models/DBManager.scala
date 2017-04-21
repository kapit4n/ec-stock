package scalafx.ecstock.models
import org.hibernate._
import java.sql.{Connection,DriverManager}
import scalafx.Includes._
import scala.collection.mutable.ListBuffer

object DBManager {
  val session: Session = HibernateUtil.getSessionFactory.openSession()
  val url = "jdbc:mysql://localhost:3306/ecstock"
  val driver = "com.mysql.jdbc.Driver"
  val username = "root"
  val password = "root"

  def getCategories(): List[Category] = {
    var results = new ListBuffer[Category]()
    Class.forName(driver)
    var connection = DriverManager.getConnection(url, username, password)
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
    } finally {
      connection.close
    }
    return results.toList
  }

  def getCustomers(): List[Customer] = {
    var results = new ListBuffer[Customer]()
    Class.forName(driver)
    var connection = DriverManager.getConnection(url, username, password)
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
    } finally {
      connection.close
    }
    return results.toList
  }

  def getVendors(): List[Vendor] = {
    var results = new ListBuffer[Vendor]()
    Class.forName(driver)
    var connection = DriverManager.getConnection(url, username, password)
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
    } finally {
      connection.close
    }
    return results.toList
  }

  def getBrands(): List[Brand] = {
    var results = new ListBuffer[Brand]()
    Class.forName(driver)
    var connection = DriverManager.getConnection(url, username, password)
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
    } finally {
      connection.close
    }
    return results.toList
  }

  def getProducts(): List[Product] = {
    var results = new ListBuffer[Product]()
    Class.forName(driver)
    var connection = DriverManager.getConnection(url, username, password)
    try {
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT id, name, retailPrice, vendor, brand, category, description, total, stockLimit, imgSrc FROM product")
      while (rs.next) {
        val id = rs.getString("id").toInt
        val name = rs.getString("name")
        val retailPrice = rs.getString("retailPrice").toDouble
        val vendor = rs.getString("vendor").toInt
        val brand = rs.getString("brand").toInt
        val category = rs.getString("category").toInt
        val description = rs.getString("description")
        val total = rs.getString("total").toLong
        val stockLimit = rs.getString("stockLimit").toLong
        val imgSrc = rs.getString("imgSrc")
        results += new Product(id, name, retailPrice, vendor, brand, category, description, imgSrc, total, stockLimit)
      }
    } catch {
      case e: Exception => e.printStackTrace
    } finally {
      connection.close
    }
    return results.toList
  }

  def getProductInventories(): List[ProductInventory] = {
    var results = new ListBuffer[ProductInventory]()
    Class.forName(driver)
    var connection = DriverManager.getConnection(url, username, password)
    try {
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT id, product, vendor, quantity, cost FROM productInventory")
      while (rs.next) {
        val id = rs.getString("id").toInt
        val product = rs.getString("product").toInt
        val vendor = rs.getString("vendor").toInt
        val quantity = rs.getString("quantity").toLong
        val cost = rs.getString("cost").toDouble
        results += new ProductInventory(id, product, vendor, quantity, cost)
      }
    } catch {
      case e: Exception => e.printStackTrace
    } finally {
      connection.close
    }
    return results.toList
  }

  def updateProductTotal(amount: Long, productId: Int) = {
    Class.forName(driver)
    var connection = DriverManager.getConnection(url, username, password)
    try {
      val statement = connection.createStatement
      val rs = statement.executeUpdate("UPDATE product SET total = total + " + amount + "  WHERE id = " + productId)
      println(rs)
    } catch {
      case e: Exception => e.printStackTrace
    } finally {
      connection.close
    }
  }

}
