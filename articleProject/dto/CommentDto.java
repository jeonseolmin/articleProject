package articleProject.dto;
import articleProject.entity.Comment;

public record CommentDto(
        Long commentId,
        Long articleId,
        String name,
        String content
) {
    public static CommentDto fromEntity(Comment comment){
        return new CommentDto(
                comment.getCommentId(),
                comment.getArticleId(),
                comment.getName(),
                comment.getContent()
        );
    }
    public static Comment fromDTO(CommentDto dto){
        return new Comment(
           dto.commentId(), dto.articleId(), dto.name(), dto.content()
        );
    }

    @Override
    public String toString() {
        return "\t🏷️ "  +
                commentId +
                "\t" + name + " "+
                "\t" + content + " " ;
    }
}
