package com.dongduk.yezip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dongduk.yezip.domain.User;
import com.dongduk.yezip.service.YezipFacade;

import org.springframework.ui.Model;

@Controller
public class LoginController {
	private final YezipFacade yezip;

    @Autowired
    public LoginController(YezipFacade yezip) {
        this.yezip = yezip;
    }

    @PostMapping("/login")
    public String login(@RequestParam("id") String id,
                        @RequestParam("pw") String pw,
                        Model model) {
        User user = yezip.findByUserIdAndPw(id, pw);
        
        if (user != null) {
            // 로그인 성공
            model.addAttribute("user", user);
//            return "redirect:/"; // 로그인 성공 후 성공 팝업
        } else {
            // 로그인 실패
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "/"; // 메인 페이지로 이동
        }
        return "/"; // 메인 페이지로 이동
    }
}