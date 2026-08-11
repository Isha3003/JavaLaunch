package com.javalaunch.service.build;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class BuildService {

    public boolean isMavenProject(File projectDirectory) {
        return new File(projectDirectory, "pom.xml").exists();
    }

    public boolean isGradleProject(File projectDirectory) {
        return new File(projectDirectory, "build.gradle").exists()
                || new File(projectDirectory, "build.gradle.kts").exists();
    }

    public boolean buildMavenProject(File projectDirectory) {

        try {
             ProcessBuilder processBuilder =
        new ProcessBuilder(
                "cmd",
                "/c",
                "mvn",
                "clean",
                "package"
        );

            processBuilder.directory(projectDirectory);

            processBuilder.inheritIO();

            Process process = processBuilder.start();

            int exitCode = process.waitFor();

            return exitCode == 0;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}