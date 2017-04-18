package scalafx.ecstock.models
import org.hibernate._

object DBManager {
  val session: Session = HibernateUtil.getSessionFactory.openSession();
}
