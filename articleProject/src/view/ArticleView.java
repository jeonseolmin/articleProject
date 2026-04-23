package view;

import dto.ArticleDto;
import dto.CommentDto;
import repository.ArticleRepository;
import service.ArticleService;
import service.CommentService;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleView {
    Scanner sc = new Scanner(System.in);
    ArticleService articleService = new ArticleService();
    CommentService  commentService = new CommentService();
    List<ArticleDto> articleDto = new ArrayList<>();

    public void showAll(){
        articleDto =articleService.all();
        if (articleDto.isEmpty()){
            System.out.println("게시글이 없습니다");
            return;
        }
        String str = """
                =============================================
                id      name         title         작성일
                =============================================
                """;
        System.out.println(str);
        for (ArticleDto dto : articleDto){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateStr = dto.getInsertedDate() == null ? "없음" : dto.getInsertedDate().format(formatter);
            System.out.println(dto.getId() +"\t     "+dto.getName()+
                    " \t     "+dto.getTitle()+"      \t    "+dateStr);
            for (CommentDto commentDto : dto.getCommentlist()){
                System.out.println(commentDto);
            }
        }
        System.out.println("=============================================");
    }
    public void showNewArticle(){
        System.out.println("새글 입력 창입니다.");
        System.out.println("\uD83D\uDE80 Name\t:");
        String name = sc.next();
        System.out.println("\uD83D\uDE80 Title\t:");
        String title = sc.next();
        System.out.println("\uD83D\uDE80 Content\t:");
        String content = sc.next();
//        System.out.println("\uD83D\uDE80 작성일\t:");
//        String insertedTime = sc.nextLine();
//        System.out.println("\uD83D\uDE80 작성일\t:");
//        String updateTime = sc.nextLine();
        ArticleDto dto = ArticleDto.makeArticleDto(
                ArticleRepository.articleId,
                name,
                title,
                content,
                LocalDateTime.now());
        ArticleRepository.articleId++;
        articleService.newArticle(dto);
    }
    public void showDetail(){
        System.out.println("확인할 게시글의 아이디를 입력해주세요 :");
        Long id = sc.nextLong();
        sc.nextLine();
        ArticleDto article = articleService.detail(id);
        if(article == null){
            System.out.println("해당 게시글이 없습니다");
            return;
        }
        String str = """
                🚀 ID\t  : %d
                🚀 Name\t : %s
                🚀 Title\t :%s
                🚀 Content : %s
                🚀 작성일 : %s
                🚀 수정일 : %s
                """.formatted(article.getId(),article.getName(),article.getTitle(),article.getContent(),article.getInsertedDate(),article.getUpdatedDate());
        System.out.println(str);
        commentCRUD(article);


    }
    public void showDelete(){
        System.out.println("삭제할 게시판의 아이디를 입력해주세요: ");
        Long id = sc.nextLong();
        if (id == null && id < 0){
            System.out.println("삭제 실패했습니다. -> 잘못된 ID를 입력하셨습니다.");
            return;
        }
        articleService.delete(id);
        System.out.println("삭제됐습니다.");
    }
    public void showUpdate(){
        showAll();
        System.out.println("수정할 게시판의 아이디를 입력해주세요: ");
        Long id = sc.nextLong();
        sc.nextLine();
        System.out.println("수정할 제목을 입력해주세요: ");
        String title = sc.nextLine();
        System.out.println("수정할 내용을 입력해주세요: ");
        String content = sc.nextLine();
        ArticleDto article = articleService.detail(id);
        articleService.update(new ArticleDto(article.getId(),article.getName(), title, content,LocalDateTime.now()));
    }
    private void commentC(ArticleDto article){
        String name, content;
        System.out.println(" 댓글 등록자 이름: ");
        name = sc.next();
        System.out.println(" 댓글 내용: ");
        content = sc.next();
        CommentDto dto = new CommentDto(null,article.getId(),name,content);
        commentService.addComment(dto);
    }
    private  void commentU(ArticleDto article){
        long searchId;
        String updateContent;
        System.out.println("수정할 댓글의 번호를 입력해주세요: ");
        searchId = sc.nextLong();
        sc.nextLine();
        System.out.println("댓글을 입력해주세요: ");
        updateContent = sc.nextLine();
        CommentDto commentDto = new CommentDto(searchId,article.getId(),"",updateContent);
        if(commentService.updateComment(commentDto)){
            System.out.println("수정 성공");
        }else {
            System.out.println("수정 실패");
        }

    }
    private void commentD(){
        System.out.println("삭제할 댓글의 번호를 입력해주세요 :");
        if(commentService.deleteComment(sc.nextLong())){
            System.out.println("삭제 성공");
        }else {
            System.out.println("삭제 실패");
        }
    }
    private void commentCRUD(ArticleDto article){
        int choice;
        System.out.println("\uD83C\uDFB6\uD83C\uDFB6  댓글 리스트  \uD83C\uDFB6\uD83C\uDFB6");
        if (article.getCommentlist().isEmpty()){
            System.out.println("해당 게시판에는 댓글이 없습니다.");
        }else{
            for (CommentDto comment : article.getCommentlist()){
                System.out.println("\uD83C\uDFF7\uFE0F"+comment.commentId()+"\t"+comment.name()+"\t"+comment.content());
            }
        }
        while (true){
            System.out.println("1.댓글입력  2.댓글수정  3.댓글삭제  4.돌아가기");
            switch (choice = sc.nextInt()){
                case 1 -> commentC(article);
                case 2 -> commentU(article);
                case 3 ->  commentD();
                case 4 -> {return;}
                default -> System.out.println("잘못된 번호를 입력하셨습니다.");
            }
        }
    }
}
