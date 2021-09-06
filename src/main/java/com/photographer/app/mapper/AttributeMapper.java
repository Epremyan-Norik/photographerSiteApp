package com.photographer.app.mapper;


import java.util.List;

public interface AttributeMapper {

    Attribute getAttributeById(Long id);

    Attribute getAttributeByName(String name);

    List<Attribute> getAllAttributes();

    List<Attribute> getAllAttributesByType(Long type_id);

    int insertAttribute(Attribute attribute);

}
