package soapserver.repositories;

import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import soapserver.models.Log;
import soapserver.utils.HibernateUtil;

public class LoggingRepostitory {
  public boolean addNewLog(String host) {
    try {
      SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
      Session session = sessionFactory.getCurrentSession();

      session.beginTransaction();

      Log log = new Log();
      log.setHost(host);
      log.setTime(new Timestamp(System.currentTimeMillis()));
      session.save(log);

      session.getTransaction().commit();

      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }
}
