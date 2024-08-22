import java.util.Date;

public class Certificate {
    private Integer id;
    private String name;
    private Date issueDate;
    private Date expireDate;

    // Constructors, getters, setters
    public Certificate(String name, Date issueDate, Date expireDate) {
        this.name = name;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
