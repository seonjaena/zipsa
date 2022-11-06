package com.project.zipsa.entity;

import com.project.zipsa.entity.common.AuditDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "NO_PAYMENT")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoPayment extends AuditDateTime {

    @Id
    @Column(name = "NO_PAYMENT_IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noPaymentIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_IDX")
    private Room roomIdx;

    @Column(name = "BASIS_DATE")
    private LocalDate basisDate;

}
