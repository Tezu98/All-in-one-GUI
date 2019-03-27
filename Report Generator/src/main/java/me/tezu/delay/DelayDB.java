package me.tezu.delay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import me.tezu.generator.Handler;

public class DelayDB {
	private XSSFWorkbook wb;
	private XSSFSheet sheet;
	private File file;
	private FileInputStream fis;
	private Row row;
	private Cell cell;
	
	private String endOfWeek() {
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 7);
		dt = c.getTime();
		String pattern = "yyyy.MM.dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(dt);
	}
	
	public void test() {
	
		System.out.println("TEST 1");
	}
	
	private String getDay() {
		Date date = new Date();
		String pattern = "dd";
		SimpleDateFormat sDate = new SimpleDateFormat(pattern);
		return sDate.format(date);
	}
	
	private void createDbFile() {
		
		try {
			if(!getdbFile().exists()) {
				FileOutputStream fos = new FileOutputStream(getdbFile());
				wb.createSheet();
				wb.write(fos);
				fos.flush();
				fos.close();
				wb.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private File getdbFile() {
		String today = getDate();
		String endOfWeek = endOfWeek();
		
		return new File("Baza " + today + "-" + endOfWeek +"xlsx");
	}
	
	private String getDate() {
		String pattern = "yyyy.MM.dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(new Date());
	}
	
	private void initFile() {
		try {
			String pattern = "yyyy.MM.dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			String path = "";
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
	
	private void init() {
		try {
			file = new File("new.xlsx");
			fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
			sheet = wb.getSheetAt(0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
