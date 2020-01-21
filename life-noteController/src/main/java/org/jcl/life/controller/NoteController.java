package org.jcl.life.controller;

import com.alibaba.fastjson.JSON;
import org.jcl.life.auth.User;
import org.jcl.life.auth.UserService;
import org.jcl.life.auth.config.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenglei
 */
@RestController
public class NoteController {

    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("/test")
    public String test(ModelMap model) {
        String str = "cl hello";

        System.out.println(model.get("name"));
        System.out.println(model.get("data"));
        return str;
    }

    @RequestMapping(path = "/posttest", method = RequestMethod.POST)
    public String test(User user) {
        System.out.println("Post test");
        System.out.println(user.getUserName());
        User u = new User();
        u.setUserName("hah");
        return JSON.toJSONString(u);
    }

    @RequestMapping("/redist")
    public String redist() {
        Map map = new HashMap(4);
        map.put("1", "cl");
        map.put("2", "woshishui");
        User user = new User();
        user.setUserName("莉莉");
        map.put("3", user);
        map.put("4", 5);
        redisUtils.hmset("thash", map);
        System.out.println(redisUtils.hgetall("thash").toString());
        redisUtils.hset("thash", "4", "haohaoxuexi");
        System.out.println(redisUtils.hget("thash", "4", String.class));
        return "redis";
    }
}
