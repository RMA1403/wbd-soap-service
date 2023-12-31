package soapserver.repositories;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import soapserver.models.Subscription;
import soapserver.utils.HibernateUtil;

public class SubscriptionRepository {
  public List<String> getMessages() {
    try {
      SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
      Session session = sessionFactory.getCurrentSession();

      session.beginTransaction();

      CriteriaBuilder builder = session.getCriteriaBuilder();
      CriteriaQuery<Subscription> criteria = builder.createQuery(Subscription.class);
      Root<Subscription> root = criteria.from(Subscription.class);
      criteria.select(root);

      List<Subscription> subscriptions = session.createQuery(criteria).getResultList();
      for (Subscription s : subscriptions) {
        System.out.println("kucing");
        System.out.println(s.getId_user());
        System.out.println(s.getExpiration_date());
      }

      session.getTransaction().commit();

      return new ArrayList<String>();
    } catch (Exception e) {
      System.out.println(e);
      return new ArrayList<String>();
    }
  }

  public boolean isSubscribed(int idUser) {
    try {
      SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
      Session session = sessionFactory.getCurrentSession();

      session.beginTransaction();

      CriteriaBuilder builder = session.getCriteriaBuilder();
      CriteriaQuery<Subscription> criteria = builder.createQuery(Subscription.class);
      Root<Subscription> root = criteria.from(Subscription.class);
      criteria.select(root).where(builder.equal(root.get("id_user"), idUser));

      List<Subscription> userSubData = session.createQuery(criteria).getResultList();

      session.getTransaction().commit();

      if (userSubData.size() <= 0) {
        return false;
      }

      Subscription userSubscription = userSubData.get(0);
      if (userSubscription == null) {
        return false;
      }

      // Check expiration time
      Timestamp expirationDate = userSubscription.getExpiration_date();
      Timestamp currentTime = new Timestamp(System.currentTimeMillis());
      if (expirationDate.compareTo(currentTime) < 0) {
        this.deleteSubscription(idUser);

        return false;
      }

      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  public boolean seedSubscription() {
    try {
      SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
      Session session = sessionFactory.getCurrentSession();

      session.beginTransaction();

      for (int i = 201; i <= 300; i++) {
        Subscription subscription = new Subscription();
        subscription.setId_user(i);
        subscription.setExpiration_date(new Timestamp(System.currentTimeMillis() + (1000 * 60 * 600)));
        session.save(subscription);
      }

      session.getTransaction().commit();

      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  public boolean addSubscription(int idUser) {
    try {
      SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
      Session session = sessionFactory.getCurrentSession();

      session.beginTransaction();
      Subscription subscription = new Subscription();
      subscription.setId_user(idUser);
      subscription.setExpiration_date(new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60)));
      session.save(subscription);

      session.getTransaction().commit();
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  public String getExpired(int idUser) {
    try {
      SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
      Session session = sessionFactory.getCurrentSession();

      session.beginTransaction();

      CriteriaBuilder builder = session.getCriteriaBuilder();
      CriteriaQuery<Subscription> criteria = builder.createQuery(Subscription.class);
      Root<Subscription> root = criteria.from(Subscription.class);
      criteria.select(root).where(builder.equal(root.get("id_user"), idUser));

      List<Subscription> userSubData = session.createQuery(criteria).getResultList();

      session.getTransaction().commit();

      if (userSubData.size() <= 0) {
        return "not subscribed";
      }

      Subscription userSubscription = userSubData.get(0);
      if (userSubscription == null) {
        return "not subscribed";
      }

      // Check expiration time
      Timestamp expirationDate = userSubscription.getExpiration_date();
      
      //Getting the calendar class instance
      Calendar calendar = Calendar.getInstance();

      // Passing the long value to calendar class function
      calendar.setTimeInMillis(expirationDate.getTime());
      
      DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' hh:mm a");
      String dateToString = df.format(expirationDate.toLocalDateTime());

      return dateToString;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return "error";
    }
  }


  public boolean extendSubscription(int idUser) {
    try {
      SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
      Session session = sessionFactory.getCurrentSession();

      session.beginTransaction();

      CriteriaBuilder builder = session.getCriteriaBuilder();
      CriteriaQuery<Subscription> criteria = builder.createQuery(Subscription.class);
      Root<Subscription> root = criteria.from(Subscription.class);
      criteria.select(root).where(builder.equal(root.get("id_user"), idUser));

      List<Subscription> userSubData = session.createQuery(criteria).getResultList();

      if (userSubData.size() <= 0) {
        return false;
      }

      Subscription userSubscription = userSubData.get(0);
      if (userSubscription == null) {
        return false;
      }

      // extend expiration date
      userSubscription.setExpiration_date(new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60)) );
      session.save(userSubscription);
      session.getTransaction().commit();

      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  public boolean deleteSubscription(int idUser) {
    try {
      SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
      Session session = sessionFactory.getCurrentSession();

      session.beginTransaction();

      Subscription subscription = session.find(Subscription.class, idUser);
      session.remove(subscription);
      session.flush();
      session.clear();

      session.getTransaction().commit();

      return true;
    } catch (Exception e) {
      SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
      Session session = sessionFactory.getCurrentSession();

      session.getTransaction().commit();

      System.out.println(e.getMessage());
      return false;
    }
  }
}
