package org.openokr.sys.entity;

import com.zzheng.framework.mybatis.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "t_okr_sys_shortcut_menu")
public class ShortcutMenuEntity extends BaseEntity implements Serializable {
    /** 用户ID */
    private String userId;

    /** 菜单ID */
    private String menuId;

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     * @return userId
     */
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * 用户ID
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
        addSettedField("userId");
    }

    /**
     * 菜单ID
     * @return menuId
     */
    @Column(name = "menu_id")
    public String getMenuId() {
        return menuId;
    }

    /**
     * 菜单ID
     * @param menuId
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
        addSettedField("menuId");
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return ShortcutMenuEntityMapper.class;
    }
}