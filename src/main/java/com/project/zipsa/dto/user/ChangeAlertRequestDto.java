package com.project.zipsa.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeAlertRequestDto {

    private Boolean isAlert;
    private Boolean isMarketingSMS;
    private Boolean isMarketingEmail;

}
