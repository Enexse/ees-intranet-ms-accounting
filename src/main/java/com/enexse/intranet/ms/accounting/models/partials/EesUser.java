package com.enexse.intranet.ms.accounting.models.partials;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EesUser implements Serializable {

    private String userId;
    private String keycloakId;
    private String firstName;
    private String lastName;
    private String pseudo;
    private String personalEmail;
    private String departmentCode;
    private String enexseEmail;
    private String status;
    private String gender;
    private String matricule;
    private EesUserInfo eesUserInfo;
}
