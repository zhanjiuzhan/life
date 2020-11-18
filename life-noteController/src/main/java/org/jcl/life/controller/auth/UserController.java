package org.jcl.life.controller.auth;

import org.jcl.life.auth.*;
import org.jcl.life.pretreat.WithPermissionController;
import org.jcl.life.result.RetResult;
import org.jcl.life.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户信息的操作
 * @author chenglei
 */
@RestController
@RequestMapping("/auth")
public class UserController extends WithPermissionController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleAndUserService roleAndUserService;

    @Autowired
    private PermissionAndRoleService permissionAndRoleService;

    @RequestMapping("/getUser")
    public RetResult getUser(String userId) {
        RetResult ret = RetResult.getRetResult(User.class);
        if (StringUtils.isNotEmpty(userId)) {
            User user = userService.getUser(userId);
            if (null != user) {
                ret.setData(user);
            }
        }
        return ret;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public RetResult addUser(User user) {
        RetResult ret = RetResult.getRetResult(User.class);
        if (StringUtils.isNotEmpty(user.getUserName(), user.getPassword())) {
            boolean rs = userService.addUser(user);
            if (!rs) {
                ret.setRetCode(RetResult.CODE500);
            }
        }
        //logger.info(user.toString() + "添加成功。");
        return ret;
    }

    @RequestMapping("/getUsers")
    public RetResult getUsers() {
        RetResult ret = RetResult.getRetResult(List.class);
        List<User> users = userService.getUsers();
        ret.setData(users);
        return ret;
    }

    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
    public RetResult deleteUsers(User user) {
        RetResult ret = RetResult.getRetResult(User.class);
        if (StringUtils.isNotEmpty(user.getId(), user.getUserName())) {
            boolean rs = userService.delUser(user.getId());
            if (!rs) {
                ret.setRetCode(RetResult.CODE500);
            }
        }
        return ret;
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public RetResult updateUsers(User user) {
        RetResult ret = RetResult.getRetResult(User.class);
        if (StringUtils.isNotEmpty(user.getId(), user.getUserName(),
                user.getPassword())) {
            boolean rs = userService.updateUser(user);
            if (!rs) {
                ret.setRetCode(RetResult.CODE500);
            }
        }
        return ret;
    }


    // TODO 以下的函数紧紧是临时添加的逻辑不全

    @RequestMapping("/addRole")
    public RetResult addRole(Role role) {
        RetResult ret = RetResult.getRetResult(Role.class);
        roleService.addRole(role);
        return ret;
    }

    @RequestMapping("/addUserAndRole")
    public RetResult addRoleAndUser(RoleAndUser relation) {
        RetResult ret = RetResult.getRetResult(RoleAndUser.class);
        roleAndUserService.addRelation(relation);
        return ret;
    }

    @RequestMapping("/addPermissionAndRole")
    public RetResult addPermissionAndRole(PermissionAndRole relation) {
        RetResult ret = RetResult.getRetResult(PermissionAndRole.class);
        permissionAndRoleService.addRelation(relation);
        return ret;
    }
}