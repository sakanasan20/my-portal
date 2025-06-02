package com.niq_dev.portal.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppSystemDto {

    private Long id;

    private String code;

    private String name;

    private String description;

    private List<AppModuleDto> modules;
	
}
