package com.huijiewei.agile.app.admin.adapter.persistence;

import com.huijiewei.agile.app.admin.adapter.persistence.entity.AdminGroupPermission;
import com.huijiewei.agile.app.admin.adapter.persistence.repository.JpaAdminGroupPermissionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huijiewei
 */

@Component
public class JpaAdminGroupCacheAdapter {
    public static final String ADMIN_GROUP_PERMISSIONS_CACHE_KEY = "admin-group-permissions";
    public static final String ADMIN_GROUP_MENUS_CACHE_KEY = "admin-group-menus";

    private final JpaAdminGroupPermissionRepository jpaAdminGroupPermissionRepository;

    public JpaAdminGroupCacheAdapter(JpaAdminGroupPermissionRepository jpaAdminGroupPermissionRepository) {
        this.jpaAdminGroupPermissionRepository = jpaAdminGroupPermissionRepository;
    }

    @Cacheable(cacheNames = ADMIN_GROUP_PERMISSIONS_CACHE_KEY, key = "#id")
    public List<String> getPermissions(Integer id) {
        return this.jpaAdminGroupPermissionRepository
                .findAllByAdminGroupId(id)
                .stream()
                .map(AdminGroupPermission::getActionId)
                .collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = {ADMIN_GROUP_MENUS_CACHE_KEY, ADMIN_GROUP_PERMISSIONS_CACHE_KEY}, key = "#id")
    public void updatePermissions(Integer id, List<String> permissions, Boolean delete) {
        if (delete) {
            this.jpaAdminGroupPermissionRepository.deleteByAdminGroupId(id);
        }

        if (permissions == null || permissions.isEmpty()) {
            return;
        }

        List<AdminGroupPermission> adminGroupPermissions = new ArrayList<>();

        for (String actionId : permissions) {
            AdminGroupPermission permission = new AdminGroupPermission();
            permission.setActionId(actionId);
            permission.setAdminGroupId(id);

            adminGroupPermissions.add(permission);
        }

        this.jpaAdminGroupPermissionRepository.batchInsert(adminGroupPermissions);
    }
}
