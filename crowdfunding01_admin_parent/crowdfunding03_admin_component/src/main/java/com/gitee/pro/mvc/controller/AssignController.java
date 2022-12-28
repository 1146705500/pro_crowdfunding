package com.gitee.pro.mvc.controller;

import com.gitee.pro.entity.Auth;
import com.gitee.pro.entity.Role;
import com.gitee.pro.service.api.AdminService;
import com.gitee.pro.service.api.AuthService;
import com.gitee.pro.service.api.RoleService;
import com.gitee.pro.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/assign")
public class AssignController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    /**
     * 跳转到分配角色页面
     *
     * @param adminId  要分配角色的用户 id
     * @param modelMap 通过 modelMap 共享数据
     * @return 跳转的页面
     */
    @RequestMapping("/to/assign/role/page.html")
    public String toAssignRolePage(
            @RequestParam("adminId") Integer adminId,
            ModelMap modelMap
    ) {
        // 查询已分配角色
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        // 查询未分配角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);
        // 存入模型（本质上其实是：request.setAttribute("attrName", attrValue);）
        modelMap.addAttribute("adminId", adminId);
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);
        return "assign-role";
    }

    /**
     * 保存用户与角色的关联关系
     *
     * @param adminId    要分配角色的用户 id
     * @param roleIdList 要关联的角色 id 集合
     * @return 重定向到分配角色页面
     */
    @RequestMapping("/do/assign/role.html")
    public String saveAdminRoleRelationship(
            @RequestParam("adminId") Integer adminId,
            // 允许用户在页面上取消已分配角色再提交表单，所以可以不提供 roleIdList 请求参数
            // 设置 required = false 表示这个参数不是必要的
            @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList
    ) {
        adminService.saveAdminRoleRelationship(adminId, roleIdList);
        return "redirect:/assign/to/assign/role/page.html?adminId=" + adminId;
    }

    /**
     * 获取所有的权限
     *
     * @return 响应体
     */
    @ResponseBody
    @RequestMapping("/get/all/auth.json")
    public ResultEntity<List<Auth>> getAllAuth() {
        List<Auth> authList = authService.getAll();
        return ResultEntity.successWithData(authList);
    }

    /**
     * 通过角色 id 获取角色具备的权限 id
     *
     * @param roleId 角色 id
     * @return 角色具备的权限的 id 的集合
     */
    @ResponseBody
    @RequestMapping("/get/assign/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(
            @RequestParam("roleId") Integer roleId
    ) {
        List<Integer> authList = authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(authList);
    }

    /**
     * 保存角色与权限的关联关系
     *
     * @param map 角色 id 集合，权限 id 集合
     * @return 响应体
     */
    @ResponseBody
    @RequestMapping("/do/assign/auth.json")
    public ResultEntity<String> saveRoleAuthRelationship(
            @RequestBody Map<String, List<Integer>> map
    ) {
        authService.saveRoleAuthRelationship(map);
        return ResultEntity.successWithoutData();
    }
}
