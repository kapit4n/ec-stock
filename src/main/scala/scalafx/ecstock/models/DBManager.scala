package scalafx.ecstock.models
import org.hibernate._
import java.sql.{Connection,DriverManager}
import scalafx.ecstock.models.Category
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
}
