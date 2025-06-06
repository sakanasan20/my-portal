package com.niq_dev.portal.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppFeatureDto {

    private Long id;

    private String code;
    
    private String name;
    
    private String description;
    
    private String parentCode;
    
    private Long moduleId;
	
}
