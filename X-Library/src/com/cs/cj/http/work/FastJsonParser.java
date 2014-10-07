
package com.cs.cj.http.work;

import com.alibaba.fastjson.JSON;
import com.cs.cj.http.parserinterface.BaseParser;
import org.json.JSONException;
import org.json.JSONObject;
import android.text.TextUtils;
import android.util.Log;

public class FastJsonParser<T> extends BaseParser<T> {
    public static final String TAG = "FastJsonParser";

    private Class<T> mClazz ;
    
    public FastJsonParser(Class<T> clazz)
    {
    	mClazz = clazz ;
    }
    
    @Override
    public T parse(String rsp) throws JSONException {
        switch (parseType(rsp)) {
        case BaseParserJSONObject:
            JSONObject  paramObject = new JSONObject(rsp);
            if(paramObject != null &&!TextUtils.isEmpty(paramObject.optString(CODE))){
                if (paramObject != null && paramObject.optString(CODE).equals(SUCCESS) ){
                    JSONObject jsonObject = paramObject.optJSONObject(DATA);
                    if (jsonObject != null) {
                        return JSON.parseObject(jsonObject.toString(), mClazz);
                    }else{
                        Log.d(TAG, "没有数据可用");
                        return null;
                    }
                }else{
                    Log.d(TAG, "服务器code 代码错误");
                    return null;

                }

            }
            return JSON.parseObject(rsp, mClazz);

        case BaseParserJSONArray:

            break;
        case BaseParserString:
        	
        	//包含{}  并且第一个字符 到 第一个 { 之间不包含 [
            if(rsp.contains("{") && rsp.contains("}") && !rsp.substring(0, rsp.indexOf("{") + 1).contains("["))
        	{
        		String s = rsp.substring(rsp.indexOf("{"), rsp.lastIndexOf("}")+1);
        		 return JSON.parseObject(s, mClazz);
        	}
            
            break;

        case BaseParserUnknown:
            break;
        }

        return null;
    }
}