package rs.ac.bg.ecookbook;

public class Comment {
    String author;
    String date;
    String time;
    String body;

    public Comment(String author, String date, String time, String body) {
        this.author = author;
        this.date = date;
        this.time = time;
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
