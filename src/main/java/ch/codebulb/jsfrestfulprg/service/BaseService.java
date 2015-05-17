package ch.codebulb.jsfrestfulprg.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ch.codebulb.jsfrestfulprg.model.Identifiable;

// Mocked implementation
public abstract class BaseService<T extends Identifiable> {
    private final Map<Long, T> ENTITIES = new HashMap<>();
    private long currentId = 0;
    
    public T findById(Long id) {
        return ENTITIES.get(id);
    }
    
    public List<T> findAll() {
        return new ArrayList<>(ENTITIES.values());
    }
    
    public void save(T entity) {
        // CREATE
        if (entity.getId() == null) {
            currentId = ++currentId;
            entity.setId(currentId);
        }
        // UPDATE
        ENTITIES.put(entity.getId(), entity);
    }
    
    public void delete(Long id) {
        ENTITIES.remove(id);
    }
}
