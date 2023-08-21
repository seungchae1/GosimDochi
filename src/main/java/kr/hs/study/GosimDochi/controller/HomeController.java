package kr.hs.study.GosimDochi.controller;

import kr.hs.study.GosimDochi.DTO.Myuser;
import kr.hs.study.GosimDochi.Service.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    service s;

    public static Myuser user;

    @GetMapping("/")
    public String home(Model model){
        if(user==null) model.addAttribute("not_login", true);
        else {
            model.addAttribute("is_login", true);
            model.addAttribute("name", user.getName());
        }

        return "index";
    }
    @GetMapping("/login")
    public String log_form(){
        return "login";
    }
    @PostMapping("/login_re")
    public String login_re(@RequestParam("id") String id,@RequestParam("pass") String pass){
        List<Myuser> users = s.select_user();
        for ( Myuser i: users) {
            if(id.equals(i.getId()) && pass.equals(i.getPass())){
                user = new Myuser();
                user.setId(i.getId());
                user.setName(i.getName());
                user.setPass(i.getPass());
                break;
            }
        }
        return "redirect:/";
    }

    @GetMapping("/join")
    public String join_form(){
        return "join";
    }
    @PostMapping("/join_re")
    public String join_re(@RequestParam("id") String id,@RequestParam("pass") String pass,
                          @RequestParam("ch_pass") String ch_pass, @RequestParam("name") String name){
        if(pass.equals(ch_pass)){
            Myuser u = new Myuser();
            u.setId(id);
            u.setName(name);
            u.setPass(pass);
            s.insert_user(u);
        }else{
            return "redirect:/join";
        }
        return "redirect:/";
    }

    @GetMapping("/write_post")
    public String w_post(){
        return "post_form";
    }

}