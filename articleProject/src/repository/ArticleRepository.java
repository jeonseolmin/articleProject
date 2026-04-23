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
        return articleList.removeIf(article -> article.getId().equals(id));
    }

    @Override
    public boolean update(Article article) {
        for (int i = 0; i < articleList.size(); i++){
            if(articleList.get(i).getId().equals(article.getId())){
                articleList.set(i,article);
                return true;
            }
        }
        return false;
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
    public boolean deleteComment(Long deleteCommentId) {
        for (Article article : articleList) {
            if (article.removeComment(deleteCommentId)) {
                return true;
            }
        }
        return false;
    }
}
