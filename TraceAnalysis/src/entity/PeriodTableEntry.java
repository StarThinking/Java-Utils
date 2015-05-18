package entity;

import java.util.ArrayList;
import java.util.List;

public class PeriodTableEntry {

	private int periodId;
	private long readNum;
	private long writeNum;
	private float readRatio;
	private long startTime;
	private long endTime;
	private long IOPS;
	private List<DiskTableEntry> diskTable;
	
	public PeriodTableEntry(int periodId, long startTime, long endTime) {
		this.periodId = periodId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.readNum = 0;
		this.writeNum = 0;
		this.readRatio = 0;
		this.IOPS = 0;	
		this.diskTable = new ArrayList<DiskTableEntry>();
	}
	
	public PeriodTableEntry(int periodId, long readNum, long writeNum, float readRatio, long startTime, long endTime, long IOPS) {
		this.periodId = periodId;
		this.readNum = readNum;
		this.writeNum = writeNum;
		this.readRatio = readRatio;
		this.startTime = startTime;
		this.endTime = endTime;
		this.IOPS = IOPS;	
		this.diskTable = new ArrayList<DiskTableEntry>();
	}
	
	public String toString() {
		long period = (endTime - startTime) / 1000000;
		return "PeriodTableEntry: periodId=" + periodId + " readNum=" + readNum + " writeNum=" + writeNum + " readRatio=" + readRatio 
				 + " period=" + period + " IPOS=" + IOPS;
	}
	
	public int getPeriodId() {
		return periodId;
	}

	public void setPeriodId(int periodId) {
		this.periodId = periodId;
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

	public List<DiskTableEntry> getDiskTable() {
		return diskTable;
	}

	public void setDiskTable(List<DiskTableEntry> diskTable) {
		this.diskTable = diskTable;
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


}
