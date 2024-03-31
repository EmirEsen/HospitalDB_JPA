package org.example;

import org.example.entity.enums.BranchType;
import org.example.repository.BranchRepository;
import org.example.repository.DoctorRepository;
import org.example.repository.PatientRepository;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

//        DemoData demoData = new DemoData();
//        demoData.createDemoData();
        PatientRepository patientRepository = new PatientRepository();
        DoctorRepository doctorRepository = new DoctorRepository();
        BranchRepository branchRepository = new BranchRepository();

        //Adı verilen hastanın tüm randevularını listelesin.
        patientRepository.getAppointmentsByTckn("17870050859");

        //Adı verilen hasta Hangi branştan kaç randevu almış, listelesin.
        patientRepository.getPatientAppointmentBranchCount("17870050859");

        //Adı verilen hasta Belirli bir tarih aralığında aldığı tüm randevuları listelensin.
        patientRepository.getPatientAppointmentsBetweenDates("17870050859",
                LocalDate.of(2024, 4,1), LocalDate.of(2030, 4,10), false);

        //Bir hastanın son ziyaret tarihini döndürün
        patientRepository.getPatientAppointmentsBetweenDates("17870050859",
                LocalDate.of(2024, 4,1), LocalDate.of(2030, 4,10), true);

        //Adı verilen doktor kaç hasta bakmıştır?
        doctorRepository.getAppointmentCountOfDoctor("Mina");

        //Bellirli bir branşta görev yapan doktorlar kimlerdir?
        doctorRepository.getDoctorsInBranch(BranchType.NEUROLOGY);

        //Bir doktorun belirli bir tarih aralığında kaç randevusu olduğunu listeleyin
        doctorRepository.getDoctorAppointmentsBetweenDates("Emir",
                LocalDate.of(2024, 5, 5), LocalDate.of(2024, 6, 1));

        //Belirli bir tarih aralığında en çok randevu veren doktoru listeleyen bir metod.


        //En çok randevu alan 3 branşı listeleyin
        branchRepository.getBranchAppointmentCount();

        //Belirli bir branşta en çok randevuya sahip hastaları listeleyen bir metod.
        doctorRepository.getDoctorWithMostAppointmentBetweenDates(LocalDate.of(2026, 4, 1), LocalDate.of(2028, 6, 30));


    }
}