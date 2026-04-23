package articleProject;

import articleProject.view.ArticleView;
import articleProject.db.DBConn;

import java.util.Scanner;

public class ArticleMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArticleView articleView = new ArticleView();
        int choice;
        while (true){
            System.out.println("게시글 리스트");
            System.out.println("0.전체보기\t 1.새글\t 2.자세히보기\t 3.게시글삭제\t 4.수정\t 5.종료");
            switch (choice=sc.nextInt()){
                case 0-> articleView.showAll();
                case 1-> articleView.showNewArticle();
                case 2-> articleView.showDetail();
                case 3-> articleView.showDelete();
                case 4-> articleView.showUpdate();
                case 5->{return;}
            }
        }
    }
}
