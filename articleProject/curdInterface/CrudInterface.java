package articleProject.curdInterface;

import articleProject.entity.Article;
import articleProject.entity.Comment;

import java.util.List;

public interface CrudInterface {
    //게시글
    List<Article> all();
    void newArticle(Article article);
    Article detail(Long id);
    boolean delete(Long id);
    void update(Article article);

    //댓글
    void insertComment(Comment comment);
    void updateComment(Comment comment);
    void deleteComment(Long deleteCommentId);
}
