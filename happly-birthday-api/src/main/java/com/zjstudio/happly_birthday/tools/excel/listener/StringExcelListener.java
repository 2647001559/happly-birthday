package com.zjstudio.happly_birthday.tools.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * <p>
 * TODO String类,解析监听器
 * </p>
 *
 * @author 添柴灬少年
 * @version 1.0
 * @date 2020/1/9 9:24
 **/
public class StringExcelListener extends AnalysisEventListener<List<String>> {
    /**
     * 自定义用于暂时存储data 可以通过实例获取该值
     */
    private List<List<String>> dataList = Lists.newArrayList();

    @Override
    public void invoke(List<String> rowInfo, AnalysisContext context) {
        // 数据存储到list，供批量处理，或后续自己业务逻辑处理。
        dataList.add(rowInfo);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //解析结束销毁不用的资源
    }

    public List<List<String>> getDataList() {
        return dataList;
    }

    public void setDataList(List<List<String>> dataList) {
        this.dataList = dataList;
    }

    public void clear() {
        dataList.clear();
    }
}
