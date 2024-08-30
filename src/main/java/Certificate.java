public class Certificate {
    private Integer id;
    private String name;
    private String date;
    private String expireDate;
    private Integer houseId;
    private Integer emailSent;

    public Certificate() {
    }

    // Constructors, getters, setters
    public Certificate(String name, String date, String expireDate) {
        this.name = name;
        this.date = date;
        this.expireDate = expireDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getEmailSent() {
        return emailSent;
    }

    public void setEmailSent(Integer emailSent) {
        this.emailSent = emailSent;
    }
}
