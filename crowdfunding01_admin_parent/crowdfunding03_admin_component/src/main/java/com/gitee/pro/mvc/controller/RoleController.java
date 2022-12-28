package com.gitee.pro.mvc.controller;

import com.gitee.pro.entity.Role;
import com.gitee.pro.service.api.RoleService;
import com.gitee.pro.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * ajax 请求
     * 获取分页数据
     *
     * @param keyword  要获取的分页关键字
     * @param pageNum  要获取的分页页数
     * @param pageSize 要获取的分页条数
     * @return 响应体
     */
    @ResponseBody
    @RequestMapping("/get/page.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        // 调用 Service 方法获取分页数据
        // 如果抛出异常，交给异常映射机制处理
        PageInfo<Role> pageInfo = roleService.getPageInfo(keyword, pageNum, pageSize);
        // 封装到 ResultEntity 对象中返回
        return ResultEntity.successWithData(pageInfo);
    }

    /**
     * ajax 请求
     * 添加角色
     *
     * @param role 要添加的角色信息
     * @return 响应体
     */
    @ResponseBody
    @RequestMapping("/save.json")
    public ResultEntity<String> saveRole(Role role) {
        // 执行
        roleService.saveRole(role);
        // 无数据，返回
        return ResultEntity.successWithoutData();
    }

    /**
     * ajax 请求
     * 更新角色
     *
     * @param role 要更新的角色信息
     * @return 响应体
     */
    @ResponseBody
    @RequestMapping("/update.json")
    public ResultEntity<String> updateRole(Role role) {
        // 执行
        roleService.updateRole(role);
        // 无数据，返回
        return ResultEntity.successWithoutData();
    }

    /**
     * ajax 请求
     * 删除角色
     * 通过角色 id 数组批量删除角色
     *
     * @param roleIdArray 要删除的角色 id 数组
     * @return 响应体
     */
    @ResponseBody
    @RequestMapping("/remove.json")
    public ResultEntity<String> removeByRoleIdArray(@RequestBody List<Integer> roleIdArray) {
        // 执行
        roleService.removeRole(roleIdArray);
        // 无数据，返回
        return ResultEntity.successWithoutData();
    }

}