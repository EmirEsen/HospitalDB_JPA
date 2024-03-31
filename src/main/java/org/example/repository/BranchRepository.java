package org.example.repository;

import jakarta.persistence.TypedQuery;
import org.example.entity.Branch;
import org.example.entity.enums.BranchType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BranchRepository extends RepositoryManager<Branch, Long> {

    public BranchRepository() {
        super(Branch.class);
    }

    //En çok randevu alan 3 branşı listeleyin
    public void getBranchAppointmentCount() {
        Map<BranchType, Long> branchCountMap = new LinkedHashMap<>();

        String queryString = "SELECT b.branchType, COUNT(a) " +
                "FROM Appointment a JOIN Doctor d ON d.id = a.doctor_id " +
                "JOIN Branch b ON b.id = d.branch_id " +
                "GROUP BY b.branchType " +
                "ORDER BY COUNT(a) DESC";

        TypedQuery<Object[]> query = getEntityManager().createQuery(queryString, Object[].class);

        List<Object[]> results = query.getResultList();
        // Iterating over the results to populate the branchCountMap
        for (Object[] result : results) {
            BranchType branchType = (BranchType) result[0];
            Long appointmentCount = (Long) result[1];
            branchCountMap.put(branchType, appointmentCount);
        }

        System.out.printf("--------------------------------%n");
        System.out.printf("Branch Appointment Distribution %n");
        System.out.printf("--------------------------------%n");
        System.out.printf("| %-13s | %-10s |%n", "BRANCH", "#APPOINTMENTS");
        System.out.printf("--------------------------------%n");
        for (Map.Entry<BranchType, Long> entry : branchCountMap.entrySet()) {
            System.out.printf("| %-13s | %-8s |%n", entry.getKey().name(), entry.getValue());
        }
        System.out.printf("--------------------------------%n");
    }


}
