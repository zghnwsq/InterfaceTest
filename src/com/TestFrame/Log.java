package com.TestFrame;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
	
	public Logger log;
	public FileHandler fileHandler;
	
	/***
	 * 
	 * @param logName 日志对象名字
	 * @param level 输出该级别以上的内容
	 * @throws SecurityException 
	 * @throws IOException
	 */
	
	public Log(String logName, String level) throws SecurityException, IOException{
		log = Logger.getLogger(logName);
		//设置日志输出到文档的内容级别，默认INFO
		if(level=="SEVERE"){
			log.setLevel(Level.SEVERE);
		}else if(level.equals("WARNING")){
			log.setLevel(Level.WARNING);
		}else if(level.equals("CONFIG")){
			log.setLevel(Level.CONFIG);
		}else if(level.equals("FINE")){
			log.setLevel(Level.FINE);
		}else{
			log.setLevel(Level.INFO);
		}		
		//写入文件的handler
        fileHandler = new FileHandler("./log/"+logName+".log", true);  
        fileHandler.setFormatter(new SimpleFormatter());
		if(level.equals("SEVERE")){
	        fileHandler.setLevel(Level.SEVERE);
		}else if(level.equals("WARNING")){
	        fileHandler.setLevel(Level.WARNING);
		}else if(level.equals("CONFIG")){
	        fileHandler.setLevel(Level.CONFIG);
		}else if(level.equals("FINE")){
	        fileHandler.setLevel(Level.FINE);
		}else{
	        fileHandler.setLevel(Level.INFO);
		}
        log.addHandler(fileHandler); 
	}
	
	/***
	 * 
	 * @param lev 写入日志信息的级别
	 * @param logMsg 要写入日志的信息
	 */
	
	public void write(String lev, String logMsg){
		if(lev.equals("SEVERE")){
			log.severe(logMsg);
		}else if(lev.equals("WARNING")){
			log.severe(logMsg);
		}else if(lev.equals("CONFIG")){
			log.config(logMsg);
		}else if(lev.equals("FINE")){
			log.fine(logMsg);
		}else{
			log.info(logMsg);
		}
		
	}
	
	
	
	

}
