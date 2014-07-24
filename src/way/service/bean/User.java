package way.service.bean;

public class User {
	private String name;
	private String pw;
	private String nick;
	private String datetime;
	private char sex;
	private int id;
	private double longi;
	private double lati;

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
	
	public void setLongi(double longi) {
		this.longi = longi;
	}

	public double getLongi() {
		return longi;
	}

	public void setLati(double lati) {
		this.lati = lati;
	}

	public double getLati() {
		return lati;
	}

	@Override
	public String toString() {
		return String.format(
				"[id=%s, name=%s, pw=%s, nick=%s, sex=%s, datetime=%s, longi=%s, lati=%s]", id,
				name, pw, nick, sex, datetime, longi, lati);
	}

	

}