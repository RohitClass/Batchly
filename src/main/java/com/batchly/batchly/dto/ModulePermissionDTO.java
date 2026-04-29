package com.batchly.batchly.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModulePermissionDTO {

    private String moduleName;
    private List<String> permissions;
}