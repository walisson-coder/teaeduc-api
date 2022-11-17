package com.api.teaeduc.utils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class QueryStatement<T> implements Statement<T> {

    @PersistenceContext protected EntityManager entityManager;
    protected Query query;

    @Override
    @SuppressWarnings("unchecked")
    public List<T> asList(StringBuilder hql, Map<String, Object> parameters) {

        query = entityManager.createQuery(hql.toString());
        parameters.forEach(query::setParameter);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> asList(StringBuilder hql, Map<String, Object> parameters, Integer page, Integer pageSize) {

		query = entityManager.createQuery(hql.toString());

        if (page != null) {
            query.setFirstResult(page.intValue());
        }
        if (pageSize != null) {
            query.setMaxResults(pageSize.intValue());
        }
		if (page != null && pageSize != null) {
			int start = (page - 1) * pageSize;
			query.setFirstResult(start);
		}
		
        parameters.forEach(query::setParameter);
        return query.getResultList();
    }

    public int totalResult(StringBuilder hql, Map<String, Object> parameters) {

        query = entityManager.createQuery(hql.toString());
        parameters.forEach(query::setParameter);
        return ((Number) query.getSingleResult()).intValue();
    }

    private T mapByReflect(Class<T> clazz, Object[] objects) {

        List<Class<?>> tupleTypes = new ArrayList<>();
        for (Object object : objects) {
			if (object != null) {
				tupleTypes.add(object.getClass());
			} else {
				tupleTypes.add(null);
			}
        }
        try {
            Constructor<T> ctor = clazz.getConstructor(tupleTypes.toArray(new Class<?>[objects.length]));
            return ctor.newInstance(objects);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<T> map(Class<T> clazz, List<Object[]> objectList) {

        List<T> result = new ArrayList<>();
        for (Object[] objects : objectList) {
            result.add(mapByReflect(clazz, objects));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    /**
     * Sempre verificar se o Objeto VO ou DTO está com os contrutores preparados para receber os valores nativos do SQL.
     */
    public List<T> asList(Query query, Class<T> clazz) {
        List<Object[]> list = query.getResultList();
        return map(clazz, list);
    }
}
