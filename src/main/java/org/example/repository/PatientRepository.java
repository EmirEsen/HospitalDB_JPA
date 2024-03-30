package org.example.repository;

import jakarta.persistence.TypedQuery;
import org.example.entity.Appointment;
import org.example.entity.Patient;
import org.example.entity.enums.BranchType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientRepository extends RepositoryManager<Patient, Long>{
    public PatientRepository() {
        super(Patient.class);
    }

    //Adı verilen hastanın tüm randevularını listelesin.
    public void getAppointmentsByTckn(String tckn){
        String queryString = "SELECT a " +
                "FROM Appointment a JOIN Patient p ON a.patient_id = p.id " +
                "WHERE p.tckn = :tckn ";

        TypedQuery<Appointment> query = getEntityManager().createQuery(queryString, Appointment.class);
        query.setParameter("tckn", tckn);

        List<Patient> patient = findByColumnAndValue("tckn", tckn);
        System.out.printf("----------- APPOINTMENTS ------------%n");
        System.out.printf("      %s  %S  %S  %n", patient.getFirst().getTckn(), patient.getFirst().getFirstname(), patient.getFirst().getLastname());
        System.out.printf("-----------------------------------%n");
        System.out.printf("| %-9s | %-10s | %-6s |%n","Doctor Id", "DATE", "TIME");
        System.out.printf("-----------------------------------%n");

        for (Appointment a: query.getResultList()) {
            System.out.printf("| %-9s | %-10s | %-6s |%n",a.getDoctor_id(), a.getDate(), a.getTime());
        }
        System.out.printf("--------------------------------%n");
    }

    //Adı verilen hasta Hangi branştan kaç randevu almış, listelesin.
    public void getPatientAppointmentBranchCount(String tckn) {
        Map<BranchType, Long> branchCountMap = new HashMap<>();

        String queryString = "SELECT b.branchType, COUNT(a) " +
                "FROM Appointment a JOIN Patient p ON a.patient_id = p.id " +
                "JOIN Doctor d ON d.id = a.doctor_id " +
                "JOIN Branch b ON b.id = d.branch_id " +
                "WHERE p.tckn = :tckn " +
                "GROUP BY b.branchType";

        TypedQuery<Object[]> query = getEntityManager().createQuery(queryString, Object[].class);
        query.setParameter("tckn", tckn);

        List<Object[]> results = query.getResultList();
        // Iterating over the results to populate the branchCountMap
        for (Object[] result : results) {
//            String tckn = (String) result[0];
            BranchType branchType = (BranchType) result[0];
            Long appointmentCount = (Long) result[1];
            branchCountMap.put(branchType, appointmentCount);
        }

        List<Patient> patient = findByColumnAndValue("tckn", tckn);
        System.out.printf("--------------------------------%n");
        System.out.printf("   %s  %S  %S                %n", patient.getFirst().getTckn(), patient.getFirst().getFirstname(), patient.getFirst().getLastname());
        System.out.printf("--------------------------------%n");
        System.out.printf("| %-13s | %-10s |%n", "BRANCH", "#APPOINTMENTS");
        System.out.printf("--------------------------------%n");
        for (Map.Entry<BranchType, Long> entry : branchCountMap.entrySet()) {
            System.out.printf("| %-13s | %-8s |%n", entry.getKey().name(), entry.getValue());
        }
        System.out.printf("--------------------------------%n");
    }



}
