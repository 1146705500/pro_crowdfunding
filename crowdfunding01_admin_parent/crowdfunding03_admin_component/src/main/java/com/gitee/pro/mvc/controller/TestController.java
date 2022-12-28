package com.gitee.pro.mvc.controller;

import com.gitee.pro.service.api.AdminService;
import com.gitee.pro.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private AdminService adminService;

    /**
     * @param request 使用 ServletAPI 向 request 域对象共享数据
     * @return 跳转的页面
     */
    @RequestMapping("/ServletAPI")
    public String testServletAPI(HttpServletRequest request) {
        request.setAttribute("testScope", "Hello, servletAPI!");
        return "success";
    }

    /**
     * @return ModelAndView 有 Model 和 View 的功能
     * Model主要用于向请求域共享数据
     * View主要用于设置视图，实现页面跳转
     */
    @RequestMapping("/ModelAndView")
    public ModelAndView testModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        // 向请求域共享数据
        modelAndView.addObject("testScope", "Hello, ModelAndView!");
        // 设置视图，实现页面跳转
        modelAndView.setViewName("success");
        return modelAndView;
    }

    /**
     * @param model 使用 Model 向 request 域对象共享数据
     * @return 跳转的页面
     */
    @RequestMapping("/Model")
    public String testModel(Model model) {
        model.addAttribute("testScope", "Hello, Model!");
        return "success";
    }

    /**
     * @param map 使用 map 向 request 域对象共享数据
     * @return 跳转的页面
     */
    @RequestMapping("/Map")
    public String testMap(Map<String, Object> map) {
        map.put("testScope", "Hello, Map!");
        return "success";
    }

    /**
     * @param modelMap 使用 ModelMap 向 request 域对象共享数据
     * @return 跳转的页面
     */
    @RequestMapping("/ModelMap")
    public String testModelMap(ModelMap modelMap) {
        modelMap.addAttribute("testScope", "Hello, ModelMap!");
        return "success";
    }

    /**
     * @param session 向session域共享数据
     * @return 跳转的页面
     */
    @RequestMapping("/session")
    public String testSession(HttpSession session) {
        session.setAttribute("testSessionScope", "hello,session");
        return "success";
    }

    /**
     * @param session 向application域共享数据
     * @return 跳转的页面
     */
    @RequestMapping("/application")
    public String testApplication(HttpSession session) {
        ServletContext application = session.getServletContext();
        application.setAttribute("testApplicationScope", "hello,application");
        return "success";
    }

    /**
     * 测试基于 XML 的异常映射
     *
     * @return 未发生异常时跳转的页面
     */
    @RequestMapping("/exceptionMapperByXML")
    public String testExceptionMapperByXML() {
        System.out.println(10 / 0);
        return "success";
    }

    /**
     * 测试基于注解的异常映射
     *
     * @return 未发生异常时跳转的页面
     */
    @RequestMapping("/exceptionMapperByAnno")
    public String testExceptionMapperByAnno() {
        String s = null;
        System.out.println(s.length());
        return "success";
    }

    /**
     * 测试crowdUtil工具类方法
     *
     * @param modelMap 向 request 域共享数据
     * @param request  请求
     * @return 跳转的页面
     */
    @RequestMapping("/crowdUtil")
    public String testCrowdUtil(ModelMap modelMap, HttpServletRequest request) {
        boolean type = CrowdUtil.judgeRequestType(request);
        System.out.println("type = " + type);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/ajax/async")
    public String testAjaxAsync() throws InterruptedException {
        Thread.sleep(2000);
        return "success";
    }

}
