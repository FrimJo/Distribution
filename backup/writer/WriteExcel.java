package writer;

import java.io.File;
import java.io.IOException;
//import java.util.Locale;

//import jxl.CellView;
//import jxl.Sheet;
import jxl.Workbook;
//import jxl.WorkbookSettings;
//import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
//import jxl.write.Formula;
//import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class WriteExcel {

  private WritableCellFormat timesBoldUnderline;
  private WritableCellFormat times;
  private String inputFile;
  
  public void setOutputFile(String inputFile) {
	  this.inputFile = inputFile;
  }

  public void write(double[] array, int row, int col) throws IOException, WriteException {
	// Lets create a times font
    WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
    // Define the cell format
    times = new WritableCellFormat(times10pt);
    // Lets automatically wrap the cells
    times.setWrap(true);
    File inputWorkbook = new File("org.xls");
    Workbook w;
	try {
		w = Workbook.getWorkbook(inputWorkbook);
		File outputWorkbook = new File("edited.xls");
	    WritableWorkbook workbook = Workbook.createWorkbook(outputWorkbook,w);
	    WritableSheet excelSheet = workbook.getSheet(0);
	    //createLabel(excelSheet);
	    createContent(excelSheet, array, row, col);

	    workbook.write();
	    workbook.close();
	} catch (BiffException e) {
		e.printStackTrace();
	}
  }

  private void createContent(WritableSheet sheet, double[] array, int row, int col) throws WriteException, RowsExceededException{
	  for(int i = 0; i < array.length; i++){
		  addNumber(sheet, col, row+i, new Double(array[i]));
	  }
  }

  private void addNumber(WritableSheet sheet, int column, int row,
      Double integer) throws WriteException, RowsExceededException {
    Number number;
    number = new Number(column, row, integer, times);
    sheet.addCell(number);
  }
} 