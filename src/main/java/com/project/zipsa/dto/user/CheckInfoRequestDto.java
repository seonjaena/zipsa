package com.project.zipsa.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckInfoRequestDto {

    private String userId;
    private String userName;
    private LocalDate userBirth;
    private String userPhone;

}
