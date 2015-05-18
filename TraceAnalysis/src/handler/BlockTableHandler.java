package handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import util.DataStore;
import entity.BlockTableEntry;


public class BlockTableHandler {
	
	public static BlockTableEntry getEntry(List<BlockTableEntry> blockTable, long blockId) {
		BlockTableEntry found = null;
		for(BlockTableEntry entry : blockTable) {
			if(entry.getBlockId() == blockId){
				found = entry;
				break;
			}
		}
		return found;
	}

	public static void printBlockTable(List<BlockTableEntry> blockTable) {
		for(BlockTableEntry entry : blockTable) {
			System.out.println(entry);
		}
	}
	
	public static boolean storeTable(File dir, List<BlockTableEntry> blockTable) {
		// store blockTable
		File file = new File(dir + "/BlockTable.csv");
		DataStore.storeTable(file, transfer2StringList(blockTable, ","));
		return true;
	}
	
	public static boolean restoreTable(File dir, List<BlockTableEntry> blockTable) {
		// restore blockTable
		File file = new File(dir + "/BlockTable.csv");
		List<String> blockTableStr = DataStore.restoreTable(file);
		transfer2DiskTable(blockTableStr, blockTable, ",");
		return true;
	}
	
	private static void transfer2DiskTable(List<String> blockTableStr, List<BlockTableEntry> blockTable, String token) {
		for(String line : blockTableStr) {
			StringTokenizer tokenizer = new StringTokenizer(line, token);
			List<String> fields = new ArrayList<String>();
			while(tokenizer.hasMoreTokens()) {
				String field = tokenizer.nextToken();
				fields.add(field);
			}
			BlockTableEntry entry = new BlockTableEntry(Integer.valueOf(fields.get(0)), Long.valueOf(fields.get(1)), 
					Long.valueOf(fields.get(2)), Float.valueOf(fields.get(3)), Long.valueOf(fields.get(4)));
			blockTable.add(entry);
		}
	}
	
	private static List<String> transfer2StringList(List<BlockTableEntry> blockTable, String token) {
		List<String> stringList = new ArrayList<String>();
		for(BlockTableEntry entry : blockTable) {
			String line = entry.getBlockId() + token + entry.getReadNum() + token + entry.getWriteNum() + token 
					+ entry.getReadRatio() + token + entry.getIOPS();
			stringList.add(line);
		}
		return stringList;
	}
	

}
