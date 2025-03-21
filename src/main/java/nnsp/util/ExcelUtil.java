package nnsp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.ScriptStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;

public class ExcelUtil {
	/**
	 * <pre>
	 * 엑셀 제목 Format
	 * </pre>
	 * 
	 * @return 엑셀 Cell format
	 * @throws Exception
	 * @return WritableCellFormat
	 */
	public static WritableCellFormat setTitleFormat() throws Exception {
		WritableCellFormat titleFormat = new WritableCellFormat();
		try {
			titleFormat.setFont(new WritableFont(WritableFont.ARIAL, // Font Type
					13, // Font Size
					WritableFont.BOLD, // Font Weight
					false, // Font Italic 여부
					UnderlineStyle.NO_UNDERLINE, // Underline
					Colour.BLACK, // Font Color
					ScriptStyle.NORMAL_SCRIPT // Script Style
			));
			titleFormat.setAlignment(Alignment.CENTRE); // Cell Text-align
			titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // Cell Vertical Align
			titleFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
			titleFormat.setBackground(Colour.GREY_25_PERCENT);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return titleFormat;
	}

	/**
	 * <pre>
	 * 엑셀 제목 Format
	 * </pre>
	 * 
	 * @return
	 * @throws Exception
	 * @return WritableCellFormat
	 */
	public static WritableCellFormat setTitleFormat2() throws Exception {
		WritableCellFormat titleFormat = new WritableCellFormat(NumberFormats.TEXT);
		try {
			titleFormat.setFont(new WritableFont(WritableFont.ARIAL, // Font Type
					13, // Font Size
					WritableFont.BOLD, // Font Weight
					false, // Font Italic 여부
					UnderlineStyle.NO_UNDERLINE, // Underline
					Colour.BLACK, // Font Color
					ScriptStyle.NORMAL_SCRIPT // Script Style
			));
			titleFormat.setAlignment(Alignment.CENTRE); // Cell Text-align
			titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // Cell Vertical Align
			titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			titleFormat.setBackground(Colour.YELLOW2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return titleFormat;
	}

	/**
	 * <pre>
	 * 엑셀 일반 셀 스타일
	 * </pre>
	 * 
	 * @return 엑셀 Cell format
	 * @throws Exception
	 * @return WritableCellFormat
	 */
	public static WritableCellFormat setCellFormat() throws Exception {
		WritableCellFormat cellFormat = new WritableCellFormat(NumberFormats.TEXT);
		try {
			cellFormat.setFont(new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80, ScriptStyle.NORMAL_SCRIPT));
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setAlignment(Alignment.CENTRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellFormat;
	}
	
	/**
	 * <pre>
	 * 엑셀 일반 셀 스타일2
	 * </pre>
	 * 
	 * @return 엑셀 Cell format
	 * @throws Exception
	 * @return WritableCellFormat
	 */
	public static WritableCellFormat setCellFormat2() throws Exception {
		WritableCellFormat cellFormat = new WritableCellFormat(NumberFormats.TEXT);
		try {
			cellFormat.setFont(new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80, ScriptStyle.NORMAL_SCRIPT));
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setAlignment(Alignment.LEFT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellFormat;
	}

	public static String createFileName(String fileName) {
		SimpleDateFormat fileFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return new StringBuilder(fileName).append("_").append(fileFormat.format(new Date())).append(".xls").toString();
	}
}