package ru.sapteh.DAO;

import java.util.List;

public interface DAO <Entity, Key>{
    Entity read(Key key);
    List<Entity> findByAll();
    void create (Entity entity);
    void update (Entity entity);
    void delete (Entity entity);
}
