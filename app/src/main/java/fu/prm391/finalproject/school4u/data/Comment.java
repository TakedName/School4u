package fu.prm391.finalproject.school4u.data;

import com.google.firebase.Timestamp;

public class Comment {
    private String userId;
    private String userName;
    private Timestamp date;
    private String content;
    private String id;

    public Comment() {
    }

    public Comment(String userId, String userName, Timestamp date, String content) {
        this.userId = userId;
        this.userName = userName;
        this.date = date;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
