package org.example.utility;

import org.example.entity.Appointment;
import org.example.entity.Branch;
import org.example.entity.Doctor;
import org.example.entity.Patient;
import org.example.entity.enums.BranchType;
import org.example.entity.enums.Gender;
import org.example.repository.AppointmentRepository;
import org.example.repository.BranchRepository;
import org.example.repository.DoctorRepository;
import org.example.repository.PatientRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DemoData {

    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private AppointmentRepository appointmentRepository;
    private BranchRepository branchRepository;

    public DemoData() {
        this.doctorRepository = new DoctorRepository();
        this.patientRepository = new PatientRepository();
        this.appointmentRepository = new AppointmentRepository();
        this.branchRepository = new BranchRepository();

    }


    public void createDemoData() {
        createDoctors();
        generateSamplePatients(30);
        generateSampleAppointments(1000);
        createBranches();

    }

    private void createBranches() {
        branchRepository.save(Branch.builder()
                .branchType(BranchType.CARDIOLOGY)
                .build());
        branchRepository.save(Branch.builder()
                .branchType(BranchType.DERMATOLOGY)
                .build());
        branchRepository.save(Branch.builder()
                .branchType(BranchType.NEUROLOGY)
                .build());
        branchRepository.save(Branch.builder()
                .branchType(BranchType.ORTHOPAEDICS)
                .build());
        branchRepository.save(Branch.builder()
                .branchType(BranchType.RADIOLOGY)
                .build());
    }

    private void createDoctors() {
        doctorRepository.save(Doctor.builder()
                .firstname("Emir")
                .lastname("Esen")
                .phone("05300760796")
                .branch_id(1L)
                .title("Dr")
                .build());
        doctorRepository.save(Doctor.builder()
                .firstname("canberk")
                .lastname("Turan")
                .phone("05300760796")
                .branch_id(2L)
                .title("Dr")
                .build());
        doctorRepository.save(Doctor.builder()
                .firstname("Bertan")
                .lastname("Sacbagli")
                .phone("05300760796")
                .branch_id(3L)
                .title("Doc")
                .build());
        doctorRepository.save(Doctor.builder()
                .firstname("Kenan")
                .lastname("Oktener")
                .phone("05300760122")
                .branch_id(4L)
                .title("Dr")
                .build());
        doctorRepository.save(Doctor.builder()
                .firstname("Mina")
                .lastname("Bilici")
                .phone("05300760796")
                .branch_id(5L)
                .title("Dr")
                .build());
        doctorRepository.save(Doctor.builder()
                .firstname("Gokcen")
                .lastname("Caan")
                .phone("05342889312")
                .title("Doc")
                .branch_id(3L)
                .build());
    }

    private static final String[] FIRST_NAMES = {"Ali", "Veli", "Ahmet", "Mehmet", "Ayse", "Fatma", "Zeynep", "Mustafa", "Huseyin", "Osman"};
    private static final String[] LAST_NAMES = {"Yilmaz", "Kaya", "Demir", "Celik", "Arslan", "Koc", "Sahin", "Yıldırım", "Cetin", "Kurt"};

    private void generateSamplePatients(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
            String tckn = generateRandomTCKN();
            String address = "Address" + (i + 1); // Simple address generation
            LocalDate startDate = LocalDate.of(1980, 1, 1);
            LocalDate endDate = LocalDate.of(2002, 12, 31);
            // Generate a random date between the start and end dates
            LocalDate dob = generateRandomDateBetween(startDate, endDate);
            Gender gender = switch (firstName){
                case "Fatma", "Zeynep", "Ayse" -> Gender.FEMALE;
                default -> Gender.MALE;
            };

            patientRepository.save(Patient.builder()
                    .firstname(firstName)
                    .lastname(lastName)
                    .tckn(tckn)
                    .address(address)
                    .dob(dob)
                    .gender(gender)
                    .build());
        }
    }

    private LocalDate generateRandomDateBetween(LocalDate startDate, LocalDate endDate) {
        // Calculate the difference in days between the start and end dates
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);

        // Convert the random epoch day back to a LocalDate
        return LocalDate.ofEpochDay(randomEpochDay);
    }

    private static String generateRandomTCKN() {
        // This is a dummy implementation to generate random TCKN numbers for demonstration purposes
        Random random = new Random();
        StringBuilder tcknBuilder = new StringBuilder("1");
        for (int i = 1; i < 11; i++) {
            tcknBuilder.append(random.nextInt(10));
        }
        return tcknBuilder.toString();
    }


    List<Long> doctorIds = List.of(1L, 2L, 3L, 4L, 5L);
    List<Long> patientIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L, 17L, 18L, 19L, 20L, 21L, 22L, 23L, 24L, 25L, 26L, 27L, 28L, 29L, 30L);

    private void generateSampleAppointments(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            Long doctorId = doctorIds.get(random.nextInt(doctorIds.size()));
            Long patientId = patientIds.get(random.nextInt(patientIds.size()));
            LocalDate startDate = LocalDate.of(2024, 1, 1);
            LocalDate endDate = LocalDate.of(2026, 12, 31);
            // Generate a random date between the start and end dates
            LocalDate date = generateRandomDateBetween(startDate, endDate);
            LocalTime time = LocalTime.of(random.nextInt(24), random.nextInt(60)); // Random time between 00:00 and 23:59

            Appointment appointment = new Appointment();
            appointment.setDoctor_id(doctorId);
            appointment.setPatient_id(patientId);
            appointment.setDate(date);
            appointment.setTime(time);
            appointment.setStatus(1L); // Default status

            appointmentRepository.save(appointment);
        }

    }

}
