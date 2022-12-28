package com.gitee.pro.mvc.controller;

import com.gitee.pro.entity.Admin;
import com.gitee.pro.service.api.AdminService;
import com.gitee.pro.util.CrowdConstant;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 登录功能
     *
     * @param loginAcct 提交的登陆账号
     * @param userPwd   提交的登录密码
     * @param session   session 域
     * @return 登录成功后重定向到管理员主页
     */
    @RequestMapping("/do/login.html")
    public String doLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPwd") String userPwd,
            HttpSession session
    ) {
        // 调用 Service 方法执行登录检查
        // 这个方法如果能够返回 admin 对象说明登陆成功，如果账号、密码不正确则会抛出异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPwd);
        // 将登录成功返回的 admin 对象存入 Session 域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/to/main.html";
    }

    /**
     * 退出登陆状态功能
     *
     * @param session 通过对 session 域的强制失效，自动取消登录状态
     * @return 退出登陆后重定向到管理员登录页面
     */
    @RequestMapping("/do/logout.html")
    public String doLogout(HttpSession session) {
        // 强制 Session 失效
        session.invalidate();
        return "redirect:/admin/to/login.html";
    }

    /**
     * 分页功能
     *
     * @param keyword  查询的关键字
     * @param pageNum  分页的页码
     * @param pageSize 每页的条数
     * @param modelMap 通过 modelMap 共享数据
     * @return 跳转到分页页面
     */
    @RequestMapping("/get/page.html")
    public String getPageInfo(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            ModelMap modelMap
    ) {
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        // 将关键字传回，用于表单回显
        modelMap.addAttribute("keyword", keyword);
        return "admin-page";
    }

    /**
     * 删除单个用户功能
     * 使用 restful 风格发送的 delete 请求
     *
     * @param adminId 要删除的用户 ID
     * @param pageNum 要删除的用户所在页的页码
     * @param keyword 要删除的用户所在分页的关键字
     * @return 重定向到之前分页
     */
    @DeleteMapping("/delete/{adminId}/{pageNum}/{keyword}")
    public String deleteAdmin(
            @PathVariable("adminId") Integer adminId,
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("keyword") String keyword
    ) {
        // 执行删除
        adminService.deleteAdminById(adminId);
        // 方案一：直接转发到 amdin-page.html ，会无法显示分页数据
//        return "admin-page";
        // 方案二：转发到 /admin/get/page ，一旦刷新页面会重复执行删除浪费性能
//        return "forward:/admin/get/page";
        // 方案三：重定向到 /admin/get/page
        // 为了保持原本所在的页面和查询关键字，再附加 pageNum 和 keyword 两个请求参数
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    /**
     * 添加用户功能
     * 添加的用户会保存在管理员列表的最后一页，所以重定向到列表的最后一页
     *
     * @param admin 要添加的用户信息
     * @return 重定向到添加的用户在管理员列表的所在位置
     */
    @PostMapping("/save")
    public String saveAdmin(Admin admin) {
        adminService.saveAdmin(admin);
        return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }

    /**
     * 跳转到管理员信息编辑页面
     *
     * @param adminId  修改的管理员 id
     * @param modelMap 通过 modelMap 共享数据
     * @return 跳转到管理员信息编辑页面
     */
    @RequestMapping("/to/page/edit.html")
    public String toEditPage(
            @RequestParam("adminId") Integer adminId,
            ModelMap modelMap
    ) {
        // 根据 adminId 查询 admin 对象
        Admin admin = adminService.getAdminById(adminId);
        // 将 Admin 对象存入模型
        modelMap.addAttribute("admin", admin);
        return "admin-edit";
    }

    /**
     * 更新功能
     *
     * @param admin 表单提交的管理员更新信息
     * @return 重定向到管理员列表
     */
    @RequestMapping("/update")
    public String updateAdmin(Admin admin) {
        adminService.updateAdmin(admin);
        return "redirect:/admin/get/page.html";
    }
}
