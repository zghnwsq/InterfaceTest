package com.TestFrame;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		}else if(level=="WARNING"){
			log.setLevel(Level.WARNING);
		}else if(level=="CONFIG"){
			log.setLevel(Level.CONFIG);
		}else if(level=="FINE"){
			log.setLevel(Level.FINE);
		}else{
			log.setLevel(Level.INFO);
		}		
		//写入文件的handler
        fileHandler = new FileHandler("./../../log/"+logName+".log");  
		if(level=="SEVERE"){
	        fileHandler.setLevel(Level.SEVERE);
		}else if(level=="WARNING"){
	        fileHandler.setLevel(Level.WARNING);
		}else if(level=="CONFIG"){
	        fileHandler.setLevel(Level.CONFIG);
		}else if(level=="FINE"){
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
		if(lev == "SEVERE"){
			log.severe(logMsg);
		}else if(lev == "WARNING"){
			log.severe(logMsg);
		}else if(lev == "CONFIG"){
			log.config(logMsg);
		}else if(lev == "FINE"){
			log.fine(logMsg);
		}else{
			log.info(logMsg);
		}
		
	}
	
	
	
	

}
