package com.gitee.pro.mvc.controller;

import com.gitee.pro.entity.Menu;
import com.gitee.pro.service.api.MenuService;
import com.gitee.pro.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 因为发送 JSON 请求都需要在每个方法上添加 @ResponseBody
 * 所以可以直接在类上添加一个 @ResponseBody
 * 而 @Controller 和 @ResponseBody 又可以合并成 @RestController
 */
// @Controller
// @ResponseBody
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

//    @ResponseBody
    @RequestMapping("/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTree() {
        // 查询全部的 Menu 对象
        List<Menu> menuList = menuService.getAll();

        // 声明一个变量用来存储找到的根节点
        Menu root = null;

        // 这里使用了嵌套的遍历循环，会造成性能的极大消耗，所以在这里使用 map
        /*// 遍历 menuList
        for (Menu menu : menuList) {
            // 获取当前 menu 对象的 pid 属性值
            Integer pid = menu.getPid();
            // 检查 pid 是否为 null
            if (pid == null) {
                // 把当前正在遍历的这个 menu 对象赋值给 root
                root = menu;
                // 停止本次循环，继续执行下一次循环
                continue;
            }
            // 如果 pid 不为 null ，说明当前节点有父节点，找到父节点就可以进行组装，建立父子关系
            for (Menu maybeFather : menuList) {
                // 获取 maybeFather 的 id 属性
                Integer id = maybeFather.getId();
                // 将子节点的 pid 和疑似父节点的 id 进行比较
                if (Objects.equals(pid, id)) {
                    // 如果确认关系，将子节点存入父节点的 children 集合
                    maybeFather.getChildren().add(menu);
                    // 找到即可停止运行循环
                    break;
                }
            }
        }*/

        // 创建 Map 对象用来存储 id 和 Menu 对象的对应关系便于查找父节点
        Map<Integer, Menu> menuMap = new HashMap<>();

        // 遍历 menuList 填充 menuMap
        for (Menu menu : menuList) {
            menuMap.put(menu.getId(), menu);
        }

        // 再次遍历 menuList 查找根节点、组装父子节点
        for (Menu menu : menuList) {
            // 获取当前 menu 对象的 pid 属性值
            Integer pid = menu.getPid();
            // 如果 pid 为 null ，判定为根节点
            if (pid == null) {
                // 把当前正在遍历的这个 menu 对象赋值给 root
                root = menu;
                // 如果当前节点是根节点，那么肯定没有父节点，不必继续执行
                continue;
            }
            // 如果 pid 不为 null ，说明当前节点有父节点，那么可以根据 pid 到 menuMap 中查找对应的 Menu 对象
            Menu father = menuMap.get(pid);
            // 将当前节点存入父节点的 children 集合
            father.getChildren().add(menu);
        }

        // 将组装的树形结构（也就是根节点对象）返回给浏览器
        return ResultEntity.successWithData(root);
    }

}
