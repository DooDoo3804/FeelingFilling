package com.example.billing.data.billingDB.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // entity 클래스들이 BaseEntity를 상속할 때 부모의 필드도 컬럼으로 인식하도록 한다.
@EntityListeners(AuditingEntityListener.class) // BaseEntity에 Auditing 기능을 추가. @EntityListener로 객체 상태가 변경될 때 콜백을 리스너 지정. AuditingEntityListener는 JPA 내부에서 엔티티 객체가 생성/변경 되는 것을 감지.
public abstract class BaseEntity {

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate //생성시간 자동 저장.
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate // 마지막 수정 시간 자동 저장
    private LocalDateTime modifiedDate;
}