package com.gitee.pro.service.api;

import com.gitee.pro.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {

    /**
     * 分页功能
     *
     * @param keyword  查询的关键字
     * @param pageNum  页码
     * @param pageSize 每页的条数
     * @return 分页的管理员信息
     */
    PageInfo<Role> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 添加角色
     *
     * @param role 要添加的角色信息
     */
    void saveRole(Role role);

    /**
     * 更新角色
     *
     * @param role 要更新的角色信息
     */
    void updateRole(Role role);

    /**
     * 删除角色
     * 通过 roleIdList 中的角色 id 删除对应的角色
     *
     * @param roleIdList 要删除的角色的 id 集合
     */
    void removeRole(List<Integer> roleIdList);

    /**
     * 获取已分配的角色
     *
     * @param adminId 用户的 id
     * @return 已分配角色的集合
     */
    List<Role> getAssignedRole(Integer adminId);

    /**
     * 获取未分配的角色
     *
     * @param adminId 用户的 id
     * @return 未分配角色的集合
     */
    List<Role> getUnAssignedRole(Integer adminId);
}
