package com.gabriel.jwt.model;

import static jakarta.persistence.GenerationType.IDENTITY;
import static org.apache.logging.log4j.util.Strings.EMPTY;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "Call_Record")
public class CallRecord {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Id_Record")
    private Long id;
    @Lob
    private String jwt;
    private Boolean isValid;
    @Lob
    private String cause;
    @Column(name = "Record_Date")
    private LocalDateTime recordDateTime;

    public CallRecord() {

    }

    public CallRecord(String jwt) {
        this.jwt = jwt;
        this.recordDateTime = LocalDateTime.now();
        this.cause = EMPTY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public LocalDateTime getRecordDateTime() {
        return recordDateTime;
    }

    public void setRecordDate(LocalDateTime recordDate) {
        this.recordDateTime = recordDate;
    }
}
