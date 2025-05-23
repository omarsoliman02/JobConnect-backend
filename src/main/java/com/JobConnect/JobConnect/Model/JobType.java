package com.JobConnect.JobConnect.Model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Types of employment")
public enum JobType {
    FULL_TIME,
    PART_TIME,
    CONTRACT,
    FREELANCE,
    INTERNSHIP
} 