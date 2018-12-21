package org.openokr.utils;

import java.util.Date;

/**
 * Created by zhengzheng on 2018/12/18.
 */
public class DateUtils extends com.zzheng.framework.base.utils.DateUtils {

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

}