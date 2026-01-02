package com.aizz.mindmingle.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.common.ResponseCode;
import com.aizz.mindmingle.domain.dos.MenuDO;
import com.aizz.mindmingle.domain.dos.MenuPermissionDO;
import com.aizz.mindmingle.domain.dos.PermissionDO;
import com.aizz.mindmingle.domain.dos.RolePermissionDO;
import com.aizz.mindmingle.domain.dos.UserDO;
import com.aizz.mindmingle.domain.dos.UserRoleDO;
import com.aizz.mindmingle.domain.dto.MenuDTO;
import com.aizz.mindmingle.domain.vo.MenuVO;
import com.aizz.mindmingle.persistence.mapper.MenuMapper;
import com.aizz.mindmingle.persistence.mapper.MenuPermissionMapper;
import com.aizz.mindmingle.persistence.mapper.PermissionMapper;
import com.aizz.mindmingle.persistence.mapper.RolePermissionMapper;
import com.aizz.mindmingle.persistence.mapper.UserMapper;
import com.aizz.mindmingle.persistence.mapper.UserRoleMapper;
import com.aizz.mindmingle.security.Access;
import com.aizz.mindmingle.service.MenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单服务实现类
 */
@Service
public class MenuServiceImpl implements MenuService {

    private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MenuPermissionMapper menuPermissionMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Response<MenuVO> createMenu(MenuDTO dto) {
        try {
            // 获取当前用户的租户ID (可以为null，系统菜单不属于任何租户)
            Long tenantId = null;
            if (Access.userId() != null) {
                UserDO currentUser = userMapper.selectById(Access.userId());
                if (currentUser != null) {
                    tenantId = currentUser.getTenantId();
                }
            }

            // 验证菜单编码唯一性
            LambdaQueryWrapper<MenuDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MenuDO::getMenuCode, dto.menuCode);
            Long count = menuMapper.selectCount(queryWrapper);
            if (count > 0) {
                return Response.error(ResponseCode.BAD_REQUEST, "菜单编码已存在");
            }

            MenuDO menuDO = new MenuDO();
            menuDO.setTenantId(tenantId);
            menuDO.setMenuName(dto.menuName);
            menuDO.setMenuCode(dto.menuCode);
            menuDO.setMenuType(dto.menuType);
            menuDO.setParentId(dto.parentId != null ? dto.parentId : 0L);
            menuDO.setPath(dto.path);
            menuDO.setComponent(dto.component);
            menuDO.setIcon(dto.icon);
            menuDO.setSortOrder(dto.sortOrder != null ? dto.sortOrder : 0);
            menuDO.setIsVisible(dto.isVisible != null ? dto.isVisible : 1);
            menuDO.setIsCache(0);
            menuDO.setIsFrame(0);
            menuDO.setStatus(dto.status != null ? dto.status : 1);
            menuDO.setCreatedAt(new Date());
            menuDO.setUpdatedAt(new Date());

            if (Access.userId() != null) {
                menuDO.setCreatedBy(Access.userId());
                menuDO.setUpdatedBy(Access.userId());
            }

            menuMapper.insert(menuDO);

            return Response.success(convertToVO(menuDO));
        } catch (Exception e) {
            log.error("Error in createMenu", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<MenuVO> updateMenu(Long id, MenuDTO dto) {
        try {
            MenuDO menuDO = menuMapper.selectById(id);
            if (menuDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "菜单不存在");
            }

            // 验证菜单编码唯一性
            if (StrUtil.isNotBlank(dto.menuCode) && !dto.menuCode.equals(menuDO.getMenuCode())) {
                LambdaQueryWrapper<MenuDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(MenuDO::getMenuCode, dto.menuCode);
                queryWrapper.ne(MenuDO::getId, id);
                Long count = menuMapper.selectCount(queryWrapper);
                if (count > 0) {
                    return Response.error(ResponseCode.BAD_REQUEST, "菜单编码已存在");
                }
            }

            if (StrUtil.isNotBlank(dto.menuName)) {
                menuDO.setMenuName(dto.menuName);
            }
            if (StrUtil.isNotBlank(dto.menuCode)) {
                menuDO.setMenuCode(dto.menuCode);
            }
            if (dto.menuType != null) {
                menuDO.setMenuType(dto.menuType);
            }
            if (dto.parentId != null) {
                menuDO.setParentId(dto.parentId);
            }
            if (StrUtil.isNotBlank(dto.path)) {
                menuDO.setPath(dto.path);
            }
            if (StrUtil.isNotBlank(dto.component)) {
                menuDO.setComponent(dto.component);
            }
            if (StrUtil.isNotBlank(dto.icon)) {
                menuDO.setIcon(dto.icon);
            }
            if (dto.sortOrder != null) {
                menuDO.setSortOrder(dto.sortOrder);
            }
            if (dto.isVisible != null) {
                menuDO.setIsVisible(dto.isVisible);
            }
            if (dto.status != null) {
                menuDO.setStatus(dto.status);
            }

            menuDO.setUpdatedAt(new Date());
            if (Access.userId() != null) {
                menuDO.setUpdatedBy(Access.userId());
            }

            menuMapper.updateById(menuDO);

            return Response.success(convertToVO(menuDO));
        } catch (Exception e) {
            log.error("Error in updateMenu", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<Void> deleteMenu(Long id) {
        try {
            MenuDO menuDO = menuMapper.selectById(id);
            if (menuDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "菜单不存在");
            }

            // 检查是否有子菜单
            LambdaQueryWrapper<MenuDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MenuDO::getParentId, id);
            Long childCount = menuMapper.selectCount(queryWrapper);
            if (childCount > 0) {
                return Response.error(ResponseCode.BAD_REQUEST, "存在子菜单，不可删除");
            }

            // MyBatis-Plus的@TableLogic会自动处理软删除
            menuMapper.deleteById(id);

            return Response.success();
        } catch (Exception e) {
            log.error("Error in deleteMenu", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<MenuVO> getMenuById(Long id) {
        try {
            MenuDO menuDO = menuMapper.selectById(id);
            if (menuDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "菜单不存在");
            }

            MenuVO menuVO = convertToVO(menuDO);

            // 查询菜单关联的权限
            LambdaQueryWrapper<MenuPermissionDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MenuPermissionDO::getMenuId, id);
            List<MenuPermissionDO> menuPermissions = menuPermissionMapper.selectList(queryWrapper);

            if (CollUtil.isNotEmpty(menuPermissions)) {
                List<Long> permissionIds = menuPermissions.stream()
                    .map(MenuPermissionDO::getPermissionId)
                    .collect(Collectors.toList());

                List<PermissionDO> permissions = permissionMapper.selectBatchIds(permissionIds);
                List<String> permissionCodes = permissions.stream()
                    .map(PermissionDO::getPermissionCode)
                    .collect(Collectors.toList());

                menuVO.setPermissions(permissionCodes);
            }

            return Response.success(menuVO);
        } catch (Exception e) {
            log.error("Error in getMenuById", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<List<MenuVO>> listAllMenus() {
        try {
            LambdaQueryWrapper<MenuDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.orderByAsc(MenuDO::getSortOrder);
            queryWrapper.orderByAsc(MenuDO::getId);

            List<MenuDO> allMenus = menuMapper.selectList(queryWrapper);
            List<MenuVO> menuTree = buildMenuTree(allMenus);

            return Response.success(menuTree);
        } catch (Exception e) {
            log.error("Error in listAllMenus", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<List<MenuVO>> getUserMenus(Long userId) {
        try {
            // 查询用户的角色
            LambdaQueryWrapper<UserRoleDO> userRoleWrapper = new LambdaQueryWrapper<>();
            userRoleWrapper.eq(UserRoleDO::getUserId, userId);
            List<UserRoleDO> userRoles = userRoleMapper.selectList(userRoleWrapper);

            if (CollUtil.isEmpty(userRoles)) {
                return Response.success(new ArrayList<>());
            }

            // 查询角色的权限
            List<Long> roleIds = userRoles.stream()
                .map(UserRoleDO::getRoleId)
                .collect(Collectors.toList());

            LambdaQueryWrapper<RolePermissionDO> rolePermissionWrapper = new LambdaQueryWrapper<>();
            rolePermissionWrapper.in(RolePermissionDO::getRoleId, roleIds);
            List<RolePermissionDO> rolePermissions = rolePermissionMapper.selectList(rolePermissionWrapper);

            if (CollUtil.isEmpty(rolePermissions)) {
                return Response.success(new ArrayList<>());
            }

            // 查询权限对应的菜单
            List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermissionDO::getPermissionId)
                .distinct()
                .collect(Collectors.toList());

            LambdaQueryWrapper<MenuPermissionDO> menuPermissionWrapper = new LambdaQueryWrapper<>();
            menuPermissionWrapper.in(MenuPermissionDO::getPermissionId, permissionIds);
            List<MenuPermissionDO> menuPermissions = menuPermissionMapper.selectList(menuPermissionWrapper);

            if (CollUtil.isEmpty(menuPermissions)) {
                return Response.success(new ArrayList<>());
            }

            List<Long> menuIds = menuPermissions.stream()
                .map(MenuPermissionDO::getMenuId)
                .distinct()
                .collect(Collectors.toList());

            // 查询菜单详情
            List<MenuDO> menus = menuMapper.selectBatchIds(menuIds);
            if (CollUtil.isEmpty(menus)) {
                return Response.success(new ArrayList<>());
            }

            // 过滤可见且启用的菜单
            menus = menus.stream()
                .filter(menu -> menu.getIsVisible() == 1 && menu.getStatus() == 1)
                .collect(Collectors.toList());

            // 构建树形结构
            List<MenuVO> menuTree = buildMenuTree(menus);

            return Response.success(menuTree);
        } catch (Exception e) {
            log.error("Error in getUserMenus", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<Void> assignPermissions(Long menuId, List<Long> permissionIds) {
        try {
            MenuDO menuDO = menuMapper.selectById(menuId);
            if (menuDO == null) {
                return Response.error(ResponseCode.BAD_REQUEST, "菜单不存在");
            }

            // 删除旧的菜单权限关联
            LambdaQueryWrapper<MenuPermissionDO> deleteWrapper = new LambdaQueryWrapper<>();
            deleteWrapper.eq(MenuPermissionDO::getMenuId, menuId);
            menuPermissionMapper.delete(deleteWrapper);

            // 插入新的菜单权限关联
            if (CollUtil.isNotEmpty(permissionIds)) {
                for (Long permissionId : permissionIds) {
                    MenuPermissionDO menuPermissionDO = new MenuPermissionDO();
                    menuPermissionDO.setMenuId(menuId);
                    menuPermissionDO.setPermissionId(permissionId);
                    menuPermissionDO.setCreatedAt(new Date());
                    if (Access.userId() != null) {
                        menuPermissionDO.setCreatedBy(Access.userId());
                    }
                    menuPermissionMapper.insert(menuPermissionDO);
                }
            }

            return Response.success();
        } catch (Exception e) {
            log.error("Error in assignPermissions", e);
            return Response.error(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 构建菜单树
     */
    private List<MenuVO> buildMenuTree(List<MenuDO> allMenus) {
        if (CollUtil.isEmpty(allMenus)) {
            return new ArrayList<>();
        }

        // 转换为VO
        List<MenuVO> allMenuVOs = allMenus.stream()
            .map(this::convertToVO)
            .collect(Collectors.toList());

        // 按parentId分组
        Map<Long, List<MenuVO>> menuMap = new HashMap<>();
        for (MenuVO menu : allMenuVOs) {
            Long parentId = menu.getParentId() != null ? menu.getParentId() : 0L;
            menuMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(menu);
        }

        // 构建树形结构
        List<MenuVO> rootMenus = menuMap.getOrDefault(0L, new ArrayList<>());
        for (MenuVO rootMenu : rootMenus) {
            buildChildren(rootMenu, menuMap);
        }

        return rootMenus;
    }

    /**
     * 递归构建子菜单
     */
    private void buildChildren(MenuVO parent, Map<Long, List<MenuVO>> menuMap) {
        List<MenuVO> children = menuMap.getOrDefault(parent.getId(), new ArrayList<>());
        parent.setChildren(children);

        for (MenuVO child : children) {
            buildChildren(child, menuMap);
        }
    }

    /**
     * DO转VO
     */
    private MenuVO convertToVO(MenuDO menuDO) {
        if (menuDO == null) {
            return null;
        }

        MenuVO vo = new MenuVO();
        vo.setId(menuDO.getId());
        vo.setMenuName(menuDO.getMenuName());
        vo.setMenuCode(menuDO.getMenuCode());
        vo.setMenuType(menuDO.getMenuType());
        vo.setParentId(menuDO.getParentId());
        vo.setPath(menuDO.getPath());
        vo.setComponent(menuDO.getComponent());
        vo.setIcon(menuDO.getIcon());
        vo.setSortOrder(menuDO.getSortOrder());
        vo.setIsVisible(menuDO.getIsVisible());
        vo.setStatus(menuDO.getStatus());
        vo.setChildren(new ArrayList<>());
        vo.setPermissions(new ArrayList<>());

        return vo;
    }
}
