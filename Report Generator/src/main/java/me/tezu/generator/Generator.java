package me.tezu.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Generator {

	// VARIABLES

	private File oldFile, newFile;
	private FileInputStream fis;

	private XSSFWorkbook oldWb;
	private XSSFSheet oldSheet;

	private XSSFWorkbook newWb;
	private XSSFSheet newSheet;
	
	private HashMap<String, Boolean> missing;
	private Handler handler;
	public Generator() {
		init();
	}

	public void start() {
		//Insert sheet you want to compare to other sheet
		handler = new Handler();
		System.setOut(handler.getDisplay().getCon());
		System.out.println("TEST");
		checkIfExist(newSheet, oldSheet);
		removeOldRows(oldSheet);
		createFile(oldSheet, oldWb);
	}
	
	/**
	 * Method to organize things that will be initialized one after another
	 */
	
	private void init() {
		oldSheetInit();
		newSheetInit();
		missing = new HashMap<String, Boolean>();
	}
	
	private void checkIfExist(Sheet newSheet, Sheet oldSheet) {
		Row oldRow, newRow;
		Cell oldCell, newCell;
		
		for (int r = 1; r < newSheet.getPhysicalNumberOfRows(); r++) {
			newRow = newSheet.getRow(r);
			newCell = newRow.getCell(0);
			for (int or = 1; or < oldSheet.getPhysicalNumberOfRows(); or++) {
				oldRow = oldSheet.getRow(or);
				oldCell = oldRow.getCell(0);
				missing.put(newCell.getStringCellValue(), true);
				if (newCell.getStringCellValue().equals(oldCell.getStringCellValue())) {
					if (missing.get(newCell.getStringCellValue()) == Boolean.TRUE) {
						missing.remove(newCell.getStringCellValue());
						missing.put(newCell.getStringCellValue(), false);
					}
					break;
				} else {
					if (missing.get(newCell.getStringCellValue()) == Boolean.FALSE) {
						missing.remove(newCell.getStringCellValue());
						missing.put(newCell.getStringCellValue(), true);
					}
				}
			}
		}
	}
	
	private void removeOldRows(Sheet sheet) {
		Row row;
		Cell cell;
		for (int or = 1; or <= sheet.getLastRowNum(); or++) {
			row = sheet.getRow(or);
			cell = row.getCell(0);
			if (!missing.containsKey(cell.getStringCellValue())) {
				sheet.removeRow(row);
			}

		}
	}
	
	private void addNewMissingRows(Sheet newSheet, Sheet oldSheet) {
		
	}
	
	private void createFile(Sheet sheet, XSSFWorkbook wb) {
		try {
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			File file = new File("Raport " + date + ".xlsx");
			FileOutputStream fos = new FileOutputStream(file);
			sheet = wb.getSheetAt(0);
			wb.write(fos);
			fos.flush();
			fos.close();
			wb.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public File getOldFile() {
		return oldFile;
	}

	public void setOldFile(File oldFile) {
		this.oldFile = oldFile;
	}

	public File getNewFile() {
		return newFile;
	}

	public void setNewFile(File newFile) {
		this.newFile = newFile;
	}

	/**
	 * Initialize variables for files as well as sets the sheets
	 * at 0 (first sheet available in workbook) next those methods are called in
	 * init();
	 */
	
	private void oldSheetInit() {
		try {
			oldFile = new File("old.xlsx");
			fis = new FileInputStream(oldFile);
			oldWb = new XSSFWorkbook(fis);
			oldSheet = oldWb.getSheetAt(0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void newSheetInit() {
		try {
			newFile = new File("new.xlsx");
			fis = new FileInputStream(newFile);
			newWb = new XSSFWorkbook(fis);
			newSheet = newWb.getSheetAt(0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
