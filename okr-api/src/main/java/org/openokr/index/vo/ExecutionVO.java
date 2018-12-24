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
    private Integer count0;

    /**
     * 执行情况统计数-1.正常执行
     */
    private Integer count1;

    /**
     * 执行情况统计数-2.执行有风险
     */
    private Integer count2;

    /**
     * 执行情况统计数-3.暂停执行
     */
    private Integer count3;

    /**
     * 执行情况统计数-4.提前终止
     */
    private Integer count4;

    /**
     * 执行情况统计数-5.完成
     */
    private Integer count5;

    public Integer getCount0() {
        return count0;
    }

    public void setCount0(Integer count0) {
        this.count0 = count0;
    }

    public Integer getCount1() {
        return count1;
    }

    public void setCount1(Integer count1) {
        this.count1 = count1;
    }

    public Integer getCount2() {
        return count2;
    }

    public void setCount2(Integer count2) {
        this.count2 = count2;
    }

    public Integer getCount3() {
        return count3;
    }

    public void setCount3(Integer count3) {
        this.count3 = count3;
    }

    public Integer getCount4() {
        return count4;
    }

    public void setCount4(Integer count4) {
        this.count4 = count4;
    }

    public Integer getCount5() {
        return count5;
    }

    public void setCount5(Integer count5) {
        this.count5 = count5;
    }
}