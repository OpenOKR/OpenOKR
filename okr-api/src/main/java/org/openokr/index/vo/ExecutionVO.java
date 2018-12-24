package org.openokr.index.vo;

import java.io.Serializable;

/**
 * 执行情况VO
 */
public class ExecutionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 执行情况统计数-0.未开始
     */
    private Integer count_0;

    /**
     * 执行情况统计数-1.正常执行
     */
    private Integer count_1;

    /**
     * 执行情况统计数-2.执行有风险
     */
    private Integer count_2;

    /**
     * 执行情况统计数-3.暂停执行
     */
    private Integer count_3;

    /**
     * 执行情况统计数-4.提前终止
     */
    private Integer count_4;

    /**
     * 执行情况统计数-5.完成
     */
    private Integer count_5;

    public Integer getCount_0() {
        return count_0;
    }

    public void setCount_0(Integer count_0) {
        this.count_0 = count_0;
    }

    public Integer getCount_1() {
        return count_1;
    }

    public void setCount_1(Integer count_1) {
        this.count_1 = count_1;
    }

    public Integer getCount_2() {
        return count_2;
    }

    public void setCount_2(Integer count_2) {
        this.count_2 = count_2;
    }

    public Integer getCount_3() {
        return count_3;
    }

    public void setCount_3(Integer count_3) {
        this.count_3 = count_3;
    }

    public Integer getCount_4() {
        return count_4;
    }

    public void setCount_4(Integer count_4) {
        this.count_4 = count_4;
    }

    public Integer getCount_5() {
        return count_5;
    }

    public void setCount_5(Integer count_5) {
        this.count_5 = count_5;
    }
}