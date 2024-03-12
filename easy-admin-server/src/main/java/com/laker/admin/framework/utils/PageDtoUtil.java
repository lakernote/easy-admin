package com.laker.admin.framework.utils;

import cn.hutool.core.util.PageUtil;
import lombok.Data;

import java.util.List;

/**
 * 内存分页
 */
@Data
public class PageDtoUtil {
    //当前页
    private int pageNum;
    //页大小
    private int pageSize;
    //总页数
    private int pages;
    //数据总数
    private int total;
    //分页结果
    private List<?> pageList;

    /**
     * @return
     * @description 内存分页，hutool工具类相关
     * @Param
     * @author tzh
     * @date
     */
    public static PageDtoUtil getPageDto(List<?> list, int pageNum, int pageSize) {
        PageDtoUtil pageDtoUtil = new PageDtoUtil();
        //总页数
        int pages = PageUtil.totalPage(list.size(), pageSize);
        //开始位置和结束位置
        int[] startEnd = PageUtil.transToStartEnd(pageNum - 1, pageSize);
        //分页后的结果集
        List<?> pageList = null;
        if (startEnd[1] < list.size()) {
            pageList = list.subList(startEnd[0], startEnd[1]);
        } else {
            pageList = list.subList(startEnd[0], list.size());
        }

        pageDtoUtil.setPageSize(pageSize);//页大小
        pageDtoUtil.setPageNum(pageNum);//当前页码
        pageDtoUtil.setPages(pages);//总页数
        pageDtoUtil.setTotal(list.size());//数据总数
        pageDtoUtil.setPageList(pageList);//分页结果集

        return pageDtoUtil;
    }

}