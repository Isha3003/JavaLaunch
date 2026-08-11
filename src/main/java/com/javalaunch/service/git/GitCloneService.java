package com.javalaunch.service.git;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class GitCloneService {

    public File cloneRepository(String githubUrl) {

        try {

            String repoName = githubUrl.substring(githubUrl.lastIndexOf("/") + 1);

            if (repoName.endsWith(".git")) {
                repoName = repoName.substring(0, repoName.length() - 4);
            }

            File projectDirectory = new File("projects/" + repoName);

            if (projectDirectory.exists()) {
                return projectDirectory;
            }

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "git",
                    "clone",
                    githubUrl,
                    projectDirectory.getAbsolutePath()
            );

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                return projectDirectory;
            }

            throw new RuntimeException("Git clone failed.");

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}