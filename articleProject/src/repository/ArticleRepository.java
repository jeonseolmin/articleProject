package repository;

import curdInterface.CrudInterface;
import entity.Article;
import entity.Comment;
import java.util.ArrayList;
import java.util.List;

//게시글(Article) 데이터를 저장·조회·수정·삭제(CRUD)하는 저장소 역할
public class ArticleRepository implements CrudInterface {
   public static Long articleId = 1L;
   public static Long commentId = 1L;
   public static List<Article> articleList = new ArrayList<>();

    @Override
    public List<Article> all() {
        return articleList;
    }

    @Override
    public void newArticle(Article article) {
        articleList.add(article);
    }

    @Override
    public Article detail(Long id) {
        for (Article article : articleList){
            if(article.getId().equals(id)){
                return article;
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        for (Article article : articleList){
            if(article.getId().equals(id)){
                articleList.remove((article));
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(Article article) {
        for (Article a : articleList){
            if(a.getId().equals(article.getId())){
               articleList.set(a.getId().intValue()-1,article);
            }
        }
    }

    @Override
    public void insertComment(Comment comment) {
        Article article = detail(comment.getArticleId());
        if(article !=null){
            comment.setCommentId(commentId++);
            article.addComments(comment);
        }
    }

    @Override
    public void updateComment(Comment comment) {
        Article article = detail(comment.getArticleId());
        if (article !=null){
            for (Comment savedComment : article.getCommentList()){
                if (savedComment.getCommentId().equals(comment.getCommentId())){
                    savedComment.setContent(comment.getContent());
                    return;
                }
            }
        }
    }

    @Override
    public void deleteComment(Long deleteCommentId) {
        Article article = detail(deleteCommentId);
        if (article !=null){
            for (Comment deletedComment : article.getCommentList()){
                if (deletedComment.getCommentId().equals(deleteCommentId)){
                    article.getCommentList().removeIf(c-> c.getCommentId().equals(deleteCommentId));
                    return;
                }
            }
        }
    }
}
