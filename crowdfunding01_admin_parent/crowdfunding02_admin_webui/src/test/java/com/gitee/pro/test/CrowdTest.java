package com.gitee.pro.test;

import com.gitee.pro.entity.Admin;
import com.gitee.pro.mapper.AdminMapper;
import com.gitee.pro.service.api.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * junit4 测试
 * 使用 @RunWith(SpringJunit4ClassRunner.class) 表示使用 junit4 测试
 * 使用 @ContextConfiguration("classpath:spring-persist-mybatis.xml") 表示测试相关的配置文件所在位置
 * 如果没有设置值的话，会默认去从当前测试类同级的目录中去找当前测试类名-context.xml 配置文件
 * junit5 测试
 * 使用 @SpringJUnitConfig 是 junit4 两个注解的复合注解
 */
@SpringJUnitConfig(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    /**
     * 测试获取数据库连接
     *
     * @throws SQLException 获取数据库连接抛出的异常
     */
    @Test
    public void testGetConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println("connection = " + connection);
    }

    /**
     * 测试装配 XxxMapper 接口并通过这个接口添加用户
     */
    @Test
    public void testInsertAdmin() {
        Admin admin = new Admin(null, "adminA", "123123", "管理员A", "adminA@qq.com", null);
        int count = adminMapper.insert(admin);
        System.out.println("count = " + count);
    }

    /**
     * 测试事务
     */
    @Test
    public void testTx() {
        Admin admin = new Admin(null, "adminB", "123123", "管理员B", "adminB@qq.com", null);
        adminService.saveAdmin(admin);
    }

    /**
     * 向数据库 t_admin 添加 50 条数据
     */
    @Test
    public void insertAdmin() {
        for (int i = 1; i <= 50; i++) {
            Admin admin = new Admin(null, "admin" + i, "4297F44B13955235245B2497399D7A93", i + "号管理员", "admin" + i + "@qq.com", null);
            adminService.saveAdmin(admin);
        }
    }
}
