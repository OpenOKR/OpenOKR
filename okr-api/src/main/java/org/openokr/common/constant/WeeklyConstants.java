package org.openokr.common.constant;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author yuxinzh
 * @create 2019/3/4
 */
public class WeeklyConstants {

    private WeeklyConstants(){}

    /**
     * 审批状态:
     * 0 待审核
     * 1 已确认
     * 2 已驳回
     */
    public static final String AUDIT_STATUS_WAITING = "0";
    public static final String AUDIT_STATUS_PASS = "1";
    public static final String AUDIT_STATUS_REFUSE = "2";
    public static final Map<String,String> AUDIT_STATUS_MAP = Maps.newHashMap();
    static {
        AUDIT_STATUS_MAP.put(AUDIT_STATUS_WAITING,"待审核");
        AUDIT_STATUS_MAP.put(AUDIT_STATUS_PASS,"已确认");
        AUDIT_STATUS_MAP.put(AUDIT_STATUS_REFUSE,"已驳回");
    }
}
