package com.donbaka.reklameuser.model;

public class SimpleReklame extends Model {
	private int idReklameInti; 
	private String title; 
	private String alamat; 
	private String nomorForm;
	private double latitude; 
	private double longitude;
	
	public SimpleReklame() {
		// TODO Auto-generated constructor stub
	}
	
	public int getIdReklameInti() {
		return idReklameInti;
	}

	public void setIdReklameInti(int idReklameInti) {
		this.idReklameInti = idReklameInti;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	
	public String getNomorForm() {
		return nomorForm;
	}
	
	public void setNomorForm(String nomorForm) {
		this.nomorForm = nomorForm;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		String desc = "Reklame: {id: "+id+", id reklame inti: "+idReklameInti+", title: "+title
					 +", alamat: "+alamat+", nomor form: "+nomorForm+", lat: "+latitude+", long: "+longitude+"}";
		return desc;
	}
}
