package com.project.zipsa.entity;

import com.project.zipsa.entity.enums.CHECK_CODE_TYPE;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CHECK_CODE")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckCode {

    @Id
    @Column(name = "CHECK_CODE_IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkCodeIdx;

    @Column(name = "CHECK_CODE")
    private String checkCode;

    @Column(name = "CHECK_CODE_TYPE")
    @Convert(converter = CHECK_CODE_TYPE.Converter.class)
    private CHECK_CODE_TYPE checkCodeType;

    @Column(name = "DEVICE")
    private String device;

    public CheckCode(String checkCode, CHECK_CODE_TYPE checkCodeType, String device) {
        this.checkCode = checkCode;
        this.checkCodeType = checkCodeType;
        this.device = device;
    }

}
