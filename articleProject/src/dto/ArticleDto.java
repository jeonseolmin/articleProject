package dto;
import entity.Article;
import entity.Comment;
import entity.CommonField;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleDto extends CommonField {
    Long id;
    String name,title,content;
    List<CommentDto> commentlist = new ArrayList<>();
    //기본 생성자
    public ArticleDto(Long id, String name, String title, String content) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
    }
    //날짜 포함
    public ArticleDto(Long id, String name, String title, String content, LocalDateTime insertedDate) {
        super.setInsertedDate(insertedDate);
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
    }

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

    //전체
    public ArticleDto(Long id, String name, String title, String content,LocalDateTime insertedDate, LocalDateTime updatedDate) {
        super.setUpdatedDate(updatedDate);
        super.setInsertedDate(insertedDate);
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
    }
    //댓글 포함
    public ArticleDto(Long id, String name, String title, String content, LocalDateTime insertedDate, LocalDateTime updatedDate, List<Comment> comments) {
        super.setUpdatedDate(updatedDate);
        super.setInsertedDate(insertedDate);
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
        for (Comment comment : comments){
            this.commentlist.add(
               new CommentDto(
                       comment.getCommentId(),
                       comment.getArticleId(),
                       comment.getName(),
                       comment.getContent()
               )
            );
        }
    }
    // Ariticle -> ArticleDto
    public static ArticleDto fromArticle(Article article){
        return new ArticleDto(
                article.getId(),
                article.getName(),
                article.getTitle(),
                article.getContent(),
                article.getInsertedDate(),
                article.getUpdatedDate(),
                article.getCommentList()
        );
    }
    // ArticleDto -> Ariticle
    public static Article fromDto(ArticleDto dto){
        List<Comment> commentList = new ArrayList<>();
        for (CommentDto commentDto : dto.commentlist){
            commentList.add(
                    new Comment(
                            commentDto.commentId(),
                            commentDto.articleId(),
                            commentDto.name(),
                            commentDto.content()
                    )
            );
        }
        return new Article(
                dto.id,
                dto.name,
                dto.title,
                dto.content,
                dto.getInsertedDate(),
                dto.getUpdatedDate(),
                commentList);
    }
    //새 글 작성용
    public static ArticleDto makeArticleDto(Long id,String name,String title,String content,LocalDateTime insertedDate){
        return  new ArticleDto(id,name,title,content,insertedDate);
    }
    //getCommentList

    public List<CommentDto> getCommentlist() {
        return commentlist;
    }
}
