package com.zjstudio.happly_birthday.tools.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zjstudio.happly_birthday.tools.excel.exception.EasyExcelException;
import com.zjstudio.happly_birthday.tools.excel.listener.ModelExcelListener;
import com.zjstudio.happly_birthday.tools.excel.listener.StringExcelListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>
 * TODO easyexcel工具封装
 * </p>
 *
 * @author 添柴灬少年
 * @version 1.0
 * @date 2020/1/8 17:25
 **/
public class EasyExcelUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EasyExcelUtil.class);

    /**
     * 通过String类,读取工作表数据
     *
     * @param filePath 文件路径
     * @return 数据集
     */
    public static Map<String, List<List<String>>> readExcelByString(String filePath) {
        return readExcelByString(filePath, null);
    }

    /**
     * 通过String类,读取工作表数据
     *
     * @param filePath  文件路径
     * @param sheetName sheetName
     * @return 数据集合
     */
    public static Map<String, List<List<String>>> readExcelByString(String filePath, String sheetName) {
        // 创建返回信息
        Map<String, List<List<String>>> dataListMap;
        // 解析监听器
        StringExcelListener excelListener = new StringExcelListener();
        InputStream inputStream = null;
        try {
            // 创建文件流
            inputStream = new FileInputStream(filePath);
            dataListMap = readExcelByStringFromInputStream(inputStream, sheetName);

        } catch (Exception e) {
            throw new EasyExcelException("readExcelByModel from filePath failed." + e, e);
        } finally {
            // 关闭文件流
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LOGGER.error("inputStream.close failed!", e);
            }
        }
        return dataListMap;
    }

    /**
     * 通过String类,读取工作表数据
     *
     * @param inputStream 文件流
     * @param sheetName   sheetName
     * @return 数据集合
     */
    public static Map<String, List<List<String>>> readExcelByStringFromInputStream(InputStream inputStream,
                                                                                   String sheetName) {
        // 创建返回信息
        Map<String, List<List<String>>> dataListMap = Maps.newLinkedHashMap();
        // 解析监听器
        StringExcelListener excelListener = new StringExcelListener();
        try {
            // 创建文件流
            ExcelReader excelReader = EasyExcelFactory.getReader(inputStream, excelListener);
            // 得到所有工作表
            List<Sheet> sheets = excelReader.getSheets();
            // 取所有工作表数据
            for (Sheet sheet : sheets) {
                // 工作表名称
                String currentSheetName = sheet.getSheetName();
                // 没有指定工作表，或多个工作表
                if (Strings.isNullOrEmpty(sheetName) || Splitter.on(',').trimResults().omitEmptyStrings().splitToList(
                        sheetName).contains(currentSheetName)) {
                    // 读取Excel数据
                    excelReader.read(sheet);
                    // 返回明细数据
                    List<List<String>> sheetDataInfo = Lists.newArrayList(excelListener.getDataList());
                    // 将工作表数据放入工作薄
                    dataListMap.put(currentSheetName, sheetDataInfo);
                    // 清除缓存数据
                    excelListener.clear();
                }
            }

        } catch (Exception e) {
            throw new EasyExcelException("readExcelByStringFromInputStream from inputStream failed." + e, e);
        }
        return dataListMap;
    }

    /**
     * 通过Model类,读取工作表数据
     *
     * @param filePath 文件路径
     * @param clazz    BaseRowModel
     * @return 数据集合
     */
    public static Map<String, List<? extends BaseRowModel>> readExcelByModel(String filePath,
                                                                             Class<? extends BaseRowModel> clazz) {
        return readExcelByModel(filePath, null, clazz);
    }

    /**
     * 通过Model类,读取工作表数据
     *
     * @param file  文件
     * @param clazz BaseRowModel
     * @return 数据集合
     */
    public static Map<String, List<? extends BaseRowModel>> readExcelByModel(File file,
                                                                             Class<? extends BaseRowModel> clazz) {
        return readExcelByModel(file, null, clazz);
    }

    /**
     * 通过Model类,读取工作表数据
     *
     * @param filePath  文件路径
     * @param sheetName sheetName
     * @param clazz     BaseRowModel
     * @return 数据集合
     */
    public static List<? extends BaseRowModel> readExcelByModelSheetName(String filePath, String sheetName,
                                                                         Class<? extends BaseRowModel> clazz) {
        Map<String, List<? extends BaseRowModel>> dataListMap = readExcelByModel(filePath, sheetName, clazz);
        return dataListMap.getOrDefault(sheetName, null);
    }

    /**
     * 通过Model类,读取工作表数据
     *
     * @param file      文件
     * @param sheetName sheetName
     * @param clazz     BaseRowModel
     * @return 数据集合
     */
    public static List<? extends BaseRowModel> readExcelByModelSheetName(File file, String sheetName,
                                                                         Class<? extends BaseRowModel> clazz) {
        Map<String, List<? extends BaseRowModel>> dataListMap = readExcelByModel(file, sheetName, clazz);
        return dataListMap.getOrDefault(sheetName, null);
    }

    /**
     * 通过Model类,读取工作表数据
     *
     * @param filePath  文件路径
     * @param sheetName sheetName
     * @param clazz     BaseRowModel
     * @return 数据集合
     */
    public static Map<String, List<? extends BaseRowModel>> readExcelByModel(String filePath, String sheetName,
                                                                             Class<? extends BaseRowModel> clazz) {
        Map<String, List<? extends BaseRowModel>> dataListMap;
        InputStream inputStream = null;
        try {
            // 创建文件流
            inputStream = new FileInputStream(filePath);

            dataListMap = readExcelByModelFromInputStream(inputStream, sheetName, clazz);

        } catch (Exception e) {
            throw new EasyExcelException("readExcelByModel from filePath failed." + e, e);
        } finally {
            // 关闭文件流
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LOGGER.error("inputStream.close failed!", e);
            }
        }
        return dataListMap;
    }

    /**
     * 通过Model类,读取工作表数据
     *
     * @param file      文件
     * @param sheetName sheetName
     * @param clazz     BaseRowModel
     * @return 数据集合
     */
    public static Map<String, List<? extends BaseRowModel>> readExcelByModel(File file, String sheetName,
                                                                             Class<? extends BaseRowModel> clazz) {
        Map<String, List<? extends BaseRowModel>> dataListMap;
        InputStream inputStream = null;

        try {
            // 创建文件流
            inputStream = new FileInputStream(file);
            dataListMap = readExcelByModelFromInputStream(inputStream, sheetName, clazz);
            // 关闭文件流
            inputStream.close();
        } catch (Exception e) {
            throw new EasyExcelException("readExcelByModel from File failed." + e, e);

        } finally {
            // 关闭文件流
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LOGGER.error("inputStream.close failed!", e);
            }
        }
        return dataListMap;
    }

    /**
     * 通过Model类,读取工作表数据
     *
     * @param inputStream 文件流
     * @param sheetName   sheetName
     * @param clazz       BaseRowModel
     * @return 数据集合
     */
    public static Map<String, List<? extends BaseRowModel>> readExcelByModelFromInputStream(InputStream inputStream,
                                                                                            String sheetName,
                                                                                            Class<?
                                                                                                    extends BaseRowModel> clazz) {
        // 解析每行结果在listener中处理
        // 创建返回信息
        Map<String, List<? extends BaseRowModel>> dataListMap = Maps.newLinkedHashMap();
        // 解析监听器
        ModelExcelListener excelListener = new ModelExcelListener();
        try {
            // 创建文件流
            ExcelReader excelReader = EasyExcelFactory.getReader(inputStream, excelListener);

            // 得到所有工作表
            List<Sheet> sheets = excelReader.getSheets();
            // 取所有工作表数据
            for (Sheet sheet : sheets) {
                // 工作表名称
                String currentSheetName = sheet.getSheetName();
                if (Strings.isNullOrEmpty(sheetName) || Splitter.on(',').trimResults().omitEmptyStrings().splitToList(
                        sheetName).contains(currentSheetName)) {
                    // 设置模板
                    sheet.setClazz(clazz);
                    // 读取Excel数据
                    excelReader.read(sheet);
                    // 返回明细数据
                    List<? extends BaseRowModel> sheetDataInfo = Lists.newArrayList(excelListener.getDataList());
                    // 将工作表数据放入工作薄
                    dataListMap.put(currentSheetName, sheetDataInfo);
                    // 清除缓存数据
                    excelListener.clear();

                }
            }
        } catch (Exception e) {
            throw new EasyExcelException("readExcelByModel from inputStream failed." + e, e);
        }
        return dataListMap;
    }

    /**
     * 通过String类,将一个sheet写入到一个Excel
     *
     * @param filePath  文件路径
     * @param sheetName sheetName
     * @param dataList  数据集
     */
    public static void writeExcelByString(String filePath, String sheetName, List<List<String>> dataList) {
        // 创建返回信息
        Map<String, List<List<String>>> dataListMap = Maps.newLinkedHashMap();
        // 将工作表放入到Excel中
        dataListMap.put(sheetName, dataList);
        // 输出Excel数据
        writeExcelByString(filePath, dataListMap);
    }

    /**
     * 通过String类,将多个sheet写入到一个Excel
     *
     * @param filePath    文件路径
     * @param dataListMap 数据集
     */
    public static void writeExcelByString(String filePath, Map<String, List<List<String>>> dataListMap) {
        try {
            // 工作表编号
            int sheetNo = 1;
            // 创建文件流
            OutputStream out = new FileOutputStream(filePath);
            ExcelWriter writer = EasyExcelFactory.getWriter(out);
            // 循环写入每个工作表
            for (Entry<String, List<List<String>>> entry : dataListMap.entrySet()) {
                // 得到工作表名称
                String sheetName = entry.getKey();
                // 得到工作表数据
                List<List<String>> dataList = entry.getValue();
                // 设置工作表信息
                Sheet sheet1 = new Sheet(sheetNo++, 1, null, sheetName, null);
                // 设置开始行为-1
                sheet1.setStartRow(-1);
                // 设置自适应宽度
                sheet1.setAutoWidth(Boolean.TRUE);
                // 开始写数据
                writer.write0(dataList, sheet1);
            }
            // 清空缓存
            writer.finish();
            // 关闭文件
            out.close();
        } catch (Exception e) {
            throw new EasyExcelException("writeExcelByString failed." + e, e);
        }
    }

    /**
     * 通过Model类,将一个sheet写入到一个Excel
     *
     * @param filePath  文件路径
     * @param sheetName sheetName
     * @param dataList  数据集
     * @param clazz     BaseRowModel
     */
    public static void writeExcelByModel(String filePath, String sheetName, List<? extends BaseRowModel> dataList,
                                         Class<? extends BaseRowModel> clazz) {
        // 创建返回信息
        Map<String, List<? extends BaseRowModel>> dataListMap = Maps.newLinkedHashMap();
        // 将工作表放入到Excel中
        dataListMap.put(sheetName, dataList);
        // 输出Excel数据
        writeExcelByModel(filePath, dataListMap, clazz);
    }

    /**
     * 通过String类,将多个sheet写入到一个Excel
     *
     * @param filePath    文件路径
     * @param dataListMap 数据集
     * @param clazz       BaseRowModel
     */
    public static void writeExcelByModel(String filePath, Map<String, List<? extends BaseRowModel>> dataListMap,
                                         Class<? extends BaseRowModel> clazz) {
        try {
            // 创建文件流
            OutputStream out = new FileOutputStream(filePath);
            // 写入文件
            writeIntoOutputStream(dataListMap, clazz, out);
            // 关闭文件
            out.close();
        } catch (Throwable e) {
            throw new EasyExcelException("write to file failed." + e, e);
        }
    }

    /**
     * export to byte array.
     *
     * @param sheetName sheetName
     * @param dataList  data
     * @param clazz     BaseRowModel
     * @return return a byte array with data.
     */
    public static byte[] exportByteArray(String sheetName, List<? extends BaseRowModel> dataList,
                                         Class<? extends BaseRowModel> clazz) {
        // 创建返回信息
        Map<String, List<? extends BaseRowModel>> dataListMap = Maps.newLinkedHashMap();
        // 将工作表放入到Excel中
        dataListMap.put(sheetName, dataList);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        writeIntoOutputStream(dataListMap, clazz, out);

        return out.toByteArray();
    }

    /**
     * export to byte array.
     *
     * @param dataListMap data
     * @param clazz       BaseRowModel
     * @return return a byte array with data.
     */
    public static byte[] exportByteArray(Map<String, List<? extends BaseRowModel>> dataListMap,
                                         Class<? extends BaseRowModel> clazz) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        writeIntoOutputStream(dataListMap, clazz, out);

        return out.toByteArray();
    }

    /**
     * 数据写入输出流
     *
     * @param dataListMap  数据
     * @param clazz        BaseRowModel
     * @param outputStream 输出流
     */
    public static void writeIntoOutputStream(Map<String, List<? extends BaseRowModel>> dataListMap,
                                             Class<? extends BaseRowModel> clazz,
                                             OutputStream outputStream) {
        try {
            // 工作表编号
            int sheetNo = 1;
            // 创建文件流
            ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
            // 循环写入每个工作表
            for (Entry<String, List<? extends BaseRowModel>> entry : dataListMap.entrySet()) {
                // 得到工作表名称
                String sheetName = entry.getKey();
                // 得到工作表数据
                List<? extends BaseRowModel> dataList = entry.getValue();
                // 设置工作表信息
                Sheet sheet1 = new Sheet(sheetNo++, 1, clazz, sheetName, null);
                // 设置自适应宽度
                sheet1.setAutoWidth(Boolean.TRUE);
                // 开始写数据
                writer.write(dataList, sheet1);
            }
            // 清空缓存
            writer.finish();
        } catch (Throwable e) {
            throw new EasyExcelException("write to OutputStream failed." + e, e);
        }

    }
}