package org.example.repository;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.entity.Appointment;
import org.example.entity.Doctor;
import org.example.entity.enums.BranchType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
        System.out.printf("%s.%s %s total appointments -> %d \n", doctor.getTitle(), doctor.getFirstname(), doctor.getLastname(), query.getResultList().size());

    }

    //Bellirli bir branşta görev yapan doktorlar kimlerdir?
    public void getDoctorsInBranch(BranchType branchType) {
        String queryString = "SELECT d " +
                "FROM Doctor d JOIN Branch b ON d.branch_id = b.id " +
                "WHERE b.branchType = :branch ";

        TypedQuery<Doctor> query = getEntityManager().createQuery(queryString, Doctor.class);
        query.setParameter("branch", branchType);
        System.out.println(branchType.name() + " Doctors; ");
        query.getResultList().forEach(doctor -> {
            System.out.printf("%S.%s %S \n", doctor.getTitle(), doctor.getFirstname(), doctor.getLastname());
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

    //Bir doktorun belirli bir tarih aralığında kaç randevusu olduğunu listeleyin
    public void getDoctorAppointmentsBetweenDates(String name, LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Appointment> cr = criteriaBuilder.createQuery(Appointment.class);
        Root<Appointment> root = cr.from(Appointment.class);

        List<Doctor> doctor = findByColumnAndValue("firstname", name);

        cr.select(root).where(criteriaBuilder.and(criteriaBuilder.between(root.get("date"), startDate, endDate),
                criteriaBuilder.equal(root.get("doctor_id"), doctor.getFirst().getId())));
        cr.orderBy(criteriaBuilder.asc(root.get("date")));

        List<Appointment> appointments = getEntityManager().createQuery(cr).getResultList();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String start = dtf.format(startDate);
        String end = dtf.format(endDate);
        System.out.printf("--------------------------------------------------------%n");
        System.out.printf("         %S \n " +
                        "   App. Between[%s - %s]%n", findDoctorInfoById(doctor.getFirst().getId()), start, end);
        System.out.printf("--------------------------------------------------------%n");
        System.out.printf("| %-23s | %-12s | %-10s |%n", "Patient", "Date", "Time");
        System.out.printf("--------------------------------------------------------%n");
        for(Appointment a : appointments){
            System.out.printf("| %-23s | %-12s | %-10s | \n",a.getPatient_id() ,a.getDate(), a.getTime());
        }
        System.out.printf("--------------------------------------------------------%n");
    }


    //Belirli bir tarih aralığında en çok randevu veren doktoru listeleyen bir metod.
    public void getAppointmentCountBetweenDates() {
        String queryString = "SELECT a " +
                "FROM Appointment a JOIN Doctor d ON a.doctor_id = d.id " +
                "WHERE d.firstname = :name ";


    }
}
