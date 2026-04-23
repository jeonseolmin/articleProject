package service;
import curdInterface.CrudInterface;
import dao.ArticleDAO;
import dto.ArticleDto;
import entity.Article;
import java.time.LocalDateTime;
import java.util.List;

public class ArticleService {
    CrudInterface repository = new ArticleDAO();        // DB 버전
// CrudInterface repository = new ArticleRepository(); // 메모리 버전
    public List<ArticleDto> all(){
        return repository.all().stream().map(ArticleDto::fromArticle).toList();
    }
    public void newArticle(ArticleDto dto){
        dto.setInsertedDate(LocalDateTime.now());
        Article article = ArticleDto.fromDto(dto);
        repository.newArticle(article);
    }
    public Article detail(Long id){
        return repository.detail(id);
    }
    public boolean  delete(Long id){
        return repository.delete(id);
    }
    public void update(ArticleDto dto){
        dto.setUpdatedDate(LocalDateTime.now());
        repository.update(ArticleDto.fromDto(dto));
    }
}
