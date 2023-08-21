package kr.hs.study.GosimDochi.Service;

import kr.hs.study.GosimDochi.DTO.Comm;
import kr.hs.study.GosimDochi.DTO.Myuser;
import kr.hs.study.GosimDochi.DTO.Post;
import kr.hs.study.GosimDochi.DTO.Report;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface service {
    public void insert_user(Myuser u);
    public void insert_post(Post p);
    public void insert_comm(Comm c);
    public void insert_report(Report r);
    public void update_report(int id);
    public List<Myuser> select_user();
    public List<Post> select_post();
    public List<Comm> select_comm();
    public Post select_post_One(int id);
    public void update(Post p);
    public void delete_user(String id);
    public void delete_post(int id);
    public void delete_comm(int id);
}