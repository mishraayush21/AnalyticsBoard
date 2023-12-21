package com.fitmart.analyticsBoard.controller;

import com.fitmart.analyticsBoard.model.AnalyticsData;
import com.fitmart.analyticsBoard.service.AnalyticsDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class AnalyticsController {

    @Autowired
    private AnalyticsDataService analyticsDataService;

    @GetMapping("/analytics")
    public String showAnalytics(Model model) {
        Map<String, AnalyticsData> analyticsDataList = analyticsDataService.getAllAnalyticsData();

        model.addAttribute("analyticsDataList", analyticsDataList);
        return "analytics";
    }
}
