package ru.sapteh.DAO.impl;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.Rule;
import ru.sapteh.model.User;

import javax.management.relation.Role;
import java.util.List;

public class RuleDAOimpl implements DAO<Rule,Integer> {
    private final SessionFactory factory;

    public RuleDAOimpl(SessionFactory factory) {
        this.factory = factory;
    }
    @Override
    public Rule read(Integer id) {
        try(Session session = factory.openSession()){
            Rule result = session.get(Rule.class,id);

            //Используется при FetchType.LAZY
            if(result != null){
                Hibernate.initialize(result.getUserRules());
            }
            return result;
        }
    }

    @Override
    public List<Rule> findByAll() {
        try(Session session = factory.openSession()){
            Query<Rule> result = session.createQuery("FROM Rule");
            return result.list();

        }
    }

    @Override
    public void create(Rule role) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(role);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Rule role) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(role);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Rule role) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(role);
            session.getTransaction().commit();
        }
    }
}
