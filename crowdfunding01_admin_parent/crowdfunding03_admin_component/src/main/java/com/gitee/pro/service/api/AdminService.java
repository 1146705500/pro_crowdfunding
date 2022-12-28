package com.gitee.pro.service.api;

import com.gitee.pro.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {

    /**
     * 添加管理员用户
     *
     * @param admin 添加的管理员信息
     */
    void saveAdmin(Admin admin);

    /**
     * 获取所有管理员信息
     *
     * @return 管理员列表
     */
    List<Admin> getAllAdmin();

    /**
     * 通过 id 删除管理员
     *
     * @param id 要删除的管理员 id
     */
    void deleteAdminById(Integer id);

    /**
     * 登录功能
     *
     * @param loginAcct 提交的登录账号
     * @param userPwd   提交的登录密码
     * @return 对应的管理员对象
     */
    Admin getAdminByLoginAcct(String loginAcct, String userPwd);

    /**
     * 分页功能
     *
     * @param keyword  查询的关键字
     * @param pageNum  页码
     * @param pageSize 每页的条数
     * @return 分页的管理员信息
     */
    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 通过 id 查询管理员
     *
     * @param adminId 要查询的管理员 id
     * @return 对应的管理员对象
     */
    Admin getAdminById(Integer adminId);

    /**
     * 更新功能
     *
     * @param admin 要更新的管理员信息
     */
    void updateAdmin(Admin admin);

    /**
     * 保存用户与角色的关联关系
     *
     * @param adminId    用户 id
     * @param roleIdList 要关联的角色集合
     */
    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);
}
