package com.llw.mvplibrary.base;

/**
 * 基类View，可以根据实际情况写方法
 * @author llw
 */
public interface BaseView {
    //获取列表失败返回
    void getFailed(Throwable e);
}
