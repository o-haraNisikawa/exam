package bean;

import java.io.Serializable;
import java.time.LocalDate;

public class Student implements Serializable {

    private String no;
    private String name;
    private int entYear;
    private String classNum;
    private boolean isAttend;
    private School school;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEntYear() {
        return entYear;
    }

    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public boolean isAttend() {
        return isAttend;
    }

    public void setAttend(boolean isAttend) {
        this.isAttend = isAttend;
    }

    // ★ JSP の EL 式で ${student.attend} を使えるようにする
    public boolean getAttend() {
        return isAttend;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    /**
     * 学年を年度ベースで計算する（4月始まり）
     */
    public int getSchoolYear() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();

        if (now.getMonthValue() < 4) {
            year--;
        }

        return year - this.entYear + 1;
    }
}

