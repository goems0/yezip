package com.dongduk.yezip.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dongduk.yezip.domain.User;
import com.dongduk.yezip.service.YezipFacade;

import jakarta.servlet.http.HttpSession;


@Controller
public class SignupController {
	private YezipFacade yezip;
	@Autowired
	public void setYezip(YezipFacade yezip) {
		this.yezip = yezip;
	}
    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }
    
    @PostMapping("/signup")
    @ResponseBody // JSON 형식 응답을 위해 추가
    public Map<String, Object> signup(@RequestParam("id") String id,
                                     @RequestParam("pw") String pw,
                                     @RequestParam("confirmPw") String confirmPw,
                                     @RequestParam("name") String name,
                                     @RequestParam("phone") String phone,
                                     @RequestParam("email") String email,
                                     @RequestParam("email2") String email2,
                                     @RequestParam(value = "yakgwan-check", defaultValue = "false") boolean check1,
                                     @RequestParam(value = "info-check", defaultValue = "false") boolean check2) {
        Map<String, Object> result = new HashMap<>();
        
        if (!pw.equals(confirmPw)) {
            result.put("success", false);
            result.put("error", "비밀번호가 일치하지 않습니다.");
            return result;
        }

        if (checkIdDuplicate(id)) {
            result.put("success", false);
            result.put("error", "이미 사용 중인 아이디입니다. 다시 시도해주세요.");
            return result;
        }

        if (!email2.equals("input")) {
            email = email + email2;
        }

        User user = new User(id, pw, name, phone, email);
        if (check1 && check2) {
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("error", "필수 이용약관을 확인해주세요.");
            return result;
        }
        if (yezip.registerUser(user)){
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("error", "회원가입에 실패하였습니다.");
            return result;
        }
        return result;
    }

    
    // 아이디 중복 확인
    @GetMapping("/check-id")
    @ResponseBody
    public boolean checkIdDuplicate(@RequestParam String userid) {
    	System.out.println(userid);
    	boolean result = yezip.isIdDuplicate(userid);
    	System.out.println(result);
        return result;
    }
    
    // 본인 제외 아이디 중복 확인
    @GetMapping("/check-id2")
    @ResponseBody
    public boolean checkIdDuplicate2(@RequestParam String userid, HttpSession session) {
    	String currentId = (String)session.getAttribute("userid");
    	System.out.println("현재아이디: "+currentId+ ", 바꾸려는아이디: "+userid);
    	if (currentId == null) {
            throw new IllegalStateException("로그인되지 않은 사용자입니다.");
        }
        if (currentId.equals(userid)) {
            return false; // 본인의 ID는 중복 아님
        }
        return yezip.isIdDuplicate(userid); // 중복되면 true
    }

}
