package com.lingtu.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;




public class jsonUtil
{
	static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls()
			.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	/**
	 * 对象转化为json 字符串。
	 * 
	 * @param o
	 * @return
	 */
	public static String toJson(Object o)
	{
	
		String sret = gson.toJson(o);
		return sret;
	}
/**
 * 
 * @param jsonString : json 字符串， 
 * @param cls ： 返回的对象类型。 
 * @return
 */
	public static <T> T parseObject(String jsonString, Class<T> cls)
	{
		T t = null;
		try
		{
			
			t = gson.fromJson(jsonString, cls);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		return t;
	}
	
	/**
	 * 
	 * @param jsonString :json 字符串。 
	 * @param cls ： 返回的list 中每个元素的对象类型。 
	 * @return
	 */

	public static <T> List<T> parseList(String jsonString, Class<T> cls)
	{
		List<T> list = null;
		try
		{
			
			Type type = new ListParameterizedType(cls);
			list = gson.fromJson(jsonString, type);

		} catch (Exception e)
		{
		}
		return list;
	}

	
	
	/**
	 * 获取JsonObject
	 * @param json
	 * @return
	 */
	public static JsonObject parseJson(String json){
		JsonParser parser = new JsonParser();
	    JsonObject jsonObj = parser.parse(json).getAsJsonObject();
		return jsonObj;
	}
	
	private static JsonArray parseArray(String json)
	{
		JsonParser parser = new JsonParser();
		JsonArray jsonObj = parser.parse(json).getAsJsonArray();
		return jsonObj;
		
	}
	/**
	 * 根据json字符串返回Map对象
	 * @param json
	 * @return
	 */
	public static Map<String,Object> toMap(String json){
		return toMap(parseJson(json));
	}
	
	/**
	 * 将JSONObjec对象转换成Map-List集合
	 * @param json
	 * @return
	 */
	private static Map<String, Object> toMap(JsonObject json){
	    Map<String, Object> map = new HashMap<String, Object>();
	    Set<Entry<String, JsonElement>> entrySet = json.entrySet();
	    for (Iterator<Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext(); ){
	    	Entry<String, JsonElement> entry = iter.next();
	    	String key = entry.getKey();
	    	Object value = entry.getValue();
	        if(value instanceof JsonArray)
	            map.put((String) key, toList((JsonArray) value));
	        else if(value instanceof JsonObject)
	            map.put((String) key, toMap((JsonObject) value));
	        else
	            map.put((String) key, value);
	    }
	    return map;
	}
	
	
	
	/**
	 * 将JSONArray对象转换成List集合
	 * @param json
	 * @return
	 */
	private static List<Object> toList(JsonArray json){
	    List<Object> list = new ArrayList<Object>();
	    for (int i=0; i<json.size(); i++){
	    	Object value = json.get(i);
	    	if(value instanceof JsonArray){
	            list.add(toList((JsonArray) value));
	    	}
	        else if(value instanceof JsonObject){
	            list.add(toMap((JsonObject) value));
	        }
	        else{
	            list.add(value);
	        }
	    }
	    return list;
	}
	
	public static List<Object>  toList(String strjson){
		
		 return toList(parseArray(strjson));
		}
	
}

class ListParameterizedType implements ParameterizedType
{

	private Type type;

	public ListParameterizedType(Type type)
	{
		this.type = type;
	}

	@Override
	public Type[] getActualTypeArguments()
	{
		return new Type[] { type };
	}

	@Override
	public Type getRawType()
	{
		return ArrayList.class;
	}

	@Override
	public Type getOwnerType()
	{
		return null;
	}

	// implement equals method too! (as per javadoc)
}