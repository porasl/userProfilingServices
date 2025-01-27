package com.porasl.aiuserprofiling.controller;

import com.porasl.aiuserprofiling.model.UserProfileInput;
import com.porasl.aiuserprofiling.model.UserProfilePrediction;
import com.porasl.userprofiling.service.UserProfilingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiling")
public class UserProfilingController {

    private final UserProfilingService userProfilingService;

    public UserProfilingController(UserProfilingService userProfilingService) {
        this.userProfilingService = userProfilingService;
    }

    @PostMapping("/train")
    public String train(@RequestBody List<double[]> data) {
        double[][] features = data.stream().map(arr -> arr.clone()).toArray(double[][]::new);
        int[] labels = data.stream().mapToInt(arr -> (int) arr[arr.length - 1]).toArray();

        userProfilingService.train(features, labels);
        return "Model trained successfully!";
    }

    @PostMapping("/predict")
    public UserProfilePrediction predict(@RequestBody UserProfileInput input) {
        String profile = userProfilingService.predict(input.getFeatures());
        return new UserProfilePrediction(profile, 0.95); // Mock confidence
    }
}
