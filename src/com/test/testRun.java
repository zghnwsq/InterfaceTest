package com.test;

public class testRun {

	public static void main(String[] args) {
		String range = "1-2";
		StringBuffer sceneCollection = new StringBuffer();
		if(range.indexOf("-")!=-1) {
			String[] rg = range.split("-");
			for(int i=Integer.valueOf(rg[0]); i<=Integer.valueOf(rg[1]); i++) {
				sceneCollection.append(String.valueOf(i)+",");
			}
		}else if(range.indexOf(",")!=-1) {
			sceneCollection.append(range);
		}
		String[] seneCollection = sceneCollection.toString().split(",");
		int[] seneCol = new int[seneCollection.length];
		for(int i =0; i<seneCol.length; i++) {
			seneCol[i] = Integer.valueOf(seneCollection[i]);
			System.out.println(seneCol[i]);
		}
		

	}

}
