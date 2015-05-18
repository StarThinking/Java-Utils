package util;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import entity.OpInfo;

import jxl.Sheet;
import jxl.Workbook;

public class ExcelReader {

	private Workbook workbook;
	private final File file;

	public ExcelReader(File file) {
		this.file = file;
	}

	public List<OpInfo> readExcel() {
		try {
			List<OpInfo> opInfoList = new ArrayList<OpInfo>();
			workbook = Workbook.getWorkbook(new FileInputStream(file));
			Sheet[] sheets = workbook.getSheets();
			boolean dataBegin = false;
			for (int i = 0; i < sheets.length; i++) {
				Sheet sheet = sheets[i];
				for (int rowNum = 0; rowNum < sheet.getRows(); rowNum++) {
					List<String> fields = new ArrayList<String>();
					for (int colNum = 0; colNum < sheet.getColumns(); colNum++) {
						String cellString = sheet.getCell(colNum, rowNum)
								.getContents().trim();
						fields.add(cellString);
					}
					if (dataBegin && (fields.get(0).equals("DiskWrite") || fields.get(0).equals("DiskRead"))) {
						OpInfo opInfo = transfer2OpInfo(fields);
						opInfoList.add(opInfo);
					}
					if(fields.get(0).equals("EndHeader")) {
						dataBegin = true;
					}
				}
			}
			workbook.close();
			return opInfoList;
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

}
