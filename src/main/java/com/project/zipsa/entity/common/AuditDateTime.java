package com.project.zipsa.entity.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditDateTime {

    @CreatedDate
    @Column(name = "CREATE_DATETIME", updatable = false)
    private LocalDateTime createDatetime;

    @LastModifiedDate
    @Column(name = "UPDATE_DATETIME")
    private LocalDateTime updateDatetime;

}
