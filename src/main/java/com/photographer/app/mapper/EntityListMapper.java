package com.photographer.app.mapper;

import java.util.List;

public interface EntityListMapper {
    EntityList getEntityById(Integer id);
    EntityList getEntityByName(String name);

    List<EntityList> getAllAvailableEntities();
}
