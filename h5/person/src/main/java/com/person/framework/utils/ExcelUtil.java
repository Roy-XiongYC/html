package com.person.framework.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.person.web.admin.model.SchoolScore;

/**
 * 文件帮助类
 * 
 * @author kaifa7
 *
 */
public class ExcelUtil {

	protected final static Logger log = LoggerFactory
			.getLogger(ExcelUtil.class);

	/**
	 * 导出excel功能 返回excel内存处理对像
	 * 
	 * @param title
	 * @param fileName
	 * @param content
	 *            要用有序的
	 * @param columns
	 *            要用有序的map接口
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean exportExcel(String title, String fileName,
			List<Map<String, Object>> content, Map<String, String> columns,
			HttpServletRequest req, HttpServletResponse resp) {

		OutputStream out = null;
		try {
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/x-download");

			resp.addHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(fileName, "UTF-8")); // iso8859-1
			HSSFWorkbook wb = exportExcel(title, fileName, content, columns);
			out = resp.getOutputStream();
			wb.write(out);
			out.close();
			return true;
		} catch (Exception e1) {
			log.error("export excel fault.", e1);
		} finally {

			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (Exception e) {

				}
			}

		}

		return false;
	}

	/**
	 * 返回Excel临时文件路径
	 * 
	 * @param title
	 * @param fileName
	 * @param content
	 * @param columns
	 * @param req
	 * @param resp
	 * @param outpuFilePath
	 * @return
	 */
	public static boolean exportExcel(String title, String fileName,
			List<Map<String, Object>> content, Map<String, String> columns,
			String outpuFilePath) {

		OutputStream out = null;
		try {

			HSSFWorkbook wb = exportExcel(title, fileName, content, columns);
			out = new FileOutputStream(new File(outpuFilePath));// excel的保存路径
			wb.write(out);

			return true;
		} catch (Exception e) {
			log.error("export excel fault.", e);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (Exception e) {

				}
			}
		}
		return false;
	}

	private static HSSFWorkbook exportExcel(String title, String fileName,
			List<Map<String, Object>> content, Map<String, String> columns) {
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = wb.createSheet(title);

		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 16);

		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		HSSFCell cell = row.createCell(0);
		sheet.setColumnWidth(0, 50 * 80);
		sheet.setColumnWidth(1, 50 * 80);
		sheet.setColumnWidth(2, 50 * 80);
		sheet.setColumnWidth(3, 50 * 60);
		sheet.setColumnWidth(4, 50 * 80);
		sheet.setColumnWidth(5, 50 * 60);
		sheet.setColumnWidth(6, 50 * 80);
		sheet.setColumnWidth(7, 50 * 80);
		sheet.setColumnWidth(8, 50 * 80);
		sheet.setColumnWidth(9, 50 * 80);
		sheet.setColumnWidth(10, 50 * 80);
		cell.setCellValue(title);// 设置了最上面的标题

		HSSFRow row1 = sheet.createRow(1);// 开始设置第一行的列名
		int i = 0;
		for (String cell1 : columns.keySet()) {
			row1.createCell(i).setCellValue(columns.get(cell1));
			i++;
		}
		i = 0;
		// 写内容
		for (int j = 0; j < content.size(); j++) {
			Map<String, Object> currRow = content.get(j);
			HSSFRow row2 = sheet.createRow(2 + j);
			for (String cell1 : currRow.keySet()) {
				int beginIndex = cell1.indexOf("(");
				int endIndex = cell1.indexOf(")");
				if (beginIndex != -1
						&& cell1.substring(beginIndex + 1, endIndex).equals(
								"date")) {
					Date date = (Date) currRow.get(cell1);
					DateUtil.formatDate(null);
					row2.createCell(i).setCellValue(DateUtil.formatDate(date));
				} else {
					if (currRow.get(cell1) != null) {
						row2.createCell(i).setCellValue(
								currRow.get(cell1).toString());
					} else {
						row2.createCell(i).setCellValue("");
					}

				}
				i++;
			}
			i = 0;
		}
		return wb;
	}

	/**
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static Map<String, SchoolScore> readSchoolExcel(String filePath,
			String[] fileds) throws IOException {
		File file = new File(filePath);
		if (file == null || !file.exists() || file.getName() == null) {
			return null;
		}
		InputStream is = new FileInputStream(file);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
		XSSFRow xssfRow = xssfSheet.getRow(1);
		XSSFCell cell = xssfRow.getCell(0);
		String studentClass = cell.getStringCellValue();
		
		Map<String, SchoolScore> map = new HashMap<String, SchoolScore>();
		int lastCellNum = 0;
		SchoolScore score = null;
		for (int sheetNum = 0; sheetNum <= 8; sheetNum++) {
			xssfSheet = xssfWorkbook.getSheetAt(sheetNum+4);
			for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					if(rowNum == 0){
						lastCellNum = xssfRow.getLastCellNum() - 1;
						continue;
					}
					cell = xssfRow.getCell(0);
					if(StringUtil.isNullOrBlank(cell.getStringCellValue())) continue;
					if(map.containsKey(cell.getStringCellValue())) {
						score = map.get(cell.getStringCellValue());
					}else{
						score = new SchoolScore();
						score.setStudentId(cell.getStringCellValue());
						score.setStudentClass(studentClass);
						cell = xssfRow.getCell(1);
						score.setStudentName(cell.getStringCellValue());
					}
					
					cell = xssfRow.getCell(lastCellNum);
					String methodName = "set" + fileds[sheetNum];
					try {
						MethodUtils.invokeMethod(score, methodName,cell.getNumericCellValue());
					} catch (Exception e) {
						log.error("export excel fault.", e);
						return null;
					}
					map.put(score.getStudentId(), score);
				}
			}
		}
		return map;
	}
	
	
	
	/**
	 * 2018-06-01 新版
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static Map<String, SchoolScore> readSchoolExcel1(String filePath,
			String[] fileds) throws IOException {
		File file = new File(filePath);
		if (file == null || !file.exists() || file.getName() == null) {
			return null;
		}
		InputStream is = new FileInputStream(file);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
		XSSFRow xssfRow = xssfSheet.getRow(1);
		XSSFCell cell = xssfRow.getCell(0);
		String studentClass = cell.getStringCellValue();
		
		Map<String, SchoolScore> map = new HashMap<String, SchoolScore>();
		int lastCellNum = 0;
		SchoolScore score = null;
		int[] sheetList = {4,5,8,11,12};
		String[] sheetTitle = {"SignValue","VideoValue","VoteValue","TestValue","TaskValue"};
		for (int sheetNum = 0; sheetNum < sheetList.length; sheetNum++) {
			xssfSheet = xssfWorkbook.getSheetAt(sheetList[sheetNum]);
			for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					if(rowNum == 0){
						lastCellNum = xssfRow.getLastCellNum() - 1;
						continue;
					}
					cell = xssfRow.getCell(0);
					if(cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals("")) 
						continue;
					if(map.containsKey(cell.getStringCellValue())) {
						score = map.get(cell.getStringCellValue());
					}else{
						score = new SchoolScore();
						score.setStudentId(cell.getStringCellValue());
						score.setStudentClass(studentClass);
						cell = xssfRow.getCell(1);
						score.setStudentName(cell.getStringCellValue());
					}
					
					cell = xssfRow.getCell(lastCellNum);
					String methodName = "set" + sheetTitle[sheetNum];
					try {
						MethodUtils.invokeMethod(score, methodName,cell.getNumericCellValue());
					} catch (Exception e) {
						log.error("export excel fault.", e);
						return null;
					}
					map.put(score.getStudentId(), score);
				}
			}
		}
		return map;
	}

	/**
	 * 创建excel模版
	 * 
	 * @param fileName
	 * @param keys
	 */
	public static void createExcel(HttpServletRequest request,
			HttpServletResponse response, String fileName, String[] keys) {
		// 创建excel工作簿
		Workbook wb = new HSSFWorkbook();
		// 创建第一个sheet（页），并命名
		Sheet sheet = wb.createSheet("证书上传");
		// 创建第一行
		Row row = sheet.createRow((short) 0);
		for (int i = 0; i < keys.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(keys[i]);
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			try {
				throw e;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			if (bis != null)
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * 创建模板下载
	 * 
	 * @param request
	 * @param response
	 * @param fileName
	 * @param sheetName
	 * @param keys
	 * @param columns
	 */
	public static void createExcelDown(HttpServletRequest request,
			HttpServletResponse response, String fileName, String sheetName,
			String[] keys, String[] columns) {

		// 创建excel工作簿
		Workbook wb = new HSSFWorkbook();
		// 创建第一个sheet（页），并命名
		Sheet sheet = wb.createSheet(sheetName);
		// 创建第一行
		Row row = sheet.createRow((short) 0);
		for (int i = 0; i < keys.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(keys[i]);
		}
		// 创建第二行的例子
		Row row1 = sheet.createRow((short) 1);
		for (int i = 0; i < columns.length; i++) {
			Cell cell = row1.createCell(i);
			cell.setCellValue(columns[i]);
		}

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		InputStream is = null;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			wb.write(os);
			byte[] content = os.toByteArray();
			is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
			ServletOutputStream out = null;
			out = response.getOutputStream();

			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (IOException e) {
			log.error(" download excel error ! ", e);
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				log.error(" close stream error !", e);
			}
		}
	}

	/**
	 * 创建excel模版 ,含下拉菜单
	 * 
	 * @param fileName
	 * @param keys
	 * @param cellInt
	 *            第二行第几列需产生下拉菜单
	 * @param cellKeys
	 *            下拉菜单数据来源
	 */
	public static void createBankExcel(HttpServletRequest request,
			HttpServletResponse response, String fileName, String[] keys,
			Integer cellInt, String[] cellKeys) {
		// 创建excel工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建第一个sheet（页），并命名
		HSSFSheet sheet = wb.createSheet(fileName);
		// 创建第一行
		Row row = sheet.createRow((short) 0);
		for (int i = 0; i < keys.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(keys[i]);
		}
		sheet = setHSSFValidation(sheet, cellKeys, 1, 50, cellInt, cellInt);// 第一列的前501行都设置为选择列表形式.
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			try {
				throw e;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			if (bis != null)
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     * @param sheet 要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow 结束行
     * @param firstCol   开始列
     * @param endCol  结束列
     * @return 设置好的sheet.
     */  
    public static HSSFSheet setHSSFValidation(HSSFSheet sheet,  
            String[] textlist, int firstRow, int endRow, int firstCol,  
            int endCol) {  
        // 加载下拉列表内容   
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);  
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列   
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,endRow, firstCol, endCol);  
        // 数据有效性对象   
        HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);  
        sheet.addValidationData(data_validation_list);  
        return sheet;  
    }  
	
}
