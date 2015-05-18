package model;

import java.util.List;
import entity.BlockTableEntry;
import entity.DiskTableEntry;
import entity.PeriodTableEntry;

public class SimpleModel {

	public static void getIOPSAbsorbRate(List<PeriodTableEntry> periodTable, long blockSize) {
		for(PeriodTableEntry periodTableEntry : periodTable) {
			List<DiskTableEntry> diskTable = periodTableEntry.getDiskTable();
			for(DiskTableEntry diskTableEntry : diskTable) {
				long sumIOPS = diskTableEntry.getIOPS();
				long accumIOPS = 0;
				List<BlockTableEntry> blockTable = diskTableEntry.getBlockTable();
				for(int i=0; i<blockSize; i++) {
					long IOPS = blockTable.get(i).getIOPS();
					accumIOPS += IOPS;
				}
				float absorbRate = (float)accumIOPS / (float)sumIOPS;
				System.out.println("period" + periodTableEntry.getPeriodId() + ", disk" + diskTableEntry.getDiskId()
						+ ", absorbRate = " + absorbRate);
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
