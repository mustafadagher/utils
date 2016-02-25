package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author Mustafa Dagher
 */
public class ExcelUtils {

	/**
	 * exports generic collection to an excel sheet
	 * 
	 * @param list
	 *            The collection you want to export
	 * @param type
	 *            The class type of the collection
	 * @param filePath
	 *            The path (including the filename.xls) you want to store the
	 *            excel sheet in
	 * @throws ClassNotFoundException if the class type sent does not exist
	 * @throws IOException
	 *             -if any thing cannot be written, the file exists but is a
	 *             directory rather than a regular file, does not exist but
	 *             cannot be created, or cannot be opened for any other reason
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> void populateExcelWorksheet(List<T> list, Class<T> type,
			String filePath) throws ClassNotFoundException, IOException,
			IllegalArgumentException, IllegalAccessException {

		String workbookName = filePath.substring(
				filePath.lastIndexOf('\\') + 1, filePath.lastIndexOf('.'));

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(workbookName);

		Class<T> clazz = type;

		Field[] fields = clazz.getDeclaredFields();

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		populateSheetHeader(sheet, fields, cellStyle);

		fillSheetData(list, sheet, fields);

		FileOutputStream fileOut = new FileOutputStream(filePath);
		wb.write(fileOut);
		fileOut.close();
	}

	/**
	 * create sheet grid headers
	 * 
	 * @param sheet
	 * @param fields
	 * @param cellStyle
	 */
	private static void populateSheetHeader(HSSFSheet sheet, Field[] fields,
			HSSFCellStyle cellStyle) {
		HSSFRow hssfHeader = sheet.createRow(0);
		int cellIdx = 0;
		for (Field field : fields) {
			HSSFCell hssfCell = hssfHeader.createCell(cellIdx++);
			hssfCell.setCellStyle(cellStyle);
			hssfCell.setCellValue(field.getName());
		}
	}

	/**
	 * fill sheet with data in list
	 * 
	 * @param list
	 * @param sheet
	 * @param fields
	 * @throws IllegalAccessException
	 */
	private static <T> void fillSheetData(List<T> list, HSSFSheet sheet,
			Field[] fields) throws IllegalAccessException {
		int rowIdx = 1;
		int cellIdx = 0;

		for (T item : list) {
			HSSFRow hssfRow = sheet.createRow(rowIdx++);
			cellIdx = 0;
			for (Field field : fields) {
				field.setAccessible(true);
				HSSFCell hssfCell = hssfRow.createCell(cellIdx++);
				hssfCell.setCellValue((String) field.get(item));
			}
		}
	}

}
