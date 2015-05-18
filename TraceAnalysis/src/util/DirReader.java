package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DirReader {

	// find files ends up with specific suffix
	public static List<File> findFiles(File dir, String suffix) {
		if(!dir.isDirectory()) {
			return null;
		} else {
			File[] files = dir.listFiles();
			List<File> fileList = array2List(files);
			Iterator<File> it = fileList.iterator();
			while(it.hasNext()) {
				File file = it.next();
				if(!file.getName().endsWith(suffix)) {
					it.remove();
				}
			}
			return fileList;
		}	
	}
	
	public static List<File> findDir(File dir) {
		if(!dir.isDirectory()) {
			return null;
		} else {
			File[] files = dir.listFiles();
			List<File> dirList = array2List(files);
			Iterator<File> it = dirList.iterator();
			while(it.hasNext()) {
				File file = it.next();
				if(!file.isDirectory()) {
					it.remove();
				}
			}
			return dirList;
		}
	}
	
	private static List<File> array2List(File[] files) {
		List<File> fileList = new ArrayList<File>();
		for(File file : files) {
			fileList.add(file);
		}
		return fileList;
	}
	
}
