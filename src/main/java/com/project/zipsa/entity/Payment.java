package com.project.zipsa.entity;

import com.project.zipsa.entity.common.AuditDateTime;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PAYMENT")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends AuditDateTime {

    @Id
    @Column(name = "PAYMENT_IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_IDX")
    private Room roomIdx;

    @Column(name = "IS_CONFIRMED")
    private Boolean isConfirmed;

    @Column(name = "BASIS_DATE")
    private LocalDate basisDate;

}
