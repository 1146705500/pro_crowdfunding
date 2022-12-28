package com.gitee.pro.service.impl;

import com.gitee.pro.entity.Menu;
import com.gitee.pro.mapper.MenuMapper;
import com.gitee.pro.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(null);
    }
}
