package org.jcl.life.controller;

import com.alibaba.fastjson.JSON;
import org.jcl.life.user.User;
import org.jcl.life.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;

@RestController
public class NoteController {

	@Autowired
	private UserService userServiceImpl;

	@RequestMapping("/test")
	public String test(ModelMap model) {
		String str = "cl hello";
		System.out.println(str + userServiceImpl.getUser().toString() );
		System.out.println(model.get("name"));
		System.out.println(model.get("data"));
		return str;
	}

	@RequestMapping(path = "/posttest", method= RequestMethod.POST)
	public String test(User user) {
		System.out.println("Post test");
		System.out.println(user.getUserName());
		User u = new User();
		u.setUserName("hah");
		return JSON.toJSONString(u);
	}
}
