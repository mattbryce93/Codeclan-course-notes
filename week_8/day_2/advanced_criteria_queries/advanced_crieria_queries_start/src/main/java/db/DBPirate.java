package db;

import models.Pirate;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class DBPirate {

    private static Session session;
    private static Transaction transaction;


    public static List<Pirate> getAll(){
        List<Pirate> results = null;
        session = HibernateUtil.getSessionFactory().openSession();
        try{
            Criteria cr = session.createCriteria(Pirate.class);
            results = cr.list();

        } catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public static Pirate find(int id) {
        Pirate result = null;
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria cr = session.createCriteria(Pirate.class);
            cr.add(Restrictions.eq("id", id));
            result = (Pirate)cr.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    // DBPirate
    public static Double getAverageAge(){
        session = HibernateUtil.getSessionFactory().openSession();
        Double average = null;
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(Pirate.class);
            cr.setProjection(Projections.avg("age"));
            average = (Double)cr.uniqueResult();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return average;
    }
}
