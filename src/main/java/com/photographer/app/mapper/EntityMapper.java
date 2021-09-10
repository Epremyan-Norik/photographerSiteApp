package com.photographer.app.mapper;


import java.util.List;


public interface EntityMapper {

    Entity getEntityById(Long id);

    List<Entity> getAllEntitiesByType(Long type);

    List<Entity> getAllEntities();

    int deleteEntityById(Long id);

    int insertEntity(Entity entity);

    int newGuest();


}
