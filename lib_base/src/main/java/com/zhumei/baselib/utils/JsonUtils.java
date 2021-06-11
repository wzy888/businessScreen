package com.zhumei.baselib.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Json解析辅助工具类
 * @author canhuah
 */
public class JsonUtils {

    /** 通过key获取value **/
    public static String getContent(String json, String key){
        JSONObject object = null;
        try {
            object = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(object == null) return "";
        if(key == null) return "";

        String result = null;
        try {
            Object obj = object.get(key);
            if (obj == null || obj.equals(null)){
                result = "";
            } else {
                result = obj.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** 将json转化为cls对象 **/
    public static <T> T json2Object(String jsonString, Class<T> cls) {
        T clazz = null;
        try {
            clazz = new GsonBuilder().create().fromJson(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public static <T> List<T> json2List(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                list.add(json2Object(object.toString(), cls));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    /** 将json转化为List<Map<String, Object>> **/
    public static List<Map<String, Object>> listKeyMaps(String response) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            GsonBuilder builder = new GsonBuilder();
            // 不转换没有 @Expose 注解的字段
            builder.excludeFieldsWithoutExposeAnnotation();
            Gson gson = builder.create();
            list = gson.fromJson(response, new TypeToken
                    <List<Map<String, Object>>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public static <T> List<T> parseString2List(String json, Class clazz) {

        List<T> list = new ArrayList<>();
        try {
            Type type = new ParameterizedTypeImpl(clazz);
            list =  new Gson().fromJson(json, type);
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    private static class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
