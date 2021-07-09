package com.example.ksw17security.controller;

import com.example.ksw17security.model.User;
import com.example.ksw17security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableWebSecurity // 필터체인
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class IndexController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"","/"})
    public String index(){
        return "index";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "어드민 페이지입니다.";
    }

    @GetMapping("/user")
    @ResponseBody
    public String goUser(){
        return "유저페이지";
    }

    @GetMapping("/manager")
    @ResponseBody
    public String manager() {
        return "매니저 페이지입니다.";
    }

    // 스프링에서 해당 주소 강탈?!?!??!
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/loginForm";
    }

    @GetMapping("/info")
    @Secured({"ROLE_ADMIN","ROLE_MANAGER"})
    @ResponseBody
    public String info(){
        
        return "개인정보";
    }
    @GetMapping("/data")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @PostAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public String data(){
        // TODO : PostAuthorize , PreAuthorize 비교 분석
        return "데이터";
    }
}
