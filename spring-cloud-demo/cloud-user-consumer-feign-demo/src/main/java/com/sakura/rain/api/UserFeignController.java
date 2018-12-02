package com.sakura.rain.api;

import com.sakura.rain.entity.User;
import com.sakura.rain.service.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author licunzhi
 * @desc 描述功能
 * @date 2018-12-02
 */
@RestController
public class UserFeignController {

    @Autowired
    private UserClientService service;

    @RequestMapping(value = "/user/get/{id}")
    public User get(@PathVariable("id") Long id)
    {
        return this.service.get(id);
    }

    @RequestMapping(value = "/user/list")
    public List<User> list()
    {
        return this.service.list();
    }

    @RequestMapping(value = "/user/create")
    public Object add(User dept)
    {
        return this.service.add(dept);
    }
}
