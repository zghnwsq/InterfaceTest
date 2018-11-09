package com.test;

import com.TestFrame.ReadFile;

public class testReadFile {

	public static void main(String[] args) {
		String s = ReadFile.readToString("./html/model.txt", "UTF-8");
		System.out.println(s);

	}

}
