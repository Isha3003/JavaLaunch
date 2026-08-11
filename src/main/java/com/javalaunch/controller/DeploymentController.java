package com.javalaunch.controller;

import com.javalaunch.dto.ApiResponse;
import com.javalaunch.dto.DeploymentRequest;
import com.javalaunch.entity.Deployment;
import com.javalaunch.service.DeploymentService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeploymentController {

    private final DeploymentService deploymentService;

    public DeploymentController(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    // DEPLOY
    @PostMapping("/deploy")
    public ApiResponse<String> deploy(
            @RequestBody DeploymentRequest request) {

        String message = deploymentService.deployProject(request);

        return new ApiResponse<>(
                true,
                message,
                null
        );
    }

    // GET ALL DEPLOYMENTS
    @GetMapping("/deployments")
    public ApiResponse<List<Deployment>> getAllDeployments() {

        List<Deployment> deployments =
                deploymentService.getAllDeployments();

        return new ApiResponse<>(
                true,
                "Deployments fetched successfully",
                deployments
        );
    }

    // GET DEPLOYMENT BY ID
    @GetMapping("/deployments/{id}")
    public ApiResponse<Deployment> getDeploymentById(
            @PathVariable Long id) {

        Deployment deployment =
                deploymentService.getDeploymentById(id);

        return new ApiResponse<>(
                true,
                "Deployment fetched successfully",
                deployment
        );
    }

    // GET STATUS
    @GetMapping("/deployments/{id}/status")
    public ApiResponse<String> getDeploymentStatus(
            @PathVariable Long id) {

        String status =
                deploymentService.getDeploymentStatus(id);

        return new ApiResponse<>(
                true,
                "Deployment status fetched successfully",
                status
        );
    }

    // GET LOGS
    @GetMapping("/deployments/{id}/logs")
    public ApiResponse<String> getDeploymentLogs(
            @PathVariable Long id) {

        String logs =
                deploymentService.getDeploymentLogs(id);

        return new ApiResponse<>(
                true,
                "Deployment logs fetched successfully",
                logs
        );
    }

    // STOP
    @PostMapping("/deployments/{id}/stop")
    public ApiResponse<String> stopDeployment(
            @PathVariable Long id) {

        deploymentService.stopDeployment(id);

        return new ApiResponse<>(
                true,
                "Deployment stopped successfully",
                null
        );
    }

    // RESTART
    @PostMapping("/deployments/{id}/restart")
    public ApiResponse<String> restartDeployment(
            @PathVariable Long id) {

        deploymentService.restartDeployment(id);

        return new ApiResponse<>(
                true,
                "Deployment restarted successfully",
                null
        );
    }

    // DELETE
    @DeleteMapping("/deployments/{id}")
    public ApiResponse<String> deleteDeployment(
            @PathVariable Long id) {

        deploymentService.deleteDeployment(id);

        return new ApiResponse<>(
                true,
                "Deployment deleted successfully",
                null
        );
    }
    @GetMapping("/health")
public ResponseEntity<?> healthCheck() {

    return ResponseEntity.ok(
        new ApiResponse<>(
            true,
            "JavaLaunch backend is running",
            "UP"
        )
    );
}
}