package scalafx.ecstock.models

import org.hibernate.cfg.AnnotationConfiguration
import org.hibernate.SessionFactory

object HibernateUtil {
  private val sessionFactory = buildSessionFactory

  private def buildSessionFactory: SessionFactory = {
    try {
      // Create the SessionFactory from hibernate.cfg.xml
      return new AnnotationConfiguration().configure().buildSessionFactory();

    } catch {
      case ex: Throwable => {
        // Make sure you log the exception, as it might be swallowed
        System.err.println("Initial SessionFactory creation failed." + ex);
        throw new ExceptionInInitializerError(ex);
      }
    }
  }

  def getSessionFactory = sessionFactory


  def shutdown {
    // Close caches and connection pools
    getSessionFactory.close();
  }
}