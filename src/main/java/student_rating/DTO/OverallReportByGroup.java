package student_rating.DTO;

import org.springframework.stereotype.Component;

/**
 * Created by Тарас on 17.06.2018.
 */
@Component
public class OverallReportByGroup {
    Long idStudent;
    String studentSurname;
    String studentName;
    String groupName;
    String blockName;
    String subblockName;
    String paragraphName;
    Double score;

    public Long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Long idStudent) {
        this.idStudent = idStudent;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public void setStudentSurname(String studentSurname) {
        this.studentSurname = studentSurname;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getSubblockName() {
        return subblockName;
    }

    public void setSubblockName(String subblockName) {
        this.subblockName = subblockName;
    }

    public String getParagraphName() {
        return paragraphName;
    }

    public void setParagraphName(String paragraphName) {
        this.paragraphName = paragraphName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "OverallReportByGroup{" +
                "idStudent=" + idStudent +
                ", studentSurname='" + studentSurname + '\'' +
                ", studentName='" + studentName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", blockName='" + blockName + '\'' +
                ", subblockName='" + subblockName + '\'' +
                ", paragraphName='" + paragraphName + '\'' +
                ", score=" + score +
                '}';
    }
}
