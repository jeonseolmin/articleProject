package service;
import curdInterface.CrudInterface;
import dao.ArticleDAO;
import dto.CommentDto;


public class CommentService  {
     CrudInterface dao = new ArticleDAO();
//    CrudInterface dao = new ArticleRepository();
    public void commentAdd(CommentDto comment){
        dao.insertComment(CommentDto.fromDTO(comment));
    }
    public void commentUpdate(CommentDto comment){
        dao.updateComment(CommentDto.fromDTO(comment));
    }
    public void commentDelete(Long deleteCommentId){
        dao.deleteComment(deleteCommentId);
    }
}
