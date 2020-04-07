package com.zjstudio.happly_birthday.tools.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * <p>
 * TODO  Model类,解析监听器
 * </p>
 *
 * @author 添柴灬少年
 * @version 1.0
 * @date 2020/1/9 9:23
 **/
public class ModelExcelListener extends AnalysisEventListener<BaseRowModel> {
    /**
     * 自定义用于暂时存储data 可以通过实例获取该值
     */
    private List<BaseRowModel> dataList = Lists.newArrayList();

    @Override
    public void invoke(BaseRowModel rowInfo, AnalysisContext context) {
        dataList.add(rowInfo);
    }

    /**
     * 解析结束销毁不用的资源
     *
     * @param context AnalysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //解析结束销毁不用的资源
    }

    /**
     * 获取
     *
     * @return 返回sheet数据
     */
    public List<? extends BaseRowModel> getDataList() {
        return dataList;
    }

    /**
     * 设置sheet数据
     *
     * @param dataList 数据
     */
    public void setDataList(List<BaseRowModel> dataList) {
        this.dataList = dataList;
    }

    /**
     * 清空数据
     */
    public void clear() {
        dataList.clear();
    }
}
