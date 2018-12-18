package org.openokr.sys.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;

public class PermissionVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 权限 id，格式：菜单的sn:操作code
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 菜单Id
     */
    private String menuId;

    /**
     * 描述
     */
    private String description;

    /**
     * 
     */
    private String code;



    /**
     * 权限 id，格式：菜单的sn:操作code
     */
    public String getId() {
        return id;
    }

    /**
     * 权限 id，格式：菜单的sn:操作code
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 菜单Id
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * 菜单Id
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    /**
     * 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     */
    public void setCode(String code) {
        this.code = code;
    }

}