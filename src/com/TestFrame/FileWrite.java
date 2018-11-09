package com.TestFrame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWrite {
	
	
	
	public static void write(String filename, String content) {
		try {	
			File file = new File(filename);
			if(!file.exists()) {	
				file.createNewFile();				
			}
			FileWriter fw = new FileWriter(file, false); //覆盖模式
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			fw.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
