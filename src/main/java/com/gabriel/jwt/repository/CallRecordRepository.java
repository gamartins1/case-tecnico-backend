package com.gabriel.jwt.repository;

import com.gabriel.jwt.model.CallRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallRecordRepository extends JpaRepository<CallRecord, Long> {

}
