
package com.cs.cj.http.parserinterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.text.TextUtils;

public abstract class  BaseParser<T> {


    public  final static String  CODE="code";
    public  final static String  SUCCESS="0";
    public  final static String  DATA="data";

    public enum JSONType {
        BaseParserJSONObject, BaseParserJSONArray, BaseParserString ,BaseParserUnknown
    }

    public  abstract T parse(String result) throws Exception;


    /**
    * Returns Object of type {@link JSONObject}, {@link JSONArray}, String,
    * Boolean, Integer, Long, Double or {@link JSONObject#NULL}, see
    * {@link org.json.JSONTokener#nextValue()}
    *
    * @param responseBody
    *            response bytes to be assembled in String and parsed as JSON
    * @return Object parsedResponse
    * @throws org.json.JSONException
    *             exception if thrown while parsing JSON
    */
    protected JSONType parseType(String jsonString) throws JSONException {
        if (TextUtils.isEmpty(jsonString)){
            return null;
        }
        Object result = null;
        jsonString = jsonString.trim();
        if (jsonString.startsWith("{") || jsonString.startsWith("[")) {
            result = new JSONTokener(jsonString).nextValue();
        }
        
        if (result == null) {
            result = jsonString;
        }
        if (result instanceof JSONObject) {
            return JSONType.BaseParserJSONObject;
        } else if (result instanceof JSONArray) {
            return JSONType.BaseParserJSONArray;
        } else if (result instanceof String) {
            return JSONType.BaseParserString;
        }
        return JSONType.BaseParserUnknown;
    }



}


