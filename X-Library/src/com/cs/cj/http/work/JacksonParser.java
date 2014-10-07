//package com.cs.cj.http.work;
//
//import com.cs.cj.http.parserinterface.BaseParser;
//
//import org.codehaus.jackson.JsonParseException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//import java.io.IOException;
//
//public class JacksonParser<T> extends BaseParser<T> {
//    public static final String TAG = "FastJsonParser";
//    private static ObjectMapper sObjectMapper;
//    /** 解析类型对象 **/
//    private Class<T> mClazz;
//
//    /** jackson解析对象 **/
//
//    public JacksonParser(Class<T> clazz) {
//        this.mClazz = clazz;
//    }
//
//    public Class<T> getResponseClass() {
//        return mClazz;
//    }
//
//    public void setClass(Class<T> clazz) {
//        this.mClazz = clazz;
//    }
//
//    @SuppressWarnings("deprecation")
//	public static ObjectMapper getInstance() {
//
//        if (sObjectMapper == null) {
//            synchronized (ObjectMapper.class) {
//                if(sObjectMapper==null){
//                    sObjectMapper = new ObjectMapper();
//                    sObjectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//                    sObjectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//                    sObjectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
//                }
//            }
//        }
//        return sObjectMapper;
//    }
//
//
//    public static void clear() {
//        sObjectMapper = null;
//    }
//
//
//    public T parse(String rsp) throws JsonParseException, JsonMappingException, IOException, JSONException {
//
//        switch (parseType(rsp)) {
//            case BaseParserJSONObject:
//                JSONObject  paramObject = new JSONObject(rsp);
//                if(paramObject != null &&!TextUtils.isEmpty(paramObject.optString(CODE))){
//                    if (paramObject != null && paramObject.optString(CODE).equals(SUCCESS) ){
//                        JSONObject jsonObject = paramObject.optJSONObject(DATA);
//                        if (jsonObject != null) {
//                            return getInstance().readValue(jsonObject.toString(), mClazz);
//                        }else{
//                            Log.d(TAG, "没有数据可用");
//                            return null;
//                        }
//                    }else{
//                        Log.d(TAG, "服务器code 代码错误");
//                        return null;
//                    }
//
//                }
//                return getInstance().readValue(rsp, mClazz);
//
//            case BaseParserJSONArray:
//
//                break;
//            case BaseParserString:
//
//                break;
//
//            case BaseParserUnknown:
//                break;
//        }
//
//        return null;
//    }
//
//
//}
