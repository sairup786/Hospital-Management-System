package com.hospitalmanagementsystem;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HospitalMain {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Hospital Management System Menu:");
            System.out.println("1. Add new Doctor");
            System.out.println("2. Add new Patient");
            System.out.println("3. Delete Doctor");
            System.out.println("4. Delete Patient");
            System.out.println("5. Assign Patient to Doctor");
            System.out.println("6. Show all Doctors");
            System.out.println("7. Show all Patients");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    addNewDoctor(scanner);
                    break;
                case 2:
                    addNewPatient(scanner);
                    break;
                case 3:
                    deleteDoctor(scanner);
                    break;
                case 4:
                    deletePatient(scanner);
                    break;
                case 5:
                    assignPatientToDoctor(scanner);
                    break;
                case 6:
                    showAllDoctors();
                    break;
                case 7:
                    showAllPatients();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void addNewDoctor(Scanner scanner) {
        System.out.println("Enter Doctor details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Specialization: ");
        String specialization = scanner.nextLine();

        System.out.print("Degree: ");
        String degree = scanner.nextLine();

        System.out.print("Consultant Charges: ");
        int consultantCharges = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        System.out.print("Address: ");
        String address = scanner.nextLine();

        Doctor doctor = new Doctor();
        doctor.setName(name);
        doctor.setSpecialization(specialization);
        doctor.setDegree(degree);
        doctor.setConsultantCharges(consultantCharges);
        doctor.setAddress(address);

        saveEntity(doctor);
        System.out.println("Doctor added successfully.");
    }

    private static void addNewPatient(Scanner scanner) {
        System.out.println("Enter Patient details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        System.out.print("Disease: ");
        String disease = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        Patient patient = new Patient();
        patient.setName(name);
        patient.setAge(age);
        patient.setDisease(disease);
        patient.setAddress(address);

        saveEntity(patient);
        System.out.println("Patient added successfully.");
    }

    private static void deleteDoctor(Scanner scanner) {
        System.out.print("Enter Doctor ID to delete: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        Doctor doctor = getEntityById(Doctor.class, doctorId);
        if (doctor != null) {
            deleteEntity(doctor);
            System.out.println("Doctor deleted successfully.");
        } else {
            System.out.println("Doctor not found with ID: " + doctorId);
        }
    }

    private static void deletePatient(Scanner scanner) {
        System.out.print("Enter Patient ID to delete: ");
        int patientId = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        Patient patient = getEntityById(Patient.class, patientId);
        if (patient != null) {
            deleteEntity(patient);
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("Patient not found with ID: " + patientId);
        }
    }

    private static void assignPatientToDoctor(Scanner scanner) {
        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        Patient patient = getEntityById(Patient.class, patientId);
        if (patient == null) {
            System.out.println("Patient not found with ID: " + patientId);
            return;
        }

        System.out.print("Enter Doctor ID to assign the patient: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        Doctor doctor = getEntityById(Doctor.class, doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found with ID: " + doctorId);
            return;
        }

        patient.setDoctor(doctor);
        saveEntity(patient);
        System.out.println("Patient assigned to Doctor successfully.");
    }

    private static void showAllDoctors() {
        List<Doctor> doctors = getAllEntities(Doctor.class);
        if (doctors.isEmpty()) {
            System.out.println("No Doctors found.");
        } else {
            System.out.println("List of Doctors:");
            for (Doctor doctor : doctors) {
                System.out.println(doctor);
            }
        }
    }

    private static void showAllPatients() {
        List<Patient> patients = getAllEntities(Patient.class);
        if (patients.isEmpty()) {
            System.out.println("No Patients found.");
        } else {
            System.out.println("List of Patients:");
            for (Patient patient : patients) {
                System.out.println(patient);
            }
        }
    }

    private static void saveEntity(Object entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
     //   SessionFactory factory = null;
 
        try {
     //   	factory = new Configuration().configure("connection.xml").buildSessionFactory();
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void deleteEntity(Object entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static <T> T getEntityById(Class<T> entityClass, int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        T entity = session.get(entityClass, id);
        session.close();
        return entity;
    }

    private static <T> List<T> getAllEntities(Class<T> entityClass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<T> entities = session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
        session.close();
        return entities;
    }
}


