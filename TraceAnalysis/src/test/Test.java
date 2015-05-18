package test;

import handler.TraceHandler;

public class Test {

	public static void main(String[] args) {
		
		long blockSize = 1024*1024*64;  // block size is 64MB		
		String suffix = "csv";
		//String dirName = "D:/MSNStorageFileServer/Traces";
		String dirName = "D:/MSNStorageFileServer/Sample";
		
		TraceHandler traceHandler = new TraceHandler(blockSize, suffix, dirName);
		
		//traceHandler.constructTables();

		traceHandler.restoreTables();
		
		//traceHandler.printAllBlockTable();
		traceHandler.printAllDiskTable();
		traceHandler.printAllPeriodTable();
		
		traceHandler.analyze();

	}
}
 