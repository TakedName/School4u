package fu.prm391.finalproject.school4u.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Forum implements Serializable {
    private String authorId;
    private String authorName;
    private String content;
    private ArrayList<Comment> comments;
    private String id;

    public Forum() {
    }

    public Forum(String authorId, String authorName, String content, ArrayList<Comment> comments) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.content = content;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
