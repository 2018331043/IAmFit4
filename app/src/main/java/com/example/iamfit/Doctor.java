package com.example.iamfit;

public class Doctor {
    private String AppointmentNumber,Name,Speciality;
    private float Lat,Longi;

    public Doctor(String appointmentNumber, String name, String speciality, float lat, float longi) {
        AppointmentNumber = appointmentNumber;
        Name = name;
        Speciality = speciality;
        Lat = lat;
        Longi = longi;
    }

    public Doctor() {
    }

    public String getAppointmentNumber() {
        return AppointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        AppointmentNumber = appointmentNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String speciality) {
        Speciality = speciality;
    }

    public float getLat() {
        return Lat;
    }

    public void setLat(float lat) {
        Lat = lat;
    }

    public float getLongi() {
        return Longi;
    }

    public void setLongi(float longi) {
        Longi = longi;
    }
}
