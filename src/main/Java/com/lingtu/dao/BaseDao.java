package com.lingtu.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * TODO 数据层接口
 * 
 * @author xinglt
 * @date 2014年7月24日 下午3:23:14
 *
 */
public interface BaseDao {

	/**
	 * 查询实体数据
	 * 
	 * @param clz
	 * @return
	 */
	public <T> T getMapper(Class<T> clz);

	/**
	 * 返回单条数据
	 * 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public Object selectOne(String statement, Object parameter);
	
	
	/**
	 * 返回List集合数据
	 * 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public List<?> selectList(String statement, Object parameter);


	/**
	 * 返回Map数据
	 * 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public <K, V> Map<K, V> selectMap(String statement, String parameter);
	
	/**
	 * 返回Map数据，以Key值做为主键
	 * 
	 * @param statement
	 * @param parameter
	 * @param key
	 * @return
	 */
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String key);

	/**
	 * 插入数据
	 * 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public int insert(String statement, Object parameter);

	/**
	 * 更新数据
	 * 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public int update(String statement, Object parameter);

	/**
	 * 删除数据
	 * 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public int delete(String statement, Object parameter);

	
}
