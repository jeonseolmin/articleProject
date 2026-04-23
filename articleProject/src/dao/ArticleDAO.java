package dao;

import curdInterface.CrudInterface;
import entity.Article;
import entity.Comment;
import db.DBConn;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ArticleDAO implements CrudInterface {
    private void getArticleComments(List<Article> articles){
        Connection connection = DBConn.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT  * FROM comments WHERE article_Id = ?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            for (Article article : articles){
                preparedStatement.setLong(1,article.getId());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    Comment comment = new Comment(
                            resultSet.getLong("comment_id"),
                            resultSet.getLong("article_id"),
                            resultSet.getString("name"),
                            resultSet.getString("content")
                    );
                    article.addComments(comment);
                }
                resultSet.close();
            }
            preparedStatement.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Article> all() {
        List<Article> articleList = new ArrayList<>();
        Connection connection = DBConn.getConnection();
        PreparedStatement preparedStatementArt = null;
        ResultSet resultSetArt = null;
        try{
            String sql_selectAct = "SELECT * FROM article ORDER BY id DESC";
            preparedStatementArt = connection.prepareStatement(sql_selectAct);
            resultSetArt = preparedStatementArt.executeQuery();
            while(resultSetArt.next()){
                Article article = new Article(
                        resultSetArt.getLong("id"),
                        resultSetArt.getString("name"),
                        resultSetArt.getString("title"),
                        resultSetArt.getString("content"),
                        new ArrayList<> ()
                );
                Timestamp insertedTs = resultSetArt.getTimestamp("inserted_date");
                Timestamp  updatedTs = resultSetArt.getTimestamp("updated_date");
                article.setInsertedDate(insertedTs==null ? null:insertedTs.toLocalDateTime());
                article.setUpdatedDate(updatedTs==null ? null:updatedTs.toLocalDateTime());
                articleList.add(article);
            }
            resultSetArt.close();
            preparedStatementArt.close();
            getArticleComments(articleList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  articleList;
    }
    @Override
    public void newArticle(Article article) {
        Connection connection = DBConn.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO article(name, title, content, inserted_date, updated_date) VALUES(?,?,?,?,?)";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,article.getName());
            preparedStatement.setString(2,article.getTitle());
            preparedStatement.setString(3,article.getContent());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp(5, null);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch(Exception e){
            System.out.println("INSERT  오류"+e.getMessage());
        }
    }
    @Override
    public Article detail(Long id) {
        Article article = null;
        Connection connection = DBConn.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM article WHERE id=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                article = new Article(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        new ArrayList<>()
                );
                Timestamp insertedTs = resultSet.getTimestamp("inserted_date");
                Timestamp  updatedTs = resultSet.getTimestamp("updated_date");
                article.setInsertedDate(insertedTs==null ? null:insertedTs.toLocalDateTime());
                article.setUpdatedDate(updatedTs==null ? null:updatedTs.toLocalDateTime());
            }else {return null;}
            sql = "SELECT * FROM comments WHERE article_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,article.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Comment comment = new Comment(
                        resultSet.getLong("comment_id"),
                        resultSet.getLong("article_id"),
                        resultSet.getString("name"),
                        resultSet.getString("content")

                );
                article.getCommentList().add(comment);
            }
            resultSet.close();
            preparedStatement.close();
        }catch(Exception e){
            System.out.println("INSERT  오류"+e.getMessage());
        }
        return article;
    }

    @Override
    public boolean delete(Long id) {
        int result = 0;
        Connection connection = DBConn.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String sql = "DELETE FROM article WHERE id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            result = preparedStatement.executeUpdate();
            preparedStatement.close();

        }catch (Exception e){
            System.out.println("delete 오류: " + e.getMessage());
        }
        return result > 0 ? true : false;
    }

    @Override
    public void update(Article article) {
        Connection connection = DBConn.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "UPDATE article SET name=?, title=?, content=?, inserted_date=?, updated_date=? WHERE id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,article.getName());
            preparedStatement.setString(2,article.getTitle());
            preparedStatement.setString(3,article.getContent());
            preparedStatement.setTimestamp(4,Timestamp.valueOf(article.getInsertedDate()));
            preparedStatement.setTimestamp(5,Timestamp.valueOf(article.getUpdatedDate()));
            preparedStatement.setLong(6,article.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            System.out.println("update 오류: " + e.getMessage());
        }
    }

    @Override
    public void insertComment(Comment comment) {
        Connection connection = DBConn.getConnection();
        PreparedStatement preparedStatement = null;
        try{
            String sql = "INSERT INTO comments(name, content, article_id) VALUES(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,comment.getName());
            preparedStatement.setString(2,comment.getContent());
            preparedStatement.setLong(3,comment.getArticleId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            System.out.println("InsertComment 오류"+e.getMessage());
        }
    }

    @Override
    public void updateComment(Comment comment) {
        Connection connection = DBConn.getConnection();
        PreparedStatement preparedStatement = null;
        try{
            String sql = "UPDATE comments SET content=? WHERE comment_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,comment.getContent());
            preparedStatement.setLong(2,comment.getCommentId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            System.out.println("UpdateComment 오류"+e.getMessage());
        }
    }

    @Override
    public void deleteComment(Long deleteCommentId) {
        Connection connection = DBConn.getConnection();
        PreparedStatement preparedStatement = null;
        try{
            String sql = "DELETE FROM comments WHERE comment_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,deleteCommentId);
            int result = preparedStatement.executeUpdate();
            System.out.println("삭제된 행 수 "+result);
            preparedStatement.close();
        }catch (Exception e){
            System.out.println("DeleteComment 오류"+e.getMessage());
        }
    }
}
