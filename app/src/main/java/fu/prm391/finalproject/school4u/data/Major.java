package fu.prm391.finalproject.school4u.data;

public class Major {
    private String name;
    private String kind;
    private String description;

    public Major() {
    }

    public Major(String name, String kind, String description) {
        this.name = name;
        this.kind = kind;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
