/*
 * Copyright (c) 2018, 吴汶泽 (wenzewoo@gmail.com).
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.one.util;


import com.example.one.exception.ExcelKitRuntimeException;
import jdk.nashorn.internal.runtime.options.Options;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @author wuwenze
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class POIUtil {

  private static final int mDefaultRowAccessWindowSize = 100;

  public static SXSSFWorkbook newSXSSFWorkbook(String filePath) {
	  SXSSFWorkbook workbook = null;
	  InputStream fis =null;
	  try {
		  fis = POIUtil.class.getResourceAsStream(filePath);
//		  fis = OSSUtils.getFile(filePath,ossClient);
		  XSSFWorkbook wb = new XSSFWorkbook(fis);
		  workbook = new SXSSFWorkbook(wb, POIUtil.mDefaultRowAccessWindowSize);
	  }catch(Exception e) {
		  e.printStackTrace();
		  throw new ExcelKitRuntimeException("读取模板错误!");
	  }finally {
		  if(fis!=null){
			  try {
				  fis.close();
			  } catch (IOException e) {
				  e.printStackTrace();
			  }
		  }
	  }
	  return workbook;
  }

  private static SXSSFWorkbook newSXSSFWorkbook(int rowAccessWindowSize) {
    return new SXSSFWorkbook(rowAccessWindowSize);
  }

  public static SXSSFWorkbook newSXSSFWorkbook() {
    return POIUtil.newSXSSFWorkbook(POIUtil.mDefaultRowAccessWindowSize);
  }

  public static SXSSFSheet newSXSSFSheet(SXSSFWorkbook wb, String sheetName) {
    return wb.createSheet(sheetName);
  }

  public static SXSSFRow newSXSSFRow(SXSSFSheet sheet, int index) {
    return sheet.createRow(index);
  }

  public static SXSSFCell newSXSSFCell(SXSSFRow row, int index) {
    return row.createCell(index);
  }

  public static void setColumnWidth(
      SXSSFSheet sheet, int index, Short width, String value) {
    boolean widthNotHaveConfig = (null == width || width == -1);
    if (widthNotHaveConfig && !ValidatorUtil.isEmpty(value)) {
      sheet.setColumnWidth(index, (short) (value.length() * 2048));
    } else {
      width = widthNotHaveConfig ? 200 : width;
      sheet.setColumnWidth(index, (short) (width * 35.7));
    }
  }
 
  public static void setColumnCellRangeNew(SXSSFWorkbook workbook,SXSSFSheet sheet, String[] list,
	      int firstRow, int endRow,
	      int firstCell, int endCell,String[] dynamicOpiton) {
	  
	  if (null != list) {
	      String[] datasource = list;
	      if(null!=dynamicOpiton && dynamicOpiton.length>0){
	          datasource=dynamicOpiton;
          }
	      if (null != datasource && datasource.length > 0) {
	    	  String hiddenSheetName = "a" + UUID.randomUUID().toString().replace("-", "").substring(1, 31);
	 		 //excel中的"名称"，用于标记隐藏sheet中的用作菜单下拉项的所有单元格
	 		 String formulaId = "form" + UUID.randomUUID().toString().replace("-", "");
	 	     SXSSFSheet hiddenSheet = workbook.createSheet(hiddenSheetName);//用于存储 下拉菜单数据
	 	     //存储下拉菜单项的sheet页不显示
	 	     workbook.setSheetHidden(workbook.getSheetIndex(hiddenSheet), true);
	 	     
	 	     SXSSFRow row = null;
	 	     SXSSFCell cell = null;
	 	     //隐藏sheet中添加菜单数据
	 	     for (int i = 0; i < datasource.length; i++)
	 	     {
	 	         row = hiddenSheet.createRow(i);
	 	         //隐藏表的数据列必须和添加下拉菜单的列序号相同，否则不能显示下拉菜单
	 	         cell = row.createCell(firstCell);
	 	         cell.setCellValue(datasource[i]);
	 	     }
	 	     Name namedCell = workbook.createName();//创建"名称"标签，用于链接
	 	     namedCell.setNameName(formulaId);
	 	     namedCell.setRefersToFormula(hiddenSheetName + "!A$1:A$" + datasource.length);
	 	     DataValidationHelper dvHelper = sheet.getDataValidationHelper();
	 	     DataValidationConstraint dvConstraint = dvHelper.createFormulaListConstraint(formulaId);
	 	     
	 	     CellRangeAddressList addressList = new CellRangeAddressList(firstRow, endRow, firstCell, endCell);
	 	     DataValidation validation = dvHelper
	 	             .createValidation(dvConstraint, addressList);//添加菜单(将单元格与"名称"建立关联)
	 	     sheet.addValidationData(validation);
	    	  
	      }}
  }
  
  public static void setColumnCellRange(SXSSFSheet sheet, String[] options,
      int firstRow, int endRow,
      int firstCell, int endCell) {
    if (null != options) {
      String[] datasource = options;
      if (null != datasource && datasource.length > 0) {
        if (datasource.length > 100) {
          throw new ExcelKitRuntimeException("Options item too much.");
        }

        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
        DataValidationConstraint explicitListConstraint = validationHelper
            .createExplicitListConstraint(datasource);
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCell,
            endCell);
        DataValidation validation = validationHelper
            .createValidation(explicitListConstraint, regions);
        validation.setSuppressDropDownArrow(true);
        validation.createErrorBox("提示", "请从下拉列表选取");
        validation.setShowErrorBox(true);
        sheet.addValidationData(validation);
      }
    }
  }

  public static void write(SXSSFWorkbook wb, OutputStream out) {
    try {
      if (null != out) {
        wb.write(out);
        out.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != out) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void download(
          SXSSFWorkbook wb, HttpServletResponse response, String filename) {
    try {
      OutputStream out = response.getOutputStream();
      response.setContentType(Const.XLSX_CONTENT_TYPE);
      response.setHeader(Const.XLSX_HEADER_KEY,
          String.format(Const.XLSX_HEADER_VALUE_TEMPLATE, filename));
      POIUtil.write(wb, out);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Object convertByExp(Object propertyValue, String converterExp)
      throws Exception {
    try {
      String[] convertSource = converterExp.split(",");
      for (String item : convertSource) {
        String[] itemArray = item.split("=");
        if (itemArray[0].equals(propertyValue)) {
          return itemArray[1];
        }
      }
    } catch (Exception e) {
      throw e;
    }
    return propertyValue;
  }

  public static int countNullCell(String ref, String ref2) {
    // excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
    String xfd = ref.replaceAll("\\d+", "");
    String xfd_1 = ref2.replaceAll("\\d+", "");

    xfd = POIUtil.fillChar(xfd, 3, '@', true);
    xfd_1 = POIUtil.fillChar(xfd_1, 3, '@', true);

    char[] letter = xfd.toCharArray();
    char[] letter_1 = xfd_1.toCharArray();
    int res =
        (letter[0] - letter_1[0]) * 26 * 26 + (letter[1] - letter_1[1]) * 26 + (letter[2]
            - letter_1[2]);
    return res - 1;
  }

  private static String fillChar(String str, int len, char let, boolean isPre) {
    int len_1 = str.length();
    if (len_1 < len) {
      if (isPre) {
        for (int i = 0; i < (len - len_1); i++) {
          str = let + str;
        }
      } else {
        for (int i = 0; i < (len - len_1); i++) {
          str = str + let;
        }
      }
    }
    return str;
  }

  public static void checkExcelFile(File file) {
    String filename = null != file ? file.getAbsolutePath() : null;
    if (null == filename || !file.exists()) {
      throw new ExcelKitRuntimeException("Excel file[" + filename + "] does not exist.");
    }
    if (!filename.endsWith(Const.XLSX_SUFFIX)) {
      throw new ExcelKitRuntimeException(
          "[" + filename + "]Only .xlsx formatted files are supported.");
    }
  }


}
