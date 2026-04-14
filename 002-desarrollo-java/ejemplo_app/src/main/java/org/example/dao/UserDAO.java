package org.example.dao;

import org.example.model.User;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class UserDAO {

    public void save(User user) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    public void update(User user) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

    public void delete(User user) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    public User findById(Integer id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(User.class, id);
        }
    }

    public List<User> findAll() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM User", User.class).getResultList();
        }
    }
}
