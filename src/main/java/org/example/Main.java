package org.example;

import org.example.entity.Doctor;
import org.example.entity.enums.BranchType;
import org.example.repository.DoctorRepository;
import org.example.repository.PatientRepository;

import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {

//        DemoData demoData = new DemoData();
//        demoData.createDemoData();
        PatientRepository patientRepository = new PatientRepository();
        DoctorRepository doctorRepository = new DoctorRepository();



        //Adı verilen hastanın tüm randevularını listelesin.
        patientRepository.getAppointmentsByTckn("17480836844");

        //Adı verilen hasta Hangi branştan kaç randevu almış, listelesin.
        patientRepository.getPatientAppointmentBranchCount("17480836844");


        //Adı verilen hasta Belirli bir tarih aralığında aldığı tüm randevuları listelensin.
        patientRepository.getPatientAppointmentsBetweenDates("12437352885", LocalDate.of(2024, 4,1), LocalDate.of(2024, 4,10), false);

        //Bir hastanın son ziyaret tarihini döndürün
        patientRepository.getPatientAppointmentsBetweenDates("12437352885", LocalDate.of(2024, 4,1), LocalDate.of(2024, 4,10), true);

        //Adı verilen doktor kaç hasta bakmıştır?
        doctorRepository.getAppointmentCountOfDoctor("Mina");

        //Bellirli bir branşta görev yapan doktorlar kimlerdir?
        doctorRepository.getDoctorsInBranch(BranchType.NEUROLOGY);

        //Bir doktorun belirli bir tarih aralığında kaç randevusu olduğunu listeleyin


//Belirli bir tarih aralığında en çok randevu veren doktoru listeleyen bir metod.
//En çok randevu alan 3 branşı listeleyin
//Belirli bir branşta en çok randevuya sahip hastaları listeleyen bir metod.


    }
}