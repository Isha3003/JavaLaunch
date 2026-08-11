package com.javalaunch.repository;

import com.javalaunch.entity.Deployment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeploymentRepository extends JpaRepository<Deployment, Long> {

}