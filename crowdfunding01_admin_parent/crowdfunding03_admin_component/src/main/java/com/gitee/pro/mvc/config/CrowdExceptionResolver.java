package com.gitee.pro.mvc.config;

import com.gitee.pro.exception.LoginAcctAlreadyInUseForSaveException;
import com.gitee.pro.exception.LoginAcctAlreadyInUseForUpdateException;
import com.gitee.pro.exception.LoginFailedException;
import com.gitee.pro.util.CrowdConstant;
import com.gitee.pro.util.CrowdUtil;
import com.gitee.pro.util.ResultEntity;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ControllerAdvice 表示当前类是一个基于注解的异常处理器类
 */
@ControllerAdvice
public class CrowdExceptionResolver {

    /**
     * 登录失败异常处理方法
     *
     * @param exception LoginFailedException 自定义的异常
     * @param request   当前请求对象
     * @param response  当前响应对象
     * @return 模型与视图
     * @throws IOException IO
     */
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(LoginFailedException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 添加管理员信息时，管理员账号重复异常处理方法
     *
     * @param exception LoginAcctAlreadyInUseForUpdateException 自定义的异常
     * @param request 当前请求对象
     * @param response 当前响应对象
     * @return 模型与视图
     * @throws IOException IO
     */
    @ExceptionHandler(value = LoginAcctAlreadyInUseForSaveException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException(LoginAcctAlreadyInUseForSaveException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin-add";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 添加管理员信息时，管理员账号重复异常处理方法
     *
     * @param exception LoginAcctAlreadyInUseForUpdateException 自定义的异常
     * @param request 当前请求对象
     * @param response 当前响应对象
     * @return 模型与视图
     * @throws IOException IO
     */
    @ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseForUpdateException(LoginAcctAlreadyInUseForUpdateException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 空指针异常处理方法
     *
     * @param exception NullPointerException
     * @param request   当前请求对象
     * @param response  当前响应对象
     * @return 模型与视图
     * @throws IOException IO
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullPointerException(NullPointerException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 通用方法
     *
     * @param viewName  处理完异常后跳转的页面
     * @param exception 实际捕捉到的异常
     * @param request   当前请求对象
     * @param response  当前响应对象
     * @return 跳转的模型与视图
     * @throws IOException 将 JSON 写入响应体时抛出 IO 异常
     */
    private ModelAndView commonResolve(String viewName, Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 判断当前请求类型
        boolean judgeRequestType = CrowdUtil.judgeRequestType(request);

        // 如果是 Ajax 请求
        if (judgeRequestType) {
            // 创建 ResultEntity 对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            // 创建 Gson 对象
            Gson gson = new Gson();
            // 将 ResultEntity 对象转换为 JSON 字符
            String json = gson.toJson(resultEntity);
            // 将 JSON 字符串作为响应体返回给浏览器
            response.getWriter().write(json);
            // 由于上面已经通过原生的 response 对象返回了响应，所以不提供 ModelAndView 对象
            return null;
        }

        // 如果不是 Ajax 请求，则创建 ModelAndView 对象
        ModelAndView modelAndView = new ModelAndView();
        // 将 Exception 对象存入模型
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
        // 设置对应的视图名称
        modelAndView.setViewName(viewName);
        // 返回 modelAndView 对象
        return modelAndView;
    }

}
