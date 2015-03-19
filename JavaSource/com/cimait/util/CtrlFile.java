package com.cimait.util;


import java.io.FileReader;
import java.io.BufferedReader;

public class CtrlFile {

	private String filename = "";

	public CtrlFile(String fileName) {
		filename = fileName;
	}

	public String readCtrl() {
		String result = "S";
		try {
			FileReader filectr = new FileReader(filename);
			BufferedReader reader = new BufferedReader(filectr);
			String aux = reader.readLine();
			if (!aux.equals("S")) {
				result="N";
			}
			reader.close();
		} catch (Exception e) {
			result="N";
		}
		return result;
	}

}
