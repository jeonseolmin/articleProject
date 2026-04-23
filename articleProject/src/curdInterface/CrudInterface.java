package curdInterface;

import entity.Article;
import entity.Comment;

import java.util.List;

public interface CrudInterface {
    //게시글
    List<Article> all();
    void newArticle(Article article);
    Article detail(Long id);
    boolean delete(Long id);
    boolean update(Article article);

    //댓글
    void insertComment(Comment comment);
    void updateComment(Comment comment);
    boolean deleteComment(Long deleteCommentId);
}
