package com.enexse.intranet.ms.accounting.models.partials;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EesCloudinaryDoc {

    @Id
    private String cloudinaryId;

    private String eesUploadType;
    private String userId;
    private String signature;
    private String format;
    private String resourceType;
    private String secureUrl;
    private String assetId;
    private String versionId;
    private String type;
    private String version;
    private String url;
    private String publicId;
    private List<String> tags;
    private String pages;
    private String folder;
    private String originalFilename;
    private String apiKey;
    private String bytes;
    private String width;
    private String etag;
    private boolean placeholder;
    private String height;
    private String createdAt;
}
