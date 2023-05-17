package com.medicine.dto;

import lombok.Data;

@Data
public class DoctorInfo {

    private String department;
    private int totalCount;
    private int mainCount;
    private int subCount;
    private Double mainRate;
    private Double subRate;
    private Double totalVisits;
    private Double averageEvaluation;
    private Double fee;
}
