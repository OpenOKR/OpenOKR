package org.openokr.common.constant;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @Description :
 * @author: cww
 * @DateTime: 2019/3/19 16:20
 */

public class DailyConstant {

    /**
     * 日报审批状态:
     * 00/null 待审核
     * 01 已确认
     * 02 已驳回
     */
    public static final String DAILY_AUDIT_STATUS_WAITING = "00";
    public static final String DAILY_AUDIT_STATUS_PASS = "01";
    public static final String DAILY_AUDIT_STATUS_REFUSE = "02";
    public static final Map<String,String> DAILY_AUDIT_STATUS_MAP = Maps.newHashMap();
    static {
        DAILY_AUDIT_STATUS_MAP.put(DAILY_AUDIT_STATUS_WAITING,"待审核");
        DAILY_AUDIT_STATUS_MAP.put(DAILY_AUDIT_STATUS_PASS,"已确认");
        DAILY_AUDIT_STATUS_MAP.put(DAILY_AUDIT_STATUS_REFUSE,"已驳回");
    }
}
