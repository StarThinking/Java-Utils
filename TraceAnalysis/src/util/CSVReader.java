package util;

import handler.BlockTableHandler;
import handler.DiskTableHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import entity.BlockTableEntry;
import entity.DiskTableEntry;
import entity.OpInfo;

public class CSVReader {

	private final File file;
	private final long blockSize;
	private final int BUFFER_SIZE = 100000;
	private List<String> buffer;

	public CSVReader(File file, long blockSize) {
		this.file = file;
		this.blockSize = blockSize;
		this.buffer = new ArrayList<String>(BUFFER_SIZE);
	}
	
	public List<DiskTableEntry> getDiskTable() {
		try {
			List<DiskTableEntry> diskTable = new ArrayList<DiskTableEntry>();
			BufferedReader bufferedreader = new BufferedReader(new FileReader(file)); 
			String lineContent;
			lineContent = bufferedreader.readLine();
			boolean dataBegins = false;
			boolean lastPart = false;
			int counter = 0;
			while(!lastPart) {
				System.out.println("counter " + counter + " : " + counter*BUFFER_SIZE + " to " + (++counter)*BUFFER_SIZE);
				int bufferNum = 0;
				while((lineContent = bufferedreader.readLine()) != null && bufferNum < BUFFER_SIZE) {
					buffer.add(bufferNum, lineContent);
					bufferNum++;
				}
				if(bufferNum != BUFFER_SIZE)
					lastPart = true;
				for(int i=0; i<bufferNum; i++) {
					String line = buffer.get(i);
					if(!dataBegins) {
						if(line.equals("EndHeader")) {
							dataBegins = true;
						}
					} else {  // Construct BlockTables and DiskTables
						StringTokenizer st = new StringTokenizer(line, ",");
						List<String> fields = new ArrayList<String>();
						while(st.hasMoreTokens()) {
							String field = st.nextToken().trim();
							fields.add(field);
						}
						if(fields.size() >= 1 && !fields.get(0).equals("HardFault")) {
							OpInfo opInfo = transfer2OpInfo(fields);
							int diskId = opInfo.getDiskNum();
							long blockId = opInfo.getByteOffset() / blockSize;
							DiskTableEntry diskTableEntry;
							BlockTableEntry blockTableEntry;
							if((diskTableEntry = DiskTableHandler.getEntry(diskTable, diskId)) == null) {
								long startTime = opInfo.getTimeStamp();
								diskTableEntry = new DiskTableEntry(diskId);
								diskTableEntry.setStartTime(startTime);
								List<BlockTableEntry> blockTable = diskTableEntry.getBlockTable();
								blockTableEntry = new BlockTableEntry(blockId);
								blockTable.add(blockTableEntry);				
								diskTable.add(diskTableEntry);
							} else {
								long endTime = opInfo.getTimeStamp();
								diskTableEntry.setEndTime(endTime);
								List<BlockTableEntry> blockTable = diskTableEntry.getBlockTable();
								if((blockTableEntry = BlockTableHandler.getEntry(blockTable, blockId)) == null) {
									blockTableEntry = new BlockTableEntry(blockId);
									blockTable.add(blockTableEntry);
								}
							}	
							if(opInfo.getOperation().equals("DiskRead")) {
								blockTableEntry.addReadNum();
								diskTableEntry.addReadNum();
							} else {
								blockTableEntry.addWriteNum();
								diskTableEntry.addWriteNum();		
							}			
						}				
					}
				}
			}
			bufferedreader.close();
			DiskTableHandler.sort(diskTable);
			return diskTable;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	private OpInfo transfer2OpInfo(List<String> fields) {
		String operation = fields.get(0);
		long timeStamp = Long.valueOf(fields.get(1));
		long byteOffset = Long.valueOf(new BigInteger(fields.get(5).substring(2), 16).toString());
		int ioSize = Integer.valueOf(new BigInteger(fields.get(6).substring(2), 16).toString());
		int elapsedTime = Integer.valueOf(fields.get(7));
		int diskNum = Integer.valueOf(fields.get(8));
		String fileName = fields.get(14);

		OpInfo opInfo = new OpInfo(operation, timeStamp, byteOffset, ioSize,
				elapsedTime, diskNum, fileName);
		return opInfo;
	}

	public static void main(String[] args) {
		
		long blockSize = 1024*1024*64;  // block size is 64MB		
		String dirName = "D:/MSNStorageFileServer/Traces/MSNStorageFileServer-sample.csv";
		
		CSVReader pr = new CSVReader(new File(dirName), blockSize);
		List<DiskTableEntry> diskTable = pr.getDiskTable();
		DiskTableHandler.printDiskTable(diskTable);
		
	}
}
