package com.TestFrame;

import java.util.HashMap;
import java.util.Map;
/***
 * 
 * @author ted
 *
 */
public class Param {

	public Map<String, String> paramMap; //一个用例集中的参数集合
	
	public Param() {
		paramMap = new HashMap<String, String>();
	}
	
	/***
	 * 
	 * @param key 存入参数的名
	 * @param value 存入参数的值
	 */
	public void setParam(String key, String value) {
		paramMap.put(key, value);
	}
	
	/***
	 * 
	 * @param key 根据参数名，删除参数集合中某个参数
	 */
	public void delParam(String key) {
		paramMap.remove(key);
	}
	
	/***
	 * 
	 * @param param 输入参数，如为调用则在参数集合中搜索参数吗并返回值，如不是则返回输入值
	 * @return
	 */
	public String getParam(String param) {
		String tmp="";
		if(param.indexOf("${")!=-1) {
			if(paramMap.containsKey(param.substring(param.indexOf("${")+2, param.indexOf("}")).trim())){
				tmp = paramMap.get(param.substring(param.indexOf("${")+2, param.indexOf("}")).trim()); //从参数map中查出来后去空格
			}else {
				tmp = "";
				System.out.println("no param in dictionary like:"+param);
			}
			return tmp;
		}else {
			tmp = param.trim(); //传入的也去空格
			return tmp;
		}
	}

}
