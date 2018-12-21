package org.openokr.sys;

import com.zzheng.framework.adapter.vo.ResponseResult;
import org.openokr.application.web.BaseController;
import org.openokr.sys.service.IShortcutMenuService;
import org.openokr.sys.vo.ShortcutMenuVOExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 快捷菜单
 *
 * @author zmxie
 */
@Controller
@RequestMapping("/sys/shortcutMenu")
public class ShortcutMenuController extends BaseController {

    @Autowired
    private IShortcutMenuService shortcutMenuService;

    /**
     * 查询快捷菜单
     *
     * @return
     */
    @GetMapping("/find.json")
    @ResponseBody
    public List<ShortcutMenuVOExt> find() {
        return shortcutMenuService.findByUserId(this.getCurrentUserId());
    }

    /**
     * 保存快捷菜单
     *
     * @param menuIds
     */
    @GetMapping("/save.json")
    @ResponseBody
    public ResponseResult save(@RequestBody List<String> menuIds) {
        return shortcutMenuService.save(this.getCurrentUserId(), menuIds);
    }
}