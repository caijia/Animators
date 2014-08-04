package com.cs.cj.http.work;

import java.util.List;
import com.alibaba.fastjson.JSON;
import com.cs.cj.http.parserinterface.BaseParser;

public class FastJsonParserArray<T> extends BaseParser<List<T>> {
	public static final String TAG = "FastJsonParserArray";
	private Class<T> clazz ;
	
	public FastJsonParserArray(Class<T> clazz){
		this.clazz = clazz ;
	}
	
	@Override
	public List<T> parse(String result) throws Exception {
		
		switch (parseType(result)) {
		
		case BaseParserJSONArray:
        	return JSON.parseArray(result, clazz) ;
        	
		  case BaseParserString:
		  //包含[]  并且第一个字符 到 第一个 [ 之间不包含 {
        	if(result.contains("[") && result.contains("]") && !result.substring(0, result.indexOf("[") + 1).contains("{"))
        	{
        		String s = result.substring(result.indexOf("["), result.lastIndexOf("]")+1);
        		return JSON.parseArray(s, clazz) ;
        	}
            break;
	            
        case BaseParserJSONObject:
           break;

        case BaseParserUnknown:
            break;
        }
		return null ;
	}
}
