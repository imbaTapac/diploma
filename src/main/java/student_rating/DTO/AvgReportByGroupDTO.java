package student_rating.DTO;

import org.springframework.stereotype.Component;

/**
 * Created by Тарас on 16.06.2018.
 */
@Component
public class AvgReportByGroupDTO {
    Integer number;
    String groupName;
    Double score;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "AvgReportByGroupDTO{" +
                "number=" + number +
                ", groupName='" + groupName + '\'' +
                ", score=" + score +
                '}';
    }
}

