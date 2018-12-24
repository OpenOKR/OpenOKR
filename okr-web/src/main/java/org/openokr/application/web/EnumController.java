package org.openokr.application.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 枚举类 Controller
 */
@Controller
@RequestMapping(EnumController.PREFIX)
public class EnumController extends BaseController {

    public static final String PREFIX = "/application/enum";

}