package kr.hs.study.GosimDochi.Service;

import kr.hs.study.GosimDochi.DAO.dao;
import kr.hs.study.GosimDochi.DTO.Comm;
import kr.hs.study.GosimDochi.DTO.Myuser;
import kr.hs.study.GosimDochi.DTO.Post;
import kr.hs.study.GosimDochi.DTO.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class serviceImpl implements service {
    @Autowired
    dao d;
    @Override
    public void insert_user(Myuser u) {
        d.insert_user(u);
    }

    @Override
    public void insert_post(Post p) {
        d.insert_post(p);
    }

    @Override
    public void insert_comm(Comm c) {
        d.insert_comm(c);
    }

    @Override
    public void insert_report(Report r) {
        d.insert_report(r);
    }

    @Override
    public void update_report(int id) {
        d.update_report(id);
    }

    @Override
    public List<Myuser> select_user() {
        return d.select_user();
    }

    @Override
    public List<Post> select_post() {
        return d.select_post();
    }

    @Override
    public List<Comm> select_comm() {
        return d.select_comm();
    }

    @Override
    public Post select_post_One(int id) {
        return d.select_post_One(id);
    }

    @Override
    public void update(Post p) {
        d.update(p);
    }

    @Override
    public void delete_user(String id) {
        d.delete_user(id);
    }

    @Override
    public void delete_post(int id) {
        d.delete_post(id);
    }

    @Override
    public void delete_comm(int id) {
        d.delete_comm(id);
    }
}
