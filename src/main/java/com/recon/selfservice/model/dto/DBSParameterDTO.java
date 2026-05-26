package com.recon.selfservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DBSParameterDTO {
    private Long id;
    private String name;
    private String driver;
    private String dbUrl;
    private Integer port;
    private String username;
    private String password;
    private Boolean useCloakware;
    private String cloakeareUsername;
    private String cloakeareServer;
    private String collumnSeparator;
    private Boolean printNullValues;
    private Boolean useMetadataColumnType;
    private String sql;
    private String sheetName;
}
