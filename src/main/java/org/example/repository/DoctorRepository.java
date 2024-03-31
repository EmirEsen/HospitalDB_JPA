package org.example.repository;

import jakarta.persistence.TypedQuery;
import org.example.entity.Doctor;
import org.example.entity.enums.BranchType;

import java.util.Optional;

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
        System.out.printf("%s.%s %s #number of appointments -> %d \n", doctor.getTitle(), doctor.getFirstname(), doctor.getLastname(), query.getResultList().size());

    }

    //Bellirli bir branşta görev yapan doktorlar kimlerdir?
    public void getDoctorsInBranch(BranchType branchType) {
        String queryString = "SELECT d " +
                "FROM Doctor d JOIN Branch b ON d.branch_id = b.id " +
                "WHERE b.branchType = :branch ";

        TypedQuery<Doctor> query = getEntityManager().createQuery(queryString, Doctor.class);
        query.setParameter("branch", branchType);
        System.out.println(branchType.name());
        query.getResultList().forEach(doctor -> {
            System.out.printf("%s.%s %s \n", doctor.getTitle(), doctor.getFirstname(), doctor.getLastname());
        });
    }

    public String findDoctorInfoById(Long id){
        Optional<Doctor> doctor = findById(id);
        String info = "Doctor not found";
        if(doctor.isPresent()){
            info =  "%s.%s %s".formatted(doctor.get().getTitle(), doctor.get().getFirstname(), doctor.get().getLastname());
        }
        return info;
    }
}
