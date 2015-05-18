package entity;


public class OpInfo {

	private String operation;
	private long timeStamp;
	private long byteOffset;
	private int ioSize;
	private int elapsedTime;
	private int diskNum;
	private String fileName;

	public OpInfo(String operation, long timeStamp, long byteOffset,
			int ioSize, int elapsedTime, int diskNum, String fileName) {
		this.operation = operation;
		this.timeStamp = timeStamp;
		this.byteOffset = byteOffset;
		this.ioSize = ioSize;
		this.elapsedTime = elapsedTime;
		this.diskNum = diskNum;
		this.fileName = fileName;
	}

	/*
	 DiskRead "DiskWrite"  0
	 TimeStamp "2664955" 1
	 Process Name ( PID) "p1 ( 980)" 2
	 ThreadID "6852" 3
	 IrpPtr "0xfffffade6b0a2010" 4
	 ByteOffset "0x6c80556e00" 5	 
	 IOSize "0x00010000" 6 
	 ElapsedTime "183" 7
	 DiskNum "3" 8
	 IrpFlags "0x000043"	9
	 DiskSvcTime "183" 10
	 I/O Pri "0" 11
	 VolSnap "Unknown" 12      
	 FileObject "0xfffffade72165e40" 13
	 FileName "Disk5:\s71\s72\s123" 14
	 */

	public String toString() {
		return "OpInfo: operation=" + operation + " timeStamp=" + timeStamp
				+ " byteOffset=" + byteOffset + " ioSize=" + ioSize
				+ " elapsedTime=" + elapsedTime + " diskNum=" + diskNum
				+ " fileName=" + fileName;
	}

	
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public long getByteOffset() {
		return byteOffset;
	}

	public void setByteOffset(long byteOffset) {
		this.byteOffset = byteOffset;
	}

	public int getIoSize() {
		return ioSize;
	}

	public void setIoSize(int ioSize) {
		this.ioSize = ioSize;
	}

	public int getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public int getDiskNum() {
		return diskNum;
	}

	public void setDiskNum(int diskNum) {
		this.diskNum = diskNum;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
}
