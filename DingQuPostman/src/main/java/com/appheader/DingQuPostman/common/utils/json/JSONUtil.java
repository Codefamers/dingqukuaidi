package com.appheader.DingQuPostman.common.utils.json;




import com.appheader.DingQuPostman.common.utils.json.annotation.JSONEntity;
import com.appheader.DingQuPostman.common.utils.json.annotation.JSONField;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON转换工具类 JSONObject装换为JavaBean需具备以下条件：
 * 
 * 1. JavaBean必须标注@JSONEntity注解
 * 
 * 
 * 2. 待转换的变量必须标注@JSONField注解，属性说明：
 * 
 * （1）jsonKey：该变量对应JSON中的字段名，如果不设置该值，默认为该变量的名称
 * （2）defaultStringValue：当该变量为String类型时，JSON中如果没有该字段，默认填充值
 * （3）defaultIntValue,defaultBooleanValue,defaulDoubleValue,defaultLongValue同理
 * 
 * 
 * 3. 支持的变量数据类型：
 * String，int/Integer,boolean/Boolean,long/Long,double/Double,List/
 * ArrayList,JavaBean
 * 
 * 
 * 4. 当变量类型为List或ArrayList时，需指定集合的Type，如List<T>，且T这个类必须也标注了@JSONEntity，
 * 且该T类中需要填充的变量也要标注@JSONEntity
 * 
 * 
 * 5. 当变量类型为JavaBean（自定义类），该类必须也标注了@JSONEntity，且该类中需要填充的变量也要标注@JSONEntity
 * 
 * @author alaowan
 * 
 */
public class JSONUtil {

	/**
	 * 将List集合转换为JSONArray
	 * 
	 * @param
	 * @param convertor
	 * @return
	 */
	public static <T> List<T> jsonArrayToList(JSONArray arr, ItemConvertor<T> convertor) {
		List<T> list = new ArrayList<T>();
		if (arr == null)
			return list;
		for (int i = 0; i < arr.length(); i++) {
			T object = convertor.convert(arr.optJSONObject(i));
			list.add(object);
		}
		return list;
	}

	/**
	 * JSONObject填充JavaBean
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T fromJSON(JSONObject json, Class<T> clazz) {
		T result;
		if (json == null)
			return null;
		try {
			result = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		JSONEntity entity = clazz.getAnnotation(JSONEntity.class);
		if (entity == null)
			throw new RuntimeException("Target object must add \"JSONEntity\" annotation.");
		Field[] fields = clazz.getDeclaredFields();
		// 遍历查找带有@JSONField的变量
		for (Field field : fields) {
			JSONField f = field.getAnnotation(JSONField.class);
			if (f == null)
				continue;
			try {
				String jsonKey = f.jsonKey().equals("") ? field.getName() : f.jsonKey();
				field.setAccessible(true);
				Object jsonValue = null;
				Class<?> fieldType = field.getType();
				if (fieldType.equals(String.class))
					jsonValue = json.optString(jsonKey, f.defaultStringValue().equals("") ? null : f.defaultStringValue());
				else if (fieldType.equals(Boolean.class) || fieldType.getName().equals("boolean"))
					jsonValue = json.optBoolean(jsonKey, f.defaultBooleanValue());
				else if (fieldType.equals(Integer.class) || fieldType.getName().equals("int"))
					jsonValue = json.optInt(jsonKey, f.defaultIntValue());
				else if (fieldType.equals(Double.class) || fieldType.getName().equals("double"))
					jsonValue = json.optDouble(jsonKey, f.defaultDoubleValue());
				else if (fieldType.equals(Long.class) || fieldType.getName().equals("long"))
					jsonValue = json.optLong(jsonKey, f.defaultLongValue());
				else if (fieldType.equals(List.class) || fieldType.equals(ArrayList.class)) {
					// 如果是集合，取集合中的类型
					ParameterizedType genericType = (ParameterizedType) field.getGenericType();
					Type[] rawType = genericType.getActualTypeArguments();
					Class rawClass = (Class) rawType[0];
					jsonValue = new ArrayList();
					JSONArray jsonArray = json.optJSONArray(jsonKey);
					if (jsonArray == null)
						continue;
					// 如果集合中的类标注了@JSONEntity，则递归解析该对象
					if (rawClass.getAnnotation(JSONEntity.class) != null) {
						for (int i = 0; i < jsonArray.length(); i++) {
							((ArrayList) jsonValue).add(fromJSON(jsonArray.optJSONObject(i), rawClass));
						}
					} else if (rawClass.equals(String.class)) {
						for (int i = 0; i < jsonArray.length(); i++) {
							((ArrayList) jsonValue).add(jsonArray.optString(i));
						}
					}else if(rawClass.equals(Double.class)){

						for (int i = 0; i < jsonArray.length(); i++) {
							((ArrayList) jsonValue).add(jsonArray.optDouble(i));
						}
					
					}
				} else {
					// 其他类型（JavaBean），判断如果也标注了@JSONEntity，则继续递归解析
					JSONEntity annotation = fieldType.getAnnotation(JSONEntity.class);
					if (annotation != null && json.has(jsonKey)) {
						Object subEntity = fromJSON(json.optJSONObject(jsonKey), fieldType);
						jsonValue = subEntity;
					}
				}
				field.set(result, jsonValue);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}
}
