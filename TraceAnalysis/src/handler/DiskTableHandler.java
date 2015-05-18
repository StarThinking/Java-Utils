package handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import util.DataStore;
import entity.BlockTableEntry;
import entity.DiskTableEntry;



public class DiskTableHandler {
	
	public static DiskTableEntry getEntry(List<DiskTableEntry> diskTable, int diskId) {
		DiskTableEntry found = null;
		for(DiskTableEntry entry : diskTable) {
			if(entry.getDiskId() == diskId){
				found = entry;
				break;
			}
		}
		return found;
	}
	
	public static void printDiskTable(List<DiskTableEntry> diskTable) {
		for(DiskTableEntry entry : diskTable) {
			System.out.println(entry);
		}
	}
	
	public static void sort(List<DiskTableEntry> diskTable) {
		Iterator<DiskTableEntry> diskTableEntryIt = diskTable.iterator();
		while(diskTableEntryIt.hasNext()) {
			DiskTableEntry diskTableEntry = diskTableEntryIt.next();
			List<BlockTableEntry> blockTable = diskTableEntry.getBlockTable();
			// sort blockTable
			Collections.sort(blockTable, new Comparator<BlockTableEntry>() {
				// sorted by 1.readNum 2.writeNum 3.blockId
				@Override
				public int compare(BlockTableEntry entry0, BlockTableEntry entry1) {
					if(entry0.getReadNum() != entry1.getReadNum()) {
						return (int) -(entry0.getReadNum() - entry1.getReadNum());
					} else if(entry0.getWriteNum() != entry1.getWriteNum()) {
						return (int) -(entry0.getWriteNum() - entry1.getWriteNum());
					}
					return (int) (entry0.getBlockId() - entry1.getBlockId());
				}
			});
		}
		// sort diskTable by Id
		Collections.sort(diskTable, new Comparator<DiskTableEntry>() {
			@Override
			public int compare(DiskTableEntry entry0, DiskTableEntry entry1) {
				return (int) (entry0.getDiskId() - entry1.getDiskId());
			}
		});
	}
	
	public static long getMaxEndTime(List<DiskTableEntry> diskTable) {
		long maxEndTime = 0;
		Iterator<DiskTableEntry> diskTableEntryIt = diskTable.iterator();
		while(diskTableEntryIt.hasNext()) {
			DiskTableEntry diskTableEntry = diskTableEntryIt.next();
			long time = diskTableEntry.getEndTime();
			if(maxEndTime < time) {
				maxEndTime = time;
			}
		}
		return maxEndTime;
	}
	
	public static long getMinStartTime(List<DiskTableEntry> diskTable) {
		long minStartTime = Long.MAX_VALUE;
		Iterator<DiskTableEntry> diskTableEntryIt = diskTable.iterator();
		while(diskTableEntryIt.hasNext()) {
			DiskTableEntry diskTableEntry = diskTableEntryIt.next();
			long time = diskTableEntry.getStartTime();
			if(minStartTime > time) {
				minStartTime = time;
			}
		}
		return minStartTime;
	}
	
	public static boolean storeTable(File dir, List<DiskTableEntry> diskTable) {
		// store diskTable
		File file = new File(dir + "/DiskTable.csv");
		DataStore.storeTable(file, transfer2StringList(diskTable, ","));
		
		// create disk directories
		for(DiskTableEntry entry : diskTable) {
			String diskDir = dir + "/Disk" + entry.getDiskId();
			DataStore.createDir(new File(diskDir));
		}
		return true;
	}
	
	public static boolean restoreTable(File dir, List<DiskTableEntry> diskTable) {
		// restore diskTable
		File file = new File(dir + "/DiskTable.csv");
		List<String> diskTableStr = DataStore.restoreTable(file);
		transfer2DiskTable(diskTableStr, diskTable, ",");
		return true; 
	}
	
	private static void transfer2DiskTable(List<String> diskTableStr, List<DiskTableEntry> diskTable, String token) {
		for(String line : diskTableStr) {
			StringTokenizer tokenizer = new StringTokenizer(line, token);
			List<String> fields = new ArrayList<String>();
			while(tokenizer.hasMoreTokens()) {
				String field = tokenizer.nextToken();
				fields.add(field);
			}
			DiskTableEntry entry = new DiskTableEntry(Integer.valueOf(fields.get(0)), Long.valueOf(fields.get(1)), 
					Long.valueOf(fields.get(2)), Float.valueOf(fields.get(3)), Long.valueOf(fields.get(4)), 
					Long.valueOf(fields.get(5)), Long.valueOf(fields.get(6)));
			diskTable.add(entry);
		}
	}
	
	private static List<String> transfer2StringList(List<DiskTableEntry> diskTable, String token) {
		List<String> stringList = new ArrayList<String>();
		for(DiskTableEntry entry : diskTable) {
			String line = entry.getDiskId() + token + entry.getReadNum() + token + entry.getWriteNum() + token 
					+ entry.getReadRatio() + token + entry.getStartTime() + token + entry.getEndTime() + token + entry.getIOPS();
			stringList.add(line);
		}
		return stringList;
	}
}
