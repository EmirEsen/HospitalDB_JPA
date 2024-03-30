package org.example.repository;

import org.example.entity.Appointment;

public class AppointmentRepository extends RepositoryManager<Appointment, Long>{

    public AppointmentRepository() {
        super(Appointment.class);
    }
}
