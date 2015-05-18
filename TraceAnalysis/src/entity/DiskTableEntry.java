package entity;

import java.util.ArrayList;
import java.util.List;

public class DiskTableEntry {
	
	private int diskId;
	private long readNum;
	private long writeNum;
	private float readRatio;
	private long startTime;
	private long endTime;
	private long IOPS;
	private List<BlockTableEntry> blockTable;
	
	public DiskTableEntry(int diskId) {
		this.diskId = diskId;
		this.readNum = 0;
		this.writeNum = 0;
		this.readRatio = 0;
		this.startTime = 0;
		this.endTime = 0;
		this.IOPS = 0;	
		this.blockTable = new ArrayList<BlockTableEntry>();
	}

	public DiskTableEntry(int diskId, long readNum, long writeNum, float readRatio, long startTime, long endTime, long IOPS) {
		this.diskId = diskId;
		this.readNum = readNum;
		this.writeNum = writeNum;
		this.readRatio = readRatio;
		this.startTime = startTime;
		this.endTime = endTime;
		this.IOPS = IOPS;	
		this.blockTable = new ArrayList<BlockTableEntry>();
	}
	
	public String toString() {
		long period = (endTime - startTime) / 1000000;
		return "DiskTableEntry: diskId=" + diskId + " readNum=" + readNum + " writeNum=" + writeNum + " readRatio=" + readRatio 
				+ " period=" + period + " IPOS=" + IOPS;
	}
	
	public void addReadNum() {
		this.readNum++;
	}
	
	public void addWriteNum() {
		this.writeNum++;
	}
	
	public int getDiskId() {
		return diskId;
	}

	public void setDiskId(int diskId) {
		this.diskId = diskId;
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

	public float getReadRatio() {
		return readRatio;
	}

	public void setReadRatio(float readRatio) {
		this.readRatio = readRatio;
	}

	public List<BlockTableEntry> getBlockTable() {
		return blockTable;
	}

	public void setBlockTable(List<BlockTableEntry> blockTable) {
		this.blockTable = blockTable;
	}
	
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public long getEndTime() {
		return endTime;
	}
	
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public long getIOPS() {
		return IOPS;
	}

	public void setIOPS(long iOPS) {
		IOPS = iOPS;
	}

}
