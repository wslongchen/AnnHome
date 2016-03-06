package entity;

/**
 * Created by mrpan on 16/3/5.
 */
public class Dialog {
    String ID;
    String Title;
    String Author;
    String Content;
    byte[] Image;
    boolean IsLock;
    String Date;
    String User;

    public Dialog() {
        ID = "";
        Title = "";
        Author = "";
        Content = "";
        Image = new byte[]{};
        IsLock = false;
        Date = "";
        User = "";
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public boolean isIsLock() {
        return IsLock;
    }

    public void setIsLock(boolean isLock) {
        IsLock = isLock;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }
}
