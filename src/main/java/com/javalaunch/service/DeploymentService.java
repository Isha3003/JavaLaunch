package com.javalaunch.service;
import com.javalaunch.dto.DeploymentRequest;
import com.javalaunch.entity.Deployment;
import com.javalaunch.repository.DeploymentRepository;
import com.javalaunch.service.build.BuildService;
import com.javalaunch.service.git.GitCloneService;
import com.javalaunch.service.port.PortService;
import com.javalaunch.service.run.RunService;
import java.util.List;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DeploymentService {

    private final GitCloneService gitCloneService;
    private final BuildService buildService;
    private final PortService portService;
    private final RunService runService;
    private final DeploymentRepository deploymentRepository;
    

public DeploymentService(
        GitCloneService gitCloneService,
        BuildService buildService,
        PortService portService,
        RunService runService,
        DeploymentRepository deploymentRepository
) {
    this.gitCloneService = gitCloneService;
    this.buildService = buildService;
    this.portService = portService;
    this.runService = runService;
    this.deploymentRepository = deploymentRepository;
}

    public String deployProject(DeploymentRequest request) {

    File projectDirectory =
            gitCloneService.cloneRepository(request.getGithubUrl());

    if (buildService.isMavenProject(projectDirectory)) {

        boolean buildSuccess =
                buildService.buildMavenProject(projectDirectory);

        if (!buildSuccess) {
            return "Maven Build Failed";
        }

        File jarFile = runService.findJar(projectDirectory);

int port = runService.runJar(jarFile);

long processId = runService.getProcessId(port);

Deployment deployment = new Deployment();

deployment.setProjectName(request.getProjectName());
deployment.setGithubUrl(request.getGithubUrl());
deployment.setPort(port);
deployment.setStatus("RUNNING");
deployment.setJarPath(jarFile.getAbsolutePath());
deployment.setProcessId(processId);

deploymentRepository.save(deployment);

return "Application Started Successfully on Port " + port;
    }

    return "Project is not a Maven project.";
}
public List<Deployment> getAllDeployments() {
    return deploymentRepository.findAll();
}
public Deployment getDeploymentById(Long id) {

    return deploymentRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("Deployment not found with id: " + id));
}
public String getDeploymentStatus(Long id) {

    Deployment deployment = getDeploymentById(id);

    boolean running = runService.isRunning(deployment.getPort());

    if (running) {
        return "RUNNING";
    }

    return "STOPPED";
}
public String getDeploymentLogs(Long id) {

    Deployment deployment = getDeploymentById(id);

    return runService.getLogs(deployment.getPort());
}
public void stopDeployment(Long id) {

    Deployment deployment = getDeploymentById(id);

    Long processId = deployment.getProcessId();

    if (processId != null) {

        if (runService.isProcessRunning(processId)) {

            runService.stopProcessById(processId);

        }

        deployment.setProcessId(null);
    }

    deployment.setStatus("STOPPED");

    deploymentRepository.save(deployment);
}

public void restartDeployment(Long id) {

    Deployment deployment = getDeploymentById(id);

    if (deployment.getProcessId() != null) {

        long oldProcessId = deployment.getProcessId();

        ProcessHandle processHandle =
                ProcessHandle.of(oldProcessId).orElse(null);

        if (processHandle != null && processHandle.isAlive()) {

            runService.stopProcessById(oldProcessId);
        }
    }

    File jarFile = new File(deployment.getJarPath());

    int newPort = runService.runJar(jarFile);

    long newProcessId = runService.getProcessId(newPort);

    deployment.setPort(newPort);
    deployment.setProcessId(newProcessId);
    deployment.setStatus("RUNNING");

    deploymentRepository.save(deployment);
}
public void deleteDeployment(Long id) {

    Deployment deployment = getDeploymentById(id);

    if (deployment.getProcessId() != null) {

        runService.stopProcessById(
                deployment.getProcessId()
        );
    }

    deploymentRepository.deleteById(id);
}
}