package com.gitee.pro.service.impl;

import com.gitee.pro.entity.Role;
import com.gitee.pro.entity.RoleExample;
import com.gitee.pro.mapper.RoleMapper;
import com.gitee.pro.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 调用 PageHelper 的静态方法开启分页功能
        PageHelper.startPage(pageNum, pageSize);
        // 执行查询
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);
        // 封装到 PageInfo 对象中
        return new PageInfo<>(roleList, 5);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void removeRole(List<Integer> roleIdList) {
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        // delete from t_role where id in (5,8,12)
        criteria.andIdIn(roleIdList);
        roleMapper.deleteByExample(example);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.selectAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer adminId) {
        return roleMapper.selectUnAssignedRole(adminId);
    }
}
