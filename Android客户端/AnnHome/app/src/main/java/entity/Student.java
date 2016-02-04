package entity;

/**
 * Created by mrpan on 16/2/4.
 */
import com.google.gson.annotations.Expose;

public class Student {
    @Expose
    private String studentName;
    @Expose
    private int studentId;
    public Student(){}
    public Student(int studentId,String studentName){
        this.setStudentId(studentId);
        this.setStudentName(studentName);
    }
    public String toString(){
        return this.getStudentId() + ":" + this.getStudentName();
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

}
