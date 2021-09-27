package com.jinzhi.dao;

// 增删改查方法的接口

import java.io.Serializable;
import java.util.List;

public interface IDao<T> {
	
	public void save(T t);
	
	public void update(T t);
	
	public void del(Serializable id);           // Serializable作为数据类型就可以接收到各种类型的参数，例如：String、Long、int、Float
	
	public T findById(Serializable id);
	
	public List<T> list();
	
}