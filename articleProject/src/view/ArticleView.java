package view;

import dto.ArticleDto;
import dto.CommentDto;
import repository.ArticleRepository;
import service.ArticleService;
import service.CommentService;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ArticleView {
    private final Scanner sc = new Scanner(System.in);
    private final ArticleService articleService = new ArticleService();
    private final  CommentService  commentService = new CommentService();

    public void showAll(){
        List<ArticleDto> articleDto = articleService.all();
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
        String name = sc.nextLine();
        System.out.println("\uD83D\uDE80 Title\t:");
        String title = sc.nextLine();
        System.out.println("\uD83D\uDE80 Content\t:");
        String content = sc.nextLine();
//        System.out.println("\uD83D\uDE80 작성일\t:");
//        String insertedTime = sc.nextLine();
//        System.out.println("\uD83D\uDE80 작성일\t:");
//        String updateTime = sc.nextLine();
        ArticleDto dto = ArticleDto.makeArticleDto(
                null,
                name,
                title,
                content,
                LocalDateTime.now());
        articleService.newArticle(dto);
    }
    public void showDetail(){
        System.out.println("확인할 게시글의 아이디를 입력해주세요 :");
        long id = sc.nextLong();
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
        commentMenu(article);


    }
    public void showDelete(){
        System.out.println("삭제할 게시판의 아이디를 입력해주세요: ");
        long id = sc.nextLong();
        if (id <= 0){
            System.out.println("삭제 실패했습니다. -> 잘못된 ID를 입력하셨습니다.");
            return;
        }
        if(articleService.delete(id)){System.out.println("삭제됐습니다.");}
        else {System.out.println("해당 게시글이 없습니다");}
    }
    public void showUpdate(){
        showAll();
        System.out.println("수정할 게시판의 아이디를 입력해주세요: ");
        long id = sc.nextLong();
        sc.nextLine();
        System.out.println("수정할 제목을 입력해주세요: ");
        String title = sc.nextLine();
        System.out.println("수정할 내용을 입력해주세요: ");
        String content = sc.nextLine();
        ArticleDto article = articleService.detail(id);
        if (article == null){
            System.out.println("해당 게시글이 없습니다");
            return;
        }
        articleService.update(new ArticleDto(article.getId(),article.getName(), title, content,LocalDateTime.now()));
    }
    private void addComment(ArticleDto article){
        String name, content;
        System.out.println(" 댓글 등록자 이름: ");
        name = sc.nextLine();
        System.out.println(" 댓글 내용: ");
        content = sc.nextLine();
        CommentDto dto = new CommentDto(null,article.getId(),name,content);
        commentService.addComment(dto);
    }
    private  void updateComment(ArticleDto article){
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
    private void deleteComment(){
        System.out.println("삭제할 댓글의 번호를 입력해주세요 :");
        if(commentService.deleteComment(sc.nextLong())){
            System.out.println("삭제 성공");
        }else {
            System.out.println("삭제 실패");
        }
    }
    private void commentMenu(ArticleDto article){
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
            try {
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice){
                    case 1 -> addComment(article);
                    case 2 -> updateComment(article);
                    case 3 ->  deleteComment();
                    case 4 -> {return;}
                    default -> System.out.println("잘못된 번호를 입력하셨습니다.");
                }
            }
            catch (Exception e){
                System.out.println("숫자만 입력해주세요.");
                sc.nextLine(); // 잘못 입력된 값 제거
            }
        }
    }
}
