package com.example.mjcstagram.home;

public class Home_Data {

    //글 쓴 사람의 이름
    private String user_name;
    //글쓴 사람의 사진
    private int user_img;

    //게시글 사진
    private int imgV;
    //댓글 단 사람의 이름
    private String other_user_name;
    //내 사진
    private int my_img;
    //좋아요 수
    private String likes;
    //댓글내용
    private String other_user_comment;
    //댓글 수
    private String comment_count;

    private int imagesize;


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_img(int user_img) {
        this.user_img = user_img;
    }

    public int getUser_img() {
        return user_img;
    }

    public int getImgV() {
        return imgV;
    }

    public void setImgV(int imgV) {
        this.imgV = imgV;
    }

    public String getOther_user_name() {
        return other_user_name;
    }

    public void setOther_user_name(String other_user_name) {
        this.other_user_name = other_user_name;
    }

    public String getOther_user_comment() {
        return other_user_comment;
    }

    public void setOther_user_comment(String other_user_comment) {
        this.other_user_comment = other_user_comment;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }


    public int getSize() {
        return imagesize;
    }

    public void setSize(int imagesize) {
        this.imagesize = imagesize;
    }


    public int getMy_img() {
        return my_img;
    }

    public void setMy_img(int my_img) {
        this.my_img = my_img;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

}
