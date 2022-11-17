package com.api.teaeduc.utils;

import java.util.List;
import java.util.Map;

public interface Statement<T> {

    List<T> asList(StringBuilder hql, Map<String, Object> parameters);
    List<T> asList(StringBuilder hql, Map<String, Object> parameters, Integer page, Integer pageSize);
}
