package com.gitee.pro.mapper;

import com.gitee.pro.entity.Admin;
import com.gitee.pro.entity.Role;
import com.gitee.pro.entity.RoleExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Wed Dec 14 23:03:57 CST 2022
     */
    long countByExample(RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Wed Dec 14 23:03:57 CST 2022
     */
    int deleteByExample(RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Wed Dec 14 23:03:57 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Wed Dec 14 23:03:57 CST 2022
     */
    int insert(Role record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Wed Dec 14 23:03:57 CST 2022
     */
    int insertSelective(Role record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Wed Dec 14 23:03:57 CST 2022
     */
    List<Role> selectByExample(RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Wed Dec 14 23:03:57 CST 2022
     */
    Role selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Wed Dec 14 23:03:57 CST 2022
     */
    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Wed Dec 14 23:03:57 CST 2022
     */
    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Wed Dec 14 23:03:57 CST 2022
     */
    int updateByPrimaryKeySelective(Role record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Wed Dec 14 23:03:57 CST 2022
     */
    int updateByPrimaryKey(Role record);

    /**
     * 自定义
     * 用于关键字查询的接口
     *
     * @param keyword 查询的关键字
     * @return 含有关键字的 role
     */
    List<Role> selectRoleByKeyword(String keyword);

    /**
     * 自定义
     * 用于获取用户已分配的角色
     *
     * @param adminId 用户 id
     * @return 已分配的角色集合
     */
    List<Role> selectAssignedRole(Integer adminId);

    /**
     * 自定义
     * 用于获取用户未分配的角色
     *
     * @param adminId 用户 id
     * @return 未分配的角色集合
     */
    List<Role> selectUnAssignedRole(Integer adminId);
}