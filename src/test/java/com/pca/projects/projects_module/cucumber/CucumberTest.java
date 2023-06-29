package com.pca.projects.projects_module.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
        glue = {"com.cucumber.test"},
        features = "x/y/resources")
public class CucumberTest {
}
