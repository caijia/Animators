package com.cs.animators.parser;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONTokener;
import com.alibaba.fastjson.JSON;
import com.cs.animators.entity.HotItem;
import com.cs.cj.http.parserinterface.BaseParser;

public class NewUpdateParser extends BaseParser<List<List<HotItem>>> {

	@Override
	public List<List<HotItem>> parse(String result) throws Exception {
		List<List<HotItem>> list = new ArrayList<List<HotItem>>();
		Object obj = new JSONTokener(result).nextValue();
		if(obj instanceof JSONArray)
		{
			com.alibaba.fastjson.JSONArray array = com.alibaba.fastjson.JSONArray.parseArray(result);
			for (int i = 0; i < array.size(); i++) {
				String s = array.get(i).toString();
				Object obj1 = new JSONTokener(s).nextValue();
				if(obj1 instanceof JSONArray)
				{
					List<HotItem> listItem = JSON.parseArray(s, HotItem.class);
					list.add(listItem);
				}
			}
		}
		return list;
	}

}
