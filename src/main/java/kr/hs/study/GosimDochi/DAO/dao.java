package kr.hs.study.GosimDochi.DAO;

import kr.hs.study.GosimDochi.DTO.Comm;
import kr.hs.study.GosimDochi.DTO.Myuser;
import kr.hs.study.GosimDochi.DTO.Post;
import kr.hs.study.GosimDochi.DTO.Report;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface dao {
    public void insert_user(Myuser u);
    public void insert_post(Post p);
    public void insert_comm(Comm c);
    public boolean update_report(int id);

    public List<Myuser> select_user();
    public List<Post> select_post();
    public List<Post> select_search(String keyword, String sel);
    public List<Comm> select_comm(int no);
    public Post select_post_One(int id);
    public void update_v(int id);
    public void delete_user(String id);
    public void delete_post(int id) ;
    public void delete_comm(int id) ;
}