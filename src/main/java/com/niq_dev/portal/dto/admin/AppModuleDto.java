package com.niq_dev.portal.dto.admin;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppModuleDto {

    private Long id;
    
    private String code;
    
    private String name;
    
    private String description;
    
    private List<AppFeatureDto> features;
    
    private String parentCode;
    
    private Long systemId;
	
}
