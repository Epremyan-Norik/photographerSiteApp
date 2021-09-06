package com.photographer.app.mapper;

import java.util.List;
import java.util.Map;

public interface ValueMapper {

    int insertValue(Value value);

    int updateValue(Value value);

    Value getUserIdByUsername(String username);

    Value getValueByIds(Map<String, Long> map);

    List<Value> getValues();

    List<Value> getValuesByAttId(long att_id);

}
