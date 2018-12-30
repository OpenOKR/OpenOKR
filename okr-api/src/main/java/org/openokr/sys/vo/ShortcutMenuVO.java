package org.openokr.sys.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;

public class ShortcutMenuVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 菜单ID
     */
    private String menuId;



    /**
     * 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 菜单ID
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * 菜单ID
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

}