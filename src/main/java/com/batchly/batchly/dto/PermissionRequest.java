package com.batchly.batchly.dto;

import lombok.Data;

@Data
public class PermissionRequest {
    private Long roleId;
    private String moduleName;
    private boolean canCreate;
    private boolean canRead;
    private boolean canUpdate;
    private boolean canDelete;
}