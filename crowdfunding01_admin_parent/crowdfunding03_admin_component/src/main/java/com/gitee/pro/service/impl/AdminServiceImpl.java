package com.gitee.pro.service.impl;

import com.gitee.pro.entity.Admin;
import com.gitee.pro.entity.AdminExample;
import com.gitee.pro.exception.LoginAcctAlreadyInUseForSaveException;
import com.gitee.pro.exception.LoginAcctAlreadyInUseForUpdateException;
import com.gitee.pro.exception.LoginFailedException;
import com.gitee.pro.mapper.AdminMapper;
import com.gitee.pro.service.api.AdminService;
import com.gitee.pro.util.CrowdConstant;
import com.gitee.pro.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {
        // 密码加密
        String userPswd = admin.getUserPswd();
        userPswd = CrowdUtil.md5(userPswd);
        admin.setUserPswd(userPswd);
        // 生成创建时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(date);
        admin.setCreateTime(createTime);
        // 执行保存
        try {
            adminMapper.insert(admin);
        } catch (Exception exception) {
            exception.printStackTrace();
            // 账户名重复抛出 DuplicateKeyException 时转换为抛出自定义的 LoginAcctAlreadyInUseForSaveException 和异常信息
            if (exception instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseForSaveException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public List<Admin> getAllAdmin() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public void deleteAdminById(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPwd) {
        // 根据登陆账号查询 Admin 对象
        // 创建 AdminExample 对象
        AdminExample adminExample = new AdminExample();
        // 创建 Criteria 对象
        AdminExample.Criteria criteria = adminExample.createCriteria();
        // 在 Criteria 对象中封装查询条件
        criteria.andLoginEqualTo(loginAcct);
        // 调用 AdminMapper 的方法执行查询
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        // 判断 Admin 对象是否为 null
        if (adminList == null || adminList.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (adminList.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        // 如果 Admin 对象为 null 则抛出异常
        Admin admin = adminList.get(0);
        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 如果 Admin 对象不为 null 则将数据库密码从 Admin 对象中取出
        String userPwdDB = admin.getUserPswd();
        // 将表单提交的明文密码进行加密
        String userPwdForm = CrowdUtil.md5(userPwd);
        // 对密码进行比较
        if (!Objects.equals(userPwdDB, userPwdForm)) {
            // 如果比较结果是不一致则抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 如果一致则返回 Admin 对象
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 调用 PageHelper 的静态方法开启分页功能
        PageHelper.startPage(pageNum, pageSize);
        // 执行查询
        List<Admin> adminList = adminMapper.selectAdminByKeyword(keyword);
        // 封装到 PageInfo 对象中
        return new PageInfo<>(adminList, 5);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void updateAdmin(Admin admin) {
        try {
            // Selective 表示有选择的更新信息，值为 null 的不更新
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception exception) {
            exception.printStackTrace();
            // 账户名重复抛出 DuplicateKeyException 时转换为抛出自定义的 LoginAcctAlreadyInUseForUpdateException 和异常信息
            if (exception instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        // 根据 adminId 删除旧的关联关系数据
        adminMapper.deleteOldRelationship(adminId);
        // 根据 adminId 和 roleIdList 保存新的关联关系
        if (roleIdList != null && roleIdList.size() > 0) {
            adminMapper.insertNewRelationship(adminId, roleIdList);
        }
    }

}
