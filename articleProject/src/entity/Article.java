package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Article extends CommonField {
    Long id;
    String name,title,content;
   //Comment == commentId,articleId,name,content로 이루어진 객체
    List<Comment> commentList = new ArrayList<>();

    public Article(Long id, String name, String title, String content, LocalDateTime insertedDate, LocalDateTime updatedDate, List<Comment> comments) {
        super.setInsertedDate(insertedDate);
        super.setUpdatedDate(updatedDate);
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.commentList = commentList;
    }
    public Article(Long id, String name, String title, String content, List<Comment> comments) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.commentList = commentList;
    }
    //getter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }


    //메서드
    //댓글 추가
    public void addComments(Comment comment){

        commentList.add(comment);
    }
    //댓글 목록 일괄 설정
    public void setCommentList(List<Comment> commentList){

        this.commentList = new ArrayList<Comment>(commentList);
    }

    public boolean removeComment(Long deleteCommentId) {
        return commentList.removeIf(comment -> comment.getCommentId().equals(deleteCommentId));
    }
}
