package org.example.repository;

import jakarta.persistence.TypedQuery;
import org.example.entity.Branch;
import org.example.entity.enums.BranchType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchRepository extends RepositoryManager<Branch, Long> {

    public BranchRepository() {
        super(Branch.class);
    }




}
