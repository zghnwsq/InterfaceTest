package com.TestFrame;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;

public class ReadFile {
	
	//public static String encoding = "UTF-8";
	
	public static String readToString(String filename, String encoding) {
		File file = new File(filename);
		Long fileLenght = file.length();
		byte[] fileContent = new byte[fileLenght.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(fileContent);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			return new String(fileContent, encoding);
		} catch (UnsupportedEncodingException e) {
			System.out.println("not support encoding :"+ encoding);
			e.printStackTrace();
			return null;
		}
	}

}
