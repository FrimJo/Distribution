package reader;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {

	private String inputFile;
	

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	
	public double[] read(int row, int col, int nr) throws IOException  {
		double[] array = new double[nr];
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over first 10 column and lines
			
			for (int i = 0; i < nr; i++) {
				Cell cell = sheet.getCell(col - 1, row - 1 + i);
				CellType type = cell.getType();
				if (type == CellType.LABEL) {
					// Throw self created exception
					System.out.println("I got a label " + cell.getContents());
				}
				
				if (type == CellType.NUMBER) {
					String s = cell.getContents();
					s = s.replaceAll(",", ".");
					array[i] = Double.parseDouble(s);
					System.out.println("I got a number " + array[i]);
					
				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
		return array;
	}
} 