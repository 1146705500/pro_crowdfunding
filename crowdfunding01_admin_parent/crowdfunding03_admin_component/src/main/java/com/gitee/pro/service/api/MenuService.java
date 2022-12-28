package com.gitee.pro.service.api;

import com.gitee.pro.entity.Menu;

import java.util.List;

public interface MenuService {
    /**
     * 获取全部节点
     *
     * @return 全部节点集合
     */
    List<Menu> getAll();
}
