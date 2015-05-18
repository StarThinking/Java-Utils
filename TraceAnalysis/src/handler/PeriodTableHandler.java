package handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import util.DataStore;
import entity.PeriodTableEntry;


public class PeriodTableHandler {
	
	public static void printPeriodTable(List<PeriodTableEntry> periodTable) {
		for(PeriodTableEntry entry : periodTable) {
			System.out.println(entry);
		}
	}

	public static boolean storeTable(File dir, List<PeriodTableEntry> periodTable) {
		// store periodTable
		File file = new File(dir + "/PeriodTable.csv");
		DataStore.storeTable(file, transfer2StringList(periodTable, ","));
		
		// create period directories
		for(PeriodTableEntry entry : periodTable) {
			String periodDir = dir + "/Period" + entry.getPeriodId();
			DataStore.createDir(new File(periodDir));
		}
		return true;
	}
	
	public static boolean restoreTable(File dir, List<PeriodTableEntry> periodTable) {
		// restore periodTable
		File file = new File(dir + "/PeriodTable.csv");
		List<String> periodTableStr = DataStore.restoreTable(file);
		transfer2PeriodTable(periodTableStr, periodTable, ",");
		return true;
	}
	
	private static void transfer2PeriodTable(List<String> periodTableStr, List<PeriodTableEntry> periodTable, String token) {
		for(String line : periodTableStr) {
			StringTokenizer tokenizer = new StringTokenizer(line, token);
			List<String> fields = new ArrayList<String>();
			while(tokenizer.hasMoreTokens()) {
				String field = tokenizer.nextToken();
				fields.add(field);
			}
			PeriodTableEntry entry = new PeriodTableEntry(Integer.valueOf(fields.get(0)), Long.valueOf(fields.get(1)), 
					Long.valueOf(fields.get(2)), Float.valueOf(fields.get(3)), Long.valueOf(fields.get(4)), 
					Long.valueOf(fields.get(5)), Long.valueOf(fields.get(6)));
			periodTable.add(entry);
		}
	}
	
	private static List<String> transfer2StringList(List<PeriodTableEntry> periodTable, String token) {
		List<String> stringList = new ArrayList<String>();
		for(PeriodTableEntry entry : periodTable) {
			String line = entry.getPeriodId() + token + entry.getReadNum() + token + entry.getWriteNum() + token 
					+ entry.getReadRatio() + token + entry.getStartTime() + token + entry.getEndTime() + token + entry.getIOPS();
			stringList.add(line);
		}
		return stringList;
	}
	
}
