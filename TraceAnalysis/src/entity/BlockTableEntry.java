package entity;


public class BlockTableEntry {

	private long blockId;
	private long readNum;
	private long writeNum;
	private float readRatio;
	private long IOPS;

	public BlockTableEntry(long blockId) {
		this.blockId = blockId;
		readNum = 0;
		writeNum = 0;
	}
	
	public BlockTableEntry(int blockId, long readNum, long writeNum, float readRatio, long IOPS) {
		this.blockId = blockId;
		this.readNum = readNum;
		this.writeNum = writeNum;
		this.readRatio = readRatio;
		this.IOPS = IOPS;	
	}
	
	public String toString() {
		return "BlockTableEntry: blockId=" + blockId + " readNum=" + readNum + " writeNum=" + writeNum 
				+ " readRatio=" + readRatio + " IOPS=" + IOPS;
	}
	
	public void addReadNum() {
		this.readNum++;
	}
	
	public void addWriteNum() {
		this.writeNum++;
	}
	
	public long getBlockId() {
		return blockId;
	}
	
	public void setBlockId(long blockId) {
		this.blockId = blockId;
	}
	
	public long getReadNum() {
		return readNum;
	}
	
	public void setReadNum(long readNum) {
		this.readNum = readNum;
	}
	
	public long getWriteNum() {
		return writeNum;
	}
	
	public void setWriteNum(long writeNum) {
		this.writeNum = writeNum;
	}
	
	public long getIOPS() {
		return IOPS;
	}
	
	public void setIOPS(long iOPS) {
		IOPS = iOPS;
	}
	
	public float getReadRatio() {
		return readRatio;
	}

	public void setReadRatio(float readRatio) {
		this.readRatio = readRatio;
	}

}