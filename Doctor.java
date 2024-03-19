package com.hospitalmanagementsystem;

import javax.persistence.*;

@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String specialization;
    private String degree;
    private int consultantCharges;
    private String address;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public int getConsultantCharges() {
		return consultantCharges;
	}

	public void setConsultantCharges(int consultantCharges) {
		this.consultantCharges = consultantCharges;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor(int id, String name, String specialization, String degree, int consultantCharges, String address
			) {
		super();
		this.id = id;
		this.name = name;
		this.specialization = specialization;
		this.degree = degree;
		this.consultantCharges = consultantCharges;
		this.address = address;
		
	}

	public Doctor() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Doctor [id=" + id + ", name=" + name + ", specialization=" + specialization + ", degree=" + degree
				+ ", consultantCharges=" + consultantCharges + ", address=" + address + ", patient=" + patient + "]";
	}
    
    

    
}
