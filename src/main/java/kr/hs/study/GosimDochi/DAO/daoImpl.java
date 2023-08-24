package kr.hs.study.GosimDochi.DAO;

import kr.hs.study.GosimDochi.DTO.Comm;
import kr.hs.study.GosimDochi.DTO.Myuser;
import kr.hs.study.GosimDochi.DTO.Post;
import kr.hs.study.GosimDochi.DTO.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class daoImpl implements dao{
    @Autowired
    JdbcTemplate jdbc;
    @Override
    public void insert_user(Myuser u) {
        String sql = "insert into user_tbl values(?,?,?)";
        jdbc.update(sql, u.getId(),u.getPass(), u.getName());
    }

    @Override
    public void insert_post(Post p) {
        String sql = "insert into post_tbl values(post_seq.NEXTVAL,?,?,?,sysdate,0)";
        jdbc.update(sql, p.getUser_id(),p.getTitle(), p.getContent());

        String sql2 = "insert into report_tbl values(post_seq.CURRVAL,?,0)";
        jdbc.update(sql2, p.getUser_id());

    }

    @Override
    public void insert_comm(Comm c) {
        String sql = "insert into comment_tbl values(comm_seq.NEXTVAL,?,?,?,sysdate)";
        jdbc.update(sql, c.getPost_no(), c.getUser_id(), c.getContent());
    }

    @Override
    public boolean update_report(int id) {
        String sql = "update report_tbl set count=(select count from report_tbl where no="+id+")+1 where no="+id;
        jdbc.update(sql);
        String sql2 = "select * from report_tbl where no="+id;
        Report report = jdbc.queryForObject(sql2,new BeanPropertyRowMapper<>(Report.class));
        if(report.getCount() >= 5){
            delete_post(report.getNo());
            if(!(select_comm(report.getNo()).equals(null))){
                delete_comm(report.getNo());
            }
            return true;
        }
        return false;
    }

    @Override
    public List<Myuser> select_user() {
        String sql = "select * from user_tbl";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Myuser.class));
    }

    @Override
    public List<Post> select_post() {
        String sql = "select * from post_tbl order by no desc";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Post.class));
    }

    @Override
    public List<Post> select_search(String keyword, String sel) {
        String sql="";
        switch (sel){
            case "pc" :
                sql = "select p.no, p.user_id, p.title, p.content, p.post_date, p.views from post_tbl p left outer join comment_tbl c on p.no = c.post_no" +
                        " where title like '%"+keyword+"%' or p.content like '%"+keyword+"%' or c.content like '%"+keyword+"%'";
                break;
            case "p":
                sql = "select p.no, p.user_id, p.title, p.content, p.post_date, p.views from post_tbl p left outer join comment_tbl c on p.no = c.post_no" +
                        " where title like '%"+keyword+"%' or p.content like '%"+keyword+"%'";
                break;
            case "c" :
                sql = "select p.no, p.user_id, p.title, p.content, p.post_date, p.views from post_tbl p left outer join comment_tbl c on p.no = c.post_no" +
                        " where c.content like '%"+keyword+"%'";
                break;
        }
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Post.class));
    }

    @Override
    public List<Comm> select_comm(int no) {
        String sql = "select * from comment_tbl where post_no ="+no+" order by no desc";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Comm.class));
    }

    @Override
    public Post select_post_One(int id) {
        String sql = "select * from post_tbl where no="+id;
        return jdbc.queryForObject(sql,new BeanPropertyRowMapper<>(Post.class));
    }

    @Override
    public void update_v(int id) {
        String sql = "update post_tbl set views=(select views from post_tbl where no="+id+")+1 where no="+id;
        jdbc.update(sql);
    }

    @Override
    public void delete_user(String id) {
        String sql = "delete user_tbl where id="+id;
        jdbc.update(sql);
    }

    @Override
    public void delete_post(int id) {
        String sql = "delete post_tbl where no="+id;
        jdbc.update(sql);
    }

    @Override
    public void delete_comm(int id) {
        String sql = "delete comment_tbl where post_no="+id;
        jdbc.update(sql);

    }
}
