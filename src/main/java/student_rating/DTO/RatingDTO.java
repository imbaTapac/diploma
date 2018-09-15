package student_rating.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Тарас on 01.06.2018.
 */
public class RatingDTO implements Serializable{

    Long paragraphId;
    Double score;
    String comment;

    @JsonProperty("paragraphId")
    public Long getParagraphId() {
        return paragraphId;
    }

    @JsonProperty("paragraphId")
    public void setParagraphId(Long paragraphId) {
        this.paragraphId = paragraphId;
    }

    @JsonProperty("score")
    public Double getScore() {
        return score;
    }

    @JsonProperty("score")
    public void setScore(Double score) {
        this.score = score;
    }

    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    @JsonProperty("comment")
    public void setComent(String comment) {
        this.comment = comment;
    }
}
