package kr.hs.study.GosimDochi.DTO;

import lombok.Data;

@Data
public class Post {
    int no;
    String user_id;
    String title;
    String content;
    String post_date;
    int views;
}
