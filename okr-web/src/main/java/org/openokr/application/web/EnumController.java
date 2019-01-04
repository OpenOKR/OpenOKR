package org.openokr.application.web;

import org.openokr.manage.enumerate.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 枚举类 Controller
 */
@Controller
@RequestMapping(EnumController.PREFIX)
public class EnumController extends BaseController {

    public static final String PREFIX = "/application/enum";

    @RequestMapping("/messageMarkList.json")
    @ResponseBody
    public List<Map<String, Object>> messageMarkList() {
        return MessageMarkEnum.toList();
    }

    @RequestMapping("/messageTypeList.json")
    @ResponseBody
    public List<Map<String, Object>> messageTypeList() {
        return MessageTypeEnum.toList();
    }

    @RequestMapping("/objectivesStatusList.json")
    @ResponseBody
    public List<Map<String, Object>> objectivesStatusList() {
        return ObjectivesStatusEnum.toList();
    }

    @RequestMapping("/executeStatusList.json")
    @ResponseBody
    public List<Map<String, Object>> executeStatusList() {
        return ExecuteStatusEnum.toList();
    }
}