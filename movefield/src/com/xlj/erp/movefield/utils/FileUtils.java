package com.xlj.erp.movefield.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileUtils {

	public static Object restoreObject(String filePath) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
			Object object = ois.readObject();
			ois.close();
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void saveObject(Object object, String filePath) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
			out.writeObject(object);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteFile(String filePath){
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
	}
}
