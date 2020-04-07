package com.zjstudio.happly_birthday.tools.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * <p>
 * TODO 菜单资源excel解析对应实体
 * </p>
 *
 * @author 添柴灬少年
 * @version 1.0
 * @date 2020/1/8 17:25
 **/
@Data
public class ExcelMenuEntity extends BaseRowModel {
    /**
     * 一级标识
     * index:列的下标
     * value:写出时的表头（第一行的值）
     */
    @ExcelProperty(index = 0)
    private String oneIndex;

    /**
     * 二级标识
     */
    @ExcelProperty(index = 1)
    private String twoIndex;

    /**
     * 三级标识
     */
    @ExcelProperty(index = 2)
    private String threeIndex;

    /**
     * 菜单名称
     */
    @ExcelProperty(index = 3)
    private String menuName;

    /**
     * 权限值
     */
    @ExcelProperty(index = 4)
    private String menuValue;

    /**
     * 排序
     */
    @ExcelProperty(index = 5)
    private Integer sort;

    /**
     * 图标
     */
    @ExcelProperty(index = 6)
    private String icon;

    /**
     * 类型（0：目录、1：菜单、2：按钮）
     */
    @ExcelProperty(index = 7)
    private Integer type;

    /**
     * 资源路径
     */
    @ExcelProperty(index = 8)
    private String url;
}
