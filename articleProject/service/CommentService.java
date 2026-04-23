package articleProject.service;

import articleProject.curdInterface.CrudInterface;
import articleProject.dao.ArticleDAO;
import articleProject.dto.CommentDto;
import articleProject.repository.ArticleRepository;

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
