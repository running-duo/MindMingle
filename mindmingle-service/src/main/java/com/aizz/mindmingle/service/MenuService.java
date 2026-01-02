package com.aizz.mindmingle.service;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.domain.dto.MenuDTO;
import com.aizz.mindmingle.domain.vo.MenuVO;

import java.util.List;

/**
 * 菜单服务接口
 */
public interface MenuService {

    /**
     * 创建菜单
     */
    Response<MenuVO> createMenu(MenuDTO dto);

    /**
     * 更新菜单
     */
    Response<MenuVO> updateMenu(Long id, MenuDTO dto);

    /**
     * 删除菜单
     */
    Response<Void> deleteMenu(Long id);

    /**
     * 获取菜单详情
     */
    Response<MenuVO> getMenuById(Long id);

    /**
     * 获取所有菜单(树形结构)
     */
    Response<List<MenuVO>> listAllMenus();

    /**
     * 获取用户菜单(根据权限)
     */
    Response<List<MenuVO>> getUserMenus(Long userId);

    /**
     * 为菜单分配权限
     */
    Response<Void> assignPermissions(Long menuId, List<Long> permissionIds);
}
