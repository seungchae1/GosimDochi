package kr.hs.study.GosimDochi.controller;

import kr.hs.study.GosimDochi.DTO.Comm;
import kr.hs.study.GosimDochi.DTO.Myuser;
import kr.hs.study.GosimDochi.DTO.Post;
import kr.hs.study.GosimDochi.Service.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
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
        model.addAttribute("p_list",s.select_post());
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

    @GetMapping("/logout")
    public String logout(){
        user=null;
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
    @PostMapping("/post_re")
    public String post_re(@RequestParam("title") String title, @RequestParam("content") String content){
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUser_id(user.getId());
        s.insert_post(post);
        return "redirect:/";
    }

    @GetMapping("/v_post/{no}")
    public String v_post(@PathVariable("no") int no, Model model){
        Post p = s.select_post_One(no);
        List<Comm> comms = s.select_comm(no);
        model.addAttribute("post_no",no);
        model.addAttribute("post",p);
        model.addAttribute("comm",comms);
        s.update_v(no);
        return "post_view";
    }

    @PostMapping("/write_comm")
    public String w_comm(@RequestParam("post_no") int no, @RequestParam("comm_content") String comm_content){
        Comm c = new Comm();
        c.setUser_id(user.getId());
        c.setContent(comm_content);
        c.setPost_no(no);
        s.insert_comm(c);
        return "redirect:/";
    }

    @PostMapping("/search")
    public String search(@RequestParam("sel") String sel, @RequestParam("keyword") String keyword, Model model){
        List<Post> posts = s.select_search(keyword, sel);
        if(user==null) model.addAttribute("not_login", true);
        else {
            model.addAttribute("is_login", true);
            model.addAttribute("name", user.getName());
        }
        model.addAttribute("p_list",posts);
        return "index";
    }

}
