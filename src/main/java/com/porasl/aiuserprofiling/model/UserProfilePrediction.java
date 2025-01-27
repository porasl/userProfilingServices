package com.porasl.aiuserprofiling.model;

import java.util.List;

public class UserProfilePrediction {
    private String profile;
    private double confidence;

    public UserProfilePrediction(String profile, double confidence) {
        this.profile = profile;
        this.confidence = confidence;
    }

    public String getProfile() {
        return profile;
    }

    public double getConfidence() {
        return confidence;
    }
}
