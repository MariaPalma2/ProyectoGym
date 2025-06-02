package com.mtrain.mtrain.repository;


import com.mtrain.mtrain.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Integer> {}