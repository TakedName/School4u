package fu.prm391.finalproject.school4u.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class School implements Serializable {
    private String name;
    private String code;
    private String type;
    private String avatar;
    private String location;
    private String shortDescription;
    private String fullDescription;
//    private String
    private ArrayList<String> pictures;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private List<Comment> comments;

    public School(String name, String code,String type,String avatar, String location, String shortDescription, String fullDescription, ArrayList<String> pictures, List<Comment> comments) {
        this.name = name;
        this.code = code;
        this.avatar=avatar;
        this.location = location;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.pictures = pictures;
        this.comments = comments;
    }

    public School() {
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    public boolean isSatisfied (String s){
        s=s.toLowerCase();
        if (name.toLowerCase().contains(s) || code.toLowerCase().contains(s) || location.toLowerCase().contains(s))
            return true;
        else return false;
    }
}
