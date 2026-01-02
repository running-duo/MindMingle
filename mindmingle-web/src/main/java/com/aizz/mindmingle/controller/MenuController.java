package com.aizz.mindmingle.controller;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.domain.dto.MenuDTO;
import com.aizz.mindmingle.domain.vo.MenuVO;
import com.aizz.mindmingle.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理控制器
 *
 * @author zhangyuliang
 */
@RestController
@RequestMapping("/api/v1/menus")
public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @PostMapping
    public Response<MenuVO> createMenu(@RequestBody MenuDTO menuDTO) {
        log.info("Creating menu: {}", menuDTO.getMenuName());
        return menuService.createMenu(menuDTO);
    }

    @PutMapping("/{id}")
    public Response<MenuVO> updateMenu(@PathVariable Long id, @RequestBody MenuDTO menuDTO) {
        log.info("Updating menu: {}", id);
        return menuService.updateMenu(id, menuDTO);
    }

    @DeleteMapping("/{id}")
    public Response<Void> deleteMenu(@PathVariable Long id) {
        log.info("Deleting menu: {}", id);
        return menuService.deleteMenu(id);
    }

    @GetMapping("/{id}")
    public Response<MenuVO> getMenuById(@PathVariable Long id) {
        return menuService.getMenuById(id);
    }

    @GetMapping("/all")
    public Response<List<MenuVO>> listAllMenus() {
        return menuService.listAllMenus();
    }

    @GetMapping("/my")
    public Response<List<MenuVO>> getUserMenus() {
        Long userId = com.aizz.mindmingle.security.Access.userId();
        return menuService.getUserMenus(userId);
    }

    @PostMapping("/{id}/permissions")
    public Response<Void> assignPermissions(@PathVariable Long id, @RequestBody List<Long> permissionIds) {
        log.info("Assigning permissions to menu: {}, permissions: {}", id, permissionIds);
        return menuService.assignPermissions(id, permissionIds);
    }
}
