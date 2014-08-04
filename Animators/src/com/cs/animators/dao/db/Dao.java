package com.cs.animators.dao.db;

import java.util.List;

public interface Dao<K,T> {
	
	public void save(T t);
	
	public void delete(K k);
	
	public void deleteAll();
	
	public void update(T t);
	
	public T query(K k);
	
	public List<T> queryAll();

}
