package com.zml.oa.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zml.oa.dao.IBaseDao;

@Repository
public class BaseDaoImpl<T> implements IBaseDao<T> {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession() {
	    return sessionFactory.getCurrentSession();
	}
	
	@Override
	public Serializable add(T bean) throws Exception{
		return this.getSession().save(bean) ;
	}
	
	@Override
	public void saveOrUpdate(T bean) throws Exception{
		this.getSession().saveOrUpdate(bean);
	}
	
	@Override
	public void delete(T bean) throws Exception{
		this.getSession().delete(bean);
	}
	
	@Override
	public void update(T bean) throws Exception{
		this.getSession().update(bean);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> createQuery(String queryString) throws Exception{
		List<T> list = this.getSession().createQuery(queryString).list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getBean(Class<T> obj, Serializable id) throws Exception{
		return (T) getSession().get(obj, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByPage(String hql, int firstResult, int maxResult) throws Exception{
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T unique(String hql) throws Exception{
		Query query = getSession().createQuery(hql);
	    return (T) query.uniqueResult();
	}

}
