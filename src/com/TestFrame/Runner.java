package com.TestFrame;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Runner {
	
	private SimpleDateFormat ft; //文件名时间戳格式
	private SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	//log时间戳格式	
	private Date suitBegTime = new Date(); //用例集开始时间
	private Log log; //初始化日志
	private Keyword k = new Keyword(log); //初始化关键字
	private TestSuit ts; //读取用例集
	private Param p; //全局参数
	private List<String[]> toExcel; //用于写入excel中的信息
	private List<String[]> suit; //获取用例集信息
	private List<String[]> suitRes; //用例结果集
	private List<List<String>> cases; //获取用例集的步骤集合
	
	public Runner(TestSuit ts){
		this.ts = ts;
	}
	
	public void init() throws SecurityException, IOException{
		//执行时，初始化变量
		ft = new SimpleDateFormat("yyyy_MM_dd_HHmmss"); //文件名时间戳格式
		ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	//log时间戳格式	
		suitBegTime = new Date(); //用例集开始时间
		log = new Log(ft.format(suitBegTime), "INFO"); //初始化日志
		k = new Keyword(log); //初始化关键字
		p = new Param(); //全局参数
		toExcel= new ArrayList<String[]>(); //用于写入excel中的信息
		suitRes = new ArrayList<String[]>(); //用例结果集
	}
	
	/***
	 * 
	 * @param suit 当前sheet的测试集信息，存储用例的起止行数，起始时间，结果
	 * @param cases 当前sheet的具体测试用例及步骤
	 * @return 返回当前sheet的测试集结果
	 */
	public List<String[]> sheetRunner(List<String[]> suit, List<List<String>> cases){
		List<String[]> st = suit;
		for(String[]c : st){
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
		return st;
	}
	
	/***
	 * 非脚本参数分离的用例执行方法
	 * @return 返回单个sheet或整个sheet的用例的执行结果
	 * @throws IOException
	 */
	public List<String[]> run() throws IOException{
		init();
		if(ts.sheetName == null || ts.sheetName.equals("")){
			//未指定sheet名则执行整个excel的用例
//			List<String[]> suitResult =  new ArrayList<String[]>();
			Map<String, List<String[]>> testSuits = ts.getTestSuits();
			for(String s : testSuits.keySet()) {		
				toExcel= new ArrayList<String[]>(); 
				suit = testSuits.get(s);  //拿到当前sheet的suit
				cases = ts.getSheetColletion().get(s);  //拿到当前sheet的steps
				//这里接单个sheet执行的方法
				suit = sheetRunner(suit, cases);
				//写入excel
				try {
					Excel.writeExcel(toExcel, ts.excel, Integer.valueOf(s));  //把结果写入对应sheet
					log.write("INFO", "---------------结果写入Sheet---------------");
				}catch(Exception e) {
					e.printStackTrace();
					log.write("SEVERE", e.toString());
				}
				suitRes.addAll(suit);
			}			
			return suitRes; 
		}else{
			//执行指定sheet的用例
			suit = ts.getTestSuit(); //获取用例集信息
			cases = ts.getTestCaseColletion(); //获取用例集的步骤集合
			//这里接单个sheet执行方法
			suitRes = sheetRunner(suit, cases);
			//写入excel
			Excel.writeExcel(toExcel, ts.excel, ts.sheetName);
			log.write("INFO", "---------------结果写入Excel---------------");
			return suitRes;
		}
		
	}
	
	/***
	 * 脚本数据源分离的用例执行方法
	 * @param excel  excel数据源路径
	 * @param dataSheetName 数据源的sheet名
	 * @param range 执行数据源范围
	 * @return 返回用例集执行结果
	 * @throws Exception
	 */
	public List<String[]> run(String excel, String dataSheetName, String range) throws Exception{
		init();
		suit = ts.getTestSuit(); //获取用例集信息
		cases = ts.getTestCaseColletion(); //获取用例集的步骤集合
		List<List<String>> data = Excel.readExcel(excel, dataSheetName);
		//增加部分执行数据源的方法
		StringBuffer sceneCollection = new StringBuffer();
		if(range.indexOf("-")!=-1) {
			String[] rg = range.split("-");
			for(int i=Integer.valueOf(rg[0]); i<=Integer.valueOf(rg[1]); i++) {
				sceneCollection.append(String.valueOf(i)+",");
			}
		}else if(range.indexOf(",")!=-1 || range.trim().length()==1) {
			sceneCollection.append(range);
		}else if(range.trim().length()>1 || range.trim().length()<1){
			throw new Exception ("Error data range! Correct format: '2' or '1-3' or '2,3,5' ");
		}
		String[] seneCollection = sceneCollection.toString().split(",");
		int[] seneCol = new int[seneCollection.length];
		for(int i =0; i<seneCol.length; i++) {
			seneCol[i] = Integer.valueOf(seneCollection[i]);
		}
		//根据待执行数据源的范围选取数据
		for(int scene : seneCol) {   //int scene = 1 ; scene<sceneCount; scene++
			//获取数据源
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
				log.write("INFO", "---------------CASE END: "+c[1]+" SCENE:"+scene+" "+c[6]+"---------------");
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
		Excel.writeExcel(toExcel, excel, ts.sheetName);
		log.write("INFO", "---------------结果写入Excel---------------");
		return suitRes; 	
	}
	
	
}
