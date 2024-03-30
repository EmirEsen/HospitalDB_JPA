package org.example.repository;

import jakarta.persistence.TypedQuery;
import org.example.entity.Doctor;
import org.example.entity.enums.BranchType;

public class DoctorRepository extends RepositoryManager<Doctor, Long> {

    public DoctorRepository() {
        super(Doctor.class);
    }

    //Adı verilen doktor kaç hasta bakmıştır?
    public void getAppointmentCountOfDoctor(String name) {
        String queryString = "SELECT a " +
                "FROM Appointment a JOIN Doctor d ON a.doctor_id = d.id " +
                "WHERE d.firstname = :name ";

        Doctor doctor = findByColumnAndValue("firstname", name).getFirst();

        TypedQuery<Object[]> query = getEntityManager().createQuery(queryString, Object[].class);
        query.setParameter("name", name);
        System.out.printf("%s.%s %s #number of appointments -> %d", doctor.getTitle(), doctor.getFirstname(), doctor.getLastname(), query.getResultList().size());

    }

    //Bellirli bir branşta görev yapan doktorlar kimlerdir?
    public void getDoctorsInBranch(BranchType branchType) {
        String queryString = "SELECT d " +
                "FROM Doctors d JOIN Doctor d ON a.doctor_id = d.id " +
                "WHERE d.firstname = :name ";


        TypedQuery<Object[]> query = getEntityManager().createQuery(queryString, Object[].class);
        query.setParameter("name", name);


    }


}
