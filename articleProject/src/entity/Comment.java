package entity;

public class Comment {
    Long commentId, articleId;
    String name,content;

    //생성자
    public Comment(Long commentId, Long articleId, String name, String content) {
        this.commentId = commentId;
        this.articleId = articleId;
        this.name = name;
        this.content = content;
    }
    //commentId
    public Long getCommentId() {
        return commentId;
    }
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
    //ArticleId
    public Long getArticleId() {
        return articleId;
    }
    //Name
    public String getName() {
        return name;
    }
    //Content
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
