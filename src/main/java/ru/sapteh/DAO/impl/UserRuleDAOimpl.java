package ru.sapteh.DAO.impl;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.UserRule;

import java.util.List;

public class UserRuleDAOimpl implements DAO<UserRule,Integer> {
    private final SessionFactory factory;

    public UserRuleDAOimpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public UserRule read(Integer id) {
        try (Session session = factory.openSession()) {
            UserRule result = session.get(UserRule.class, id);

            //Используется при FetchType.LAZY
            if (result != null){
                Hibernate.initialize(result.getRuleId());
                Hibernate.initialize(result.getUserId());
            }
            return result;
        }
    }

    @Override
    public List<UserRule> findByAll() {
        try (Session session = factory.openSession()) {
            Query<UserRule> result = session.createQuery("FROM UserRule");
            return result.list();
        }
    }

    @Override
    public void create(UserRule userRule) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(userRule);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(UserRule userRule) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(userRule);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(UserRule userRule) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(userRule);
            session.getTransaction().commit();
        }
    }
}
