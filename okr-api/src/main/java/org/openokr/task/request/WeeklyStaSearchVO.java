package org.openokr.task.request;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.openokr.common.constant.WeeklyStatisticConstants;
import org.openokr.util.DateUtils;

import java.text.ParseException;
import java.util.Calendar;

/**
 * @author yuxinzh
 * @create 2019/3/20
 */
@Data
@Api(value = "周报统计查询vo")
public class WeeklyStaSearchVO extends BaseVO {

    @ApiModelProperty(value = "团队ID")
    private String teamId;

    @ApiModelProperty(value = "开始时间字符串，格式yyyy-MM-dd<br>" +
            "约定:<br>" +
            "周：周一日期<br>" +
            "月：1日<br>" +
            "年：1月1日")
    private String reportStartDateStr;

    @ApiModelProperty(value = "查询时间类型，0-周 1-月 2-年")
    private String searchType;

    @ApiModelProperty(value = "分摊类别，目前有00-产品规划 01-客户定制")
    private String categoryId;

    @ApiModelProperty(value = "结束时间字符串，根据开始时间和时间类型自动计算的，不传")
    public String getReportEndDateStr() throws ParseException {
        String pattern = "yyyy-MM-dd";
        if (StringUtils.isBlank(reportStartDateStr)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        switch (reportStartDateStr) {
            case WeeklyStatisticConstants.SEARCH_TYPE_WEEK:
                return DateUtils.addDay(reportStartDateStr,pattern,6);
            case WeeklyStatisticConstants.SEARCH_TYPE_MONTH:
                calendar.setTime(DateUtils.stringToDate(reportStartDateStr,pattern));
                calendar.set(Calendar.DAY_OF_MONTH,1);
                calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
                return DateUtils.dateToString(calendar.getTime(),pattern);
            case WeeklyStatisticConstants.SEARCH_TYPE_YEAR:
                calendar.setTime(DateUtils.stringToDate(reportStartDateStr,pattern));
                calendar.set(Calendar.MONTH,11);
                calendar.set(Calendar.DAY_OF_MONTH,31);
                return DateUtils.dateToString(calendar.getTime(),pattern);
            default:
                return null;
        }
    }
}
