package org.jcl.life.controller;

import org.jcl.life.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {

	@Autowired
	private UserService userServiceImpl;

	@RequestMapping("/test")
	public String test() {
		String str = "cl hello";
		System.out.println(str + userServiceImpl.getUser().toString() );
		return str;
	}
}
