package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DataStore {

//	private final static String token = ",";
	
	public static boolean createDir(File dir) {	
        if (dir.mkdirs()) {
            System.out.println("Succeed to create dir " + dir);
            return true;
        } else {
            System.out.println("Fail to create dir " + dir);
            return false;
        }
	}
	
	public static boolean storeTable(File file, List<String> table) {
		try {
			if(file.createNewFile()) {
				FileWriter fileWriter = new FileWriter(file);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				for(String line : table) {
					bufferedWriter.write(line);
					bufferedWriter.newLine();
				}
				bufferedWriter.close();
				fileWriter.close();
				System.out.println("Succeed to create file " + file);
				return true;
			}
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}    
	}
	
	public static List<String> restoreTable(File file) {
		List<String> tableStr = new ArrayList<String>();
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferdReader = new BufferedReader(fileReader);
			String line;
			while((line = bufferdReader.readLine()) != null) {
				tableStr.add(line);
			}
			bufferdReader.close();
			fileReader.close();
			System.out.println("Succeed to restore from file " + file);
			return tableStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
