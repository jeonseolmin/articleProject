package service;
import crudInterface.CrudInterface;
import dao.ArticleDAO;
import dto.CommentDto;


public class CommentService  {
     private final CrudInterface dao = new ArticleDAO();
//   private final CrudInterface dao = new ArticleRepository();
    public void addComment(CommentDto comment){
        dao.insertComment(CommentDto.fromDTO(comment));
    }
    public boolean updateComment(CommentDto comment){
        return dao.updateComment(CommentDto.fromDTO(comment));
    }
    public boolean deleteComment(Long deleteCommentId){
        return  dao.deleteComment(deleteCommentId);
    }
}
