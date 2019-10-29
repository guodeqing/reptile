package reptile.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.User;

@Controller
public class UserController {

	@GetMapping("/tologin")
	public String  tolongin(){
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("/template/login.html");
		return "login" ;
	}
	@GetMapping("/loginout")
	public String  longinout(){
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("/template/login.html");
		return "login" ;
	}
	@RequestMapping(value="/login")
	public String  longin(HttpServletRequest request,HttpServletResponse response){
		List<User> users= new  ArrayList<>();
		User user = new User();
		user.setId(1);
		user.setPassword("123456");
		user.setUserName("郭得青");
		user.setEmail("756834111@qq.com");
		users.add(user);
		request.setAttribute("users", users);
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("/template/login.html");
		return "index" ;
	}
	@ResponseBody
	@GetMapping("/pwd")
	public String pwd(){
		return  new BCryptPasswordEncoder(4).encode("123456");
	}
}
