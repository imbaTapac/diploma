package com.student.rating.entity.log;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @since 1.0
 */
@Entity
@Table(name = "application_log")
public class ApplicationLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "NVARCHAR(56)")
    private LogType logType;

    @Column(nullable = false, columnDefinition = "NVARCHAR(128)")
    private String description;

    @Column(nullable = false, columnDefinition = "NVARCHAR(16)")
    private String ip;

    @Column
    private LocalDateTime date;

    @Column(nullable = false, columnDefinition = "NVARCHAR(32)")
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "NVARCHAR(6)")
    private Colour colour;

    @Column
    private boolean success;

    public ApplicationLog(LogType logType, String description, String ip, LocalDateTime date, String userName, Colour colour, boolean success) {
        this.logType = logType;
        this.description = description;
        this.ip = ip;
        this.date = date;
        this.userName = userName;
        this.colour = colour;
        this.success = success;
    }

    public ApplicationLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ApplicationLog{" +
                "logType=" + logType +
                ", description='" + description + '\'' +
                ", ip='" + ip + '\'' +
                ", date=" + date +
                ", userName='" + userName + '\'' +
                ", colour=" + colour +
                ", success=" + success +
                '}';
    }
}
