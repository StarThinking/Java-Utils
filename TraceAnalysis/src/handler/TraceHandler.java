package handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.SimpleModel;
import util.CSVReader;
import util.DataStore;
import util.DirReader;
import entity.BlockTableEntry;
import entity.DiskTableEntry;
import entity.PeriodTableEntry;

public class TraceHandler {

	private long blockSize;
	private String suffix;
	private String dirName;	
	private String dataStore = "/DataStore";
	private List<PeriodTableEntry> periodTable;
	
	public TraceHandler(long blockSize, String suffix, String dirName) {
		this.blockSize = blockSize;
		this.suffix = suffix;
		this.dirName = dirName;
		this.periodTable = new ArrayList<PeriodTableEntry>();
	}
	
	public void analyze() {
		long blockSize = 1;
		while(blockSize <= 5) {
			System.out.println("*****************" + blockSize + "*****************");
			SimpleModel.getIOPSAbsorbRate(periodTable, blockSize);
			blockSize++;
		}
	}
	
	public void constructTables() {
		List<File> fileList = DirReader.findFiles(new File(dirName), suffix);
		for(int fileId = 0; fileId<fileList.size(); fileId++) {
			File file = fileList.get(fileId);
			System.out.println("constructing PeriodTablefile for file " + file.getName());
			CSVReader reader = new CSVReader(file, blockSize);
			List<DiskTableEntry> diskTable = reader.getDiskTable();
			long startTime = DiskTableHandler.getMinStartTime(diskTable);
			long endTime = DiskTableHandler.getMaxEndTime(diskTable);
			PeriodTableEntry periodTableEntry = new PeriodTableEntry(fileId, startTime, endTime);
			periodTableEntry.setDiskTable(diskTable);
			periodTable.add(periodTableEntry);
			
		}		
		statistic();	
		storeTables();
	}
	
	public void restoreTables() {
		System.out.println("doing restoreTables");
		String dataStoreStr = dirName + dataStore;
		File dataStoreDir = new File(dataStoreStr);
		PeriodTableHandler.restoreTable(dataStoreDir, periodTable);
		for(PeriodTableEntry periodEntry : periodTable) {
			List<DiskTableEntry> diskTable = periodEntry.getDiskTable();
			String periodDirStr = dataStoreStr + "/Period" + periodEntry.getPeriodId();
			File periodDir = new File(periodDirStr);
			DiskTableHandler.restoreTable(periodDir, diskTable);
			for(DiskTableEntry diskTableEntry : diskTable) {
				List<BlockTableEntry> blockTable = diskTableEntry.getBlockTable();
				String diskDirStr = periodDir + "/Disk" + diskTableEntry.getDiskId();
				File diskdDir = new File(diskDirStr);
				BlockTableHandler.restoreTable(diskdDir, blockTable);
			}
		}
	}
	
	public void printAllBlockTable() {
		for(PeriodTableEntry periodTableEntry : periodTable) {
			System.out.println("----------------------------period " + periodTableEntry.getPeriodId() + "----------------------------");
			List<DiskTableEntry> diskTable = periodTableEntry.getDiskTable();
			for(DiskTableEntry diskTableEntry : diskTable) {
				System.out.println("-----------------------disk " + diskTableEntry.getDiskId() + "-----------------------");
				List<BlockTableEntry> blockTable = diskTableEntry.getBlockTable();
				BlockTableHandler.printBlockTable(blockTable);
				System.out.println();
			}
			System.out.println();
		}
	}

	public void printAllDiskTable() {
		for(PeriodTableEntry periodTableEntry : periodTable) {
			System.out.println("-----------------------period " + periodTableEntry.getPeriodId() + "-----------------------");
			List<DiskTableEntry> diskTable = periodTableEntry.getDiskTable();
			DiskTableHandler.printDiskTable(diskTable);
			System.out.println();
		}
	}
	
	public void printAllPeriodTable() {
		PeriodTableHandler.printPeriodTable(periodTable);
	}
	
	private void statistic() {
		System.out.println("doing statistic");
		for(PeriodTableEntry periodTableEntry : periodTable) {
			long readNum = 0;
			long writeNum = 0;
			List<DiskTableEntry> diskTable = periodTableEntry.getDiskTable();
			for(DiskTableEntry diskTableEntry : diskTable) {
				readNum += diskTableEntry.getReadNum();
				writeNum += diskTableEntry.getWriteNum();
			}
			periodTableEntry.setReadNum(readNum);
			periodTableEntry.setWriteNum(writeNum);
			float periodReadRatio = (float)readNum / ((float)readNum +(float)writeNum);
			periodTableEntry.setReadRatio(periodReadRatio);
			long periodIOPS = (readNum + writeNum ) / ((periodTableEntry.getEndTime()-periodTableEntry.getStartTime()) / 1000000);
			periodTableEntry.setIOPS(periodIOPS);
			
			// statistic for each DiskTable
			for(DiskTableEntry diskTableEntry : diskTable) {
				float diskReadRatio = (float)diskTableEntry.getReadNum() / ((float)diskTableEntry.getReadNum() +(float)diskTableEntry.getWriteNum());
				diskTableEntry.setReadRatio(diskReadRatio);
				long diskIOPS = (diskTableEntry.getReadNum() + diskTableEntry.getWriteNum()) / ((diskTableEntry.getEndTime()-diskTableEntry.getStartTime()) / 1000000);
				diskTableEntry.setIOPS(diskIOPS);
				
				// statistic for each BlockTable, using the period of disk
				List<BlockTableEntry> blockTable = diskTableEntry.getBlockTable();
				for(BlockTableEntry blockTableEntry : blockTable) {
					float blockReadRatio = (float)blockTableEntry.getReadNum() / ((float)blockTableEntry.getReadNum() +(float)blockTableEntry.getWriteNum());
					blockTableEntry.setReadRatio(blockReadRatio);
					long blockIOPS = (blockTableEntry.getReadNum() + blockTableEntry.getWriteNum()) / ((diskTableEntry.getEndTime()-diskTableEntry.getStartTime()) / 1000000);
					blockTableEntry.setIOPS(blockIOPS);
				}
			}
		}
	}
	
	private void storeTables() {
		System.out.println("doing storeTables");
		String dataStoreStr = dirName + dataStore;
		// create directory DataStore
		File dataStoreDir = new File(dataStoreStr);
		DataStore.createDir(dataStoreDir);
		PeriodTableHandler.storeTable(dataStoreDir, periodTable);
		for(PeriodTableEntry periodEntry : periodTable) {
			List<DiskTableEntry> diskTable = periodEntry.getDiskTable();
			String periodDirStr = dataStoreStr + "/Period" + periodEntry.getPeriodId();
			File periodDir = new File(periodDirStr);
			DiskTableHandler.storeTable(periodDir, diskTable);
			for(DiskTableEntry diskTableEntry : diskTable) {
				List<BlockTableEntry> blockTable = diskTableEntry.getBlockTable();
				String diskDirStr = periodDir + "/Disk" + diskTableEntry.getDiskId();
				File diskdDir = new File(diskDirStr);
				BlockTableHandler.storeTable(diskdDir, blockTable);
			}
		}
	}
}
