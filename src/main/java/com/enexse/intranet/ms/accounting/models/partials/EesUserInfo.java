package com.enexse.intranet.ms.accounting.models.partials;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EesUserInfo implements Serializable {

    private String[] defaultAvatar;
    private byte[] avatar;
    private String passwordChangedAt;
    private String collaboratorId;
    private String userType;
    private String language;
    private String website;
    private boolean auth2factory = false;
    private boolean ticketRestaurant = false;
    private String typeTicketRestaurant;
    private String linkedIn;
    private String facebook;
    private String twitter;
    private String instagram;
}
