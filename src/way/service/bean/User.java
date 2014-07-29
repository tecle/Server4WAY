package way.service.bean;

public class User {
	protected String name;
    protected String pw;
    protected String nick;
    protected String datetime;
    protected char sex;
    protected int id;







    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDatetime() {
        return datetime;
    }

    @Override
    public String toString() {
        return String.format(
                "[id=%s, name=%s, pw=%s, nick=%s, sex=%s, datetime=%s]", id,
                name, pw, nick, sex, datetime);
    }
	

}