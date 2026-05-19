package bean;

import java.io.Serializable;
import java.util.Map;

public class TestListSubject implements Serializable {

    private int entYear;
    private String classNum;
    private String studentNo;
    private String studentName;

    private Map<Integer, Integer> points; // no → point

    public int getEntYear() { return entYear; }
    public void setEntYear(int entYear) { this.entYear = entYear; }

    public String getClassNum() { return classNum; }
    public void setClassNum(String classNum) { this.classNum = classNum; }

    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public Map<Integer, Integer> getPoints() { return points; }
    public void setPoints(Map<Integer, Integer> points) { this.points = points; }

    public void putPoint(int no, int point) {
        this.points.put(no, point);
    }
}
