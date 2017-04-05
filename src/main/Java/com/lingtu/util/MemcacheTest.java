package com.lingtu.util;

import java.util.ArrayList;
import java.util.Date;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.lingtu.util.jsonUtil;

public class MemcacheTest
{

	  static { 
	        // 服务器列表和其权重，个人memcached地址和端口号 
	        String[] servers = {"127.0.0.1:11211"}; 
	        Integer[] weights = {3}; 

	        // 获取socke连接池的实例对象 
	        SockIOPool pool = SockIOPool.getInstance(); 

	        // 设置服务器信息 
	        pool.setServers(servers); 
	        pool.setWeights(weights); 

	        // 设置初始连接数、最小和最大连接数以及最大处理时间 
	        pool.setInitConn(1); 
	        pool.setMinConn(5); 
	        pool.setMaxConn(250); 
	        pool.setMaxIdle(1000 * 60 * 60 * 6); 

	        // 设置主线程的睡眠时间 
	        pool.setMaintSleep(30); 

	        // 设置TCP的参数，连接超时等 
	        pool.setNagle(false); 
	        pool.setSocketTO(3000); 
	        pool.setSocketConnectTO(0); 

	        // 初始化连接池 
	        pool.initialize(); 
	    } 
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		//对象
/*		jobs job = new jobs();
		job.setJob_id("1");
		job.setJob_lowsalary(100);
		job.setJob_title("oo");
		job.setMin_salary(60);		
		*/
//		// 获取数据
//		putData("job",job);
//		System.out.println(getData("jobs")+"===");
//		//对象转化为json
//		String j = jsonUtil.toJson(job);
//		putData("jobs",j);
//		Object o1 = getData("jobs");
//		System.out.println(o1);
//		//序列化对象
//		jobs o = (jobs)getData("job");
//		System.out.println (o.getJob_title());
		//存储数据， 字符，数字Integer对象 集合
/*		ArrayList<jobs> list = new ArrayList();
		for (int i = 0; i < 5; i++) {
			list.add(job);
		}
		putData("list",list);
		ArrayList<jobs> o1 = (ArrayList)getData("list");*/
//		for (int i = 0; i < 5; i++) {
//			System.out.println(o1.get(1).getJob_title());
//		}
		
		
	}
	
	//添加数据
	public static void putData(String key , Object oval)
	{
		MemCachedClient memclient=new MemCachedClient();
		
		memclient.set(key,oval);
		
	}
	
	// 查询数据。
	public static Object getData(String key)
	{
		MemCachedClient memclient=new MemCachedClient();
		Object o = memclient.get(key);
		return o;
		
	}
	// 查询数据。
	public static void setData(String key,Object value,Date date)
	{
		MemCachedClient memclient=new MemCachedClient();
		memclient.set(key, value, date);
	}
	//
//	public static void putData(String key,Object o,int time){
//		MemCachedClient memclient=new MemCachedClient();
//		memclient.set(key, o, new java.util.)
//	}
	// 删除 key，
	public static boolean deleteData(String key){
		MemCachedClient memclient=new MemCachedClient();
		return memclient.delete(key);
		
	}
	// 修改。
	
}
