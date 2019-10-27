package com.dayre.obscenerest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

@Component
public class SongResultDao {
   // private static final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    private final Session session =  HibernateSessionFactoryUtil.getSessionFactory().openSession();

    public void close(){
        session.close();
    }

    public SongResult findById(long id) {
        return session.get(SongResult.class, id);
    }

    public void save(SongResult song) {
            Transaction tx1 = session.beginTransaction();
            session.merge(song);
            tx1.commit();
    }

    public void delete(SongResult user) {
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
    }
}
