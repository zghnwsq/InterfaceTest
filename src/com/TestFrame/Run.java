package com.TestFrame;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Run {
	
	public String initTime;
	public String endTime;
	public String result;

	//运行指定sheet的方法
	public static List<String[]> runTestSuit(String excel, String sheetName) throws SecurityException, IOException{
		SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd_HHmmss"); //文件名时间戳格式
		SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	//log时间戳格式	
		Date suitBegTime = new Date(); //用例集开始时间
		Log log = new Log(ft.format(suitBegTime), "INFO"); //初始化日志
		Keyword k = new Keyword(log); //初始化关键字
		TestSuit ts = new TestSuit(excel, sheetName); //读取用例集
		Param p = new Param(); //全局参数
		List<String[]> toExcel= new ArrayList<String[]>(); //用于写入excel中的信息
		List<String[]> suit = ts.getTestSuit(); //获取用例集信息
		//		 suit.get(0)[3]="";
		List<List<String>> cases = ts.getTestCaseColletion(); //获取用例集的步骤集合
		for(String[] c : suit){
			c[4] = ft2.format(new Date()); //写入用例开始执行时间
			boolean result = true;
			log.write("INFO", "---------------CASE START: "+c[1]+"---------------");
			for(int i = Integer.valueOf(c[2]); i<=Integer.valueOf(c[3]) ;i++ ){
				String action = cases.get(Integer.valueOf(i)).get(2); //action
				//如果p1单元格为空,则填空字符串
				String p1="";
				if(cases.get(Integer.valueOf(i)).size() > 3){
					p1 = cases.get(Integer.valueOf(i)).get(3); //p1
					p1 = p.getParam(p1);
				}
				//如果p2单元格为空,则填空字符串
				String p2 = "";
				if(cases.get(Integer.valueOf(i)).size() > 4){
					p2 = cases.get(Integer.valueOf(i)).get(4); //p2
					p2 = p.getParam(p2);
				}
				//如果p3单元格为空,则填空字符串
				String p3 = "";
				if(cases.get(Integer.valueOf(i)).size() > 5){
					p3 = cases.get(Integer.valueOf(i)).get(5); //p3
					p3 = p.getParam(p3);
				}
				String params[] = {p1, p2, p3};
				boolean r = k.keyword(action, params, p);
				result = r && result;
				//这里可将keyword.res写入excel对应行
				//发送请求才写入响应
				if(action.indexOf("post")!=-1) {
					//判断结果并写入字符串
					if(r) {
						toExcel.add(new String[]{String.valueOf(i), "6", "PASS", Keyword.res});
					}else {
						toExcel.add(new String[]{String.valueOf(i), "6",  "FAIL", Keyword.res});
					}
				}else {
					if(r) {
						toExcel.add(new String[]{String.valueOf(i), "6",  "PASS",""});
					}else {
						toExcel.add(new String[]{String.valueOf(i), "6",  "FAIL",""});
					}
				}
				//如果此步骤执行失败,则跳出当前用例
				if(!r) {
					log.write("SEVERE", "xxxxxxxxxxxx Step FAIL And Stop Running This Case xxxxxxxxxxxx");
					break;
				}
				//System.out.println("this action:"+result);
			}
			c[5] = ft2.format(new Date()); //写入用例结束时间
			if(result){
				c[6] = "PASS";
			}else{
				c[6] = "FAIL";
			}
			log.write("INFO", "---------------CASE END: "+c[1]+" "+c[6]+"---------------");
		}
		Excel.writeExcel(toExcel, excel, sheetName);
		log.write("INFO", "---------------结果写入Excel---------------");
		return suit; 
	}
	
	//运行整个sheet的方法
	public static List<String[]> runTestSuit(String excel) throws SecurityException, IOException{
		List<String[]> suitResult =  new ArrayList<String[]>();  //所有sheet中用例结果汇总,不区分sheet页
		SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd_HHmmss"); //文件名时间戳格式
		SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	//log时间戳格式	
		Date suitBegTime = new Date(); //用例集开始时间
		Log log = new Log(ft.format(suitBegTime), "INFO"); //初始化日志
		Keyword k = new Keyword(log); //初始化关键字
		TestSuit ts = new TestSuit(excel); //读取用例集
		Param p = new Param(); //全局参数
		List<String[]> toExcel; //用于写入excel中的信息
		List<String[]> suit; //单个sheet用例集信息
		List<List<String>> cases; //获取单个sheet用例集的步骤集合
		
		Map<String, List<String[]>> testSuits = ts.getTestSuits();
		for(String s : testSuits.keySet()) {		
			toExcel= new ArrayList<String[]>(); 
			suit = testSuits.get(s);  //拿到当前sheet的suit
			cases = ts.getSheetColletion().get(s);  //拿到当前sheet的steps
			for(String[]c : suit){
				c[4] = ft2.format(new Date()); //写入用例开始执行时间
				boolean result = true;
				log.write("INFO", "v v v v v v v v v v v v v v v CASE START: "+c[1]+" v v v v v v v v v v v v v v v");
				for(int i = Integer.valueOf(c[2]); i<=Integer.valueOf(c[3]) ;i++ ){
					String action = cases.get(Integer.valueOf(i)).get(2); //action
					//如果p1单元格为空,则填空字符串
					String p1="";
					if(cases.get(Integer.valueOf(i)).size() > 3){
						p1 = cases.get(Integer.valueOf(i)).get(3); //p1
						p1 = p.getParam(p1);
					}
					//如果p2单元格为空,则填空字符串
					String p2 = "";
					if(cases.get(Integer.valueOf(i)).size() > 4){
						p2 = cases.get(Integer.valueOf(i)).get(4); //p2
						p2 = p.getParam(p2);
					}
					//如果p3单元格为空,则填空字符串
					String p3 = "";
					if(cases.get(Integer.valueOf(i)).size() > 5){
						p3 = cases.get(Integer.valueOf(i)).get(5); //p3
						p3 = p.getParam(p3);
					}
					String params[] = {p1, p2, p3};
					boolean r = k.keyword(action, params, p);
					result = r && result;
					//这里可将keyword.res写入excel对应行
					//发送请求才写入响应
					if(action.indexOf("post")!=-1) {
						//判断结果并写入字符串
						//toExcel: 0行, 1列, 2结果, 3详细结果
						if(r) {
							toExcel.add(new String[]{String.valueOf(i), "6","PASS",Keyword.res});
						}else {
							toExcel.add(new String[]{String.valueOf(i), "6","FAIL",Keyword.res});
						}
					}else {
						if(r) {
							toExcel.add(new String[]{String.valueOf(i), "6","PASS",""});
						}else {
							toExcel.add(new String[]{String.valueOf(i), "6","FAIL",""});
						}
					}
					//如果此步骤执行失败,则跳出当前用例
					if(!r) {
						log.write("SEVERE", "xxxxxxxxxxxx Step FAIL And Stop Running This Case xxxxxxxxxxxx");
						break;
					}
					//System.out.println("this action:"+result);
				}
				c[5] = ft2.format(new Date()); //写入用例结束时间
				if(result){
					c[6] = "PASS";
				}else{
					c[6] = "FAIL";
				}
				log.write("INFO", "^^^^^^^^^^^^CASE END: "+c[1]+" "+c[6]+"^^^^^^^^^^^^");
			}
			try {
				Excel.writeExcel(toExcel, excel, Integer.valueOf(s));  //把结果写入对应sheet
				log.write("INFO", "---------------结果写入Sheet---------------");
			}catch(Exception e) {
				e.printStackTrace();
				log.write("SEVERE", e.toString());
			}
			suitResult.addAll(suit);
		}
		
		return suitResult; 
	}

	public static List<String[]> runTestSuitWithParam(String excel, String caseSheetName, String dataSheetName) throws SecurityException, IOException{
		SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd_HHmmss"); //文件名时间戳格式
		SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	//log时间戳格式	
		Date suitBegTime = new Date(); //用例集开始时间
		Log log = new Log(ft.format(suitBegTime), "INFO"); //初始化日志
		Keyword k = new Keyword(log); //初始化关键字
		TestSuit ts = new TestSuit(excel, caseSheetName); //读取用例集
		Param p = new Param(); //全局参数
		List<String[]> toExcel= new ArrayList<String[]>(); //用于写入excel中的信息
		List<String[]> suit = ts.getTestSuit(); //获取用例集信息
		List<String[]> suitRes = new ArrayList<String[]>(); //用例结果集
		//		 suit.get(0)[3]="";
		List<List<String>> cases = ts.getTestCaseColletion(); //获取用例集的步骤集合
		
		List<List<String>> data = Excel.readExcel(excel, dataSheetName);
		int sceneCount= data.get(0).size();
		for(int scene = 1 ; scene<sceneCount; scene++) {
			for(List<String> row: data) {
				p.setParam(row.get(0), row.get(scene));
			}
			List<String[]> suitScene = new ArrayList<String[]>(); //容纳单个场景的执行结果
			for(String[] c : suit){
				c[4] = ft2.format(new Date()); //写入用例开始执行时间
				boolean result = true;
				log.write("INFO", "---------------CASE START: "+c[1]+" SCENE:"+scene+"---------------");
				for(int i = Integer.valueOf(c[2]); i<=Integer.valueOf(c[3]) ;i++ ){
					String action = cases.get(Integer.valueOf(i)).get(2); //action
					//如果p1单元格为空,则填空字符串
					String p1="";
					if(cases.get(Integer.valueOf(i)).size() > 3){
						p1 = cases.get(Integer.valueOf(i)).get(3); //p1
						p1 = p.getParam(p1);
					}
					//如果p2单元格为空,则填空字符串
					String p2 = "";
					if(cases.get(Integer.valueOf(i)).size() > 4){
						p2 = cases.get(Integer.valueOf(i)).get(4); //p2
						p2 = p.getParam(p2);
					}
					//如果p3单元格为空,则填空字符串
					String p3 = "";
					if(cases.get(Integer.valueOf(i)).size() > 5){
						p3 = cases.get(Integer.valueOf(i)).get(5); //p3
						p3 = p.getParam(p3);
					}
					String params[] = {p1, p2, p3};
					boolean r = k.keyword(action, params, p);
					result = r && result;
					//这里可将keyword.res写入excel对应行
					//发送请求才写入响应
					if(action.indexOf("post")!=-1) {
						//判断结果并写入字符串
						if(r) {
							toExcel.add(new String[]{String.valueOf(i),  String.valueOf(6+(scene-1)*2), "PASS",Keyword.res});
						}else {
							toExcel.add(new String[]{String.valueOf(i), String.valueOf(6+(scene-1)*2), "FAIL",Keyword.res});
						}
					}else {
						if(r) {
							toExcel.add(new String[]{String.valueOf(i), String.valueOf(6+(scene-1)*2), "PASS",""});
						}else {
							toExcel.add(new String[]{String.valueOf(i), String.valueOf(6+(scene-1)*2), "FAIL",""});
						}
					}
					//如果此步骤执行失败,则跳出当前用例
					if(!r) {
						log.write("SEVERE", "xxxxxxxxxxxx Step FAIL And Stop Running This Case xxxxxxxxxxxx");
						break;
					}
					//System.out.println("this action:"+result);
				}
				c[5] = ft2.format(new Date()); //写入用例结束时间
				if(result){
					c[6] = "PASS";
				}else{
					c[6] = "FAIL";
				}
				log.write("INFO", "---------------CASE END: "+c[1]+" "+c[6]+" SCENE:"+scene+"---------------");
				String[] sc = new String[7]; //新建数组,独立存储场景结果,不影响suit
				sc[0] = new String(c[0]);
				sc[1] = new String(c[1]+"scene"+scene);
				sc[2] = new String(c[2]);
				sc[3] = new String(c[3]);
				sc[4] = new String(c[4]);
				sc[5] = new String(c[5]);
				sc[6] = new String(c[6]);
				suitScene.add(sc);
			}
			suitRes.addAll(suitScene); //添加每个场景的结果
		}	
		Excel.writeExcel(toExcel, excel, caseSheetName);
		log.write("INFO", "---------------结果写入Excel---------------");
		return suitRes; 
	}
	
	
}
