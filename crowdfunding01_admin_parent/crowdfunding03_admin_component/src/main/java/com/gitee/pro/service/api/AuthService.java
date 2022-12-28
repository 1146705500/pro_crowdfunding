package com.gitee.pro.service.api;

import com.gitee.pro.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {

    /**
     * 获取所有权限
     *
     * @return 所有权限的集合
     */
    List<Auth> getAll();

    /**
     * 通过角色 id 获取角色具备的权限 id
     *
     * @param roleId 角色 id
     * @return 角色具备的权限 id 的集合
     */
    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    /**
     * 保存角色与权限的关联关系
     *
     * @param map [{"roleIdList", roleIdList}, {"authIdList", authIdList}]
     */
    void saveRoleAuthRelationship(Map<String, List<Integer>> map);
}
