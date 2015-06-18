package com.donbaka.reklameuser.model;

public class Reklame { 
	
	public static final String ID_REKLAME = "ID Reklame"; 
	public static final String TGL_VERIFIKASI = "Tgl Verifikasi UPTSA"; 
	public static final String NAMA_PEMOHON = "Nama Pemohon"; 
	public static final String JENIS_BILLBOARD = "Jenis Billboard"; 
	public static final String JENIS_PRODUK = "Jenis Produk"; 
	public static final String LETAK_REKLAME = "Letak Reklame"; 
	public static final String STATUS_TANAH = "Status Tanah"; 
	public static final String LOKASI_PENEMPATAN = "Lokasi Penempatan"; 
	public static final String BATAS_IJIN = "Batas Ijin";
	public static final String ALAMAT = "Alamat"; 
	public static final String KECAMATAN = "Kecamatan"; 
	public static final String NIPR = "NIPR"; 
	public static final String SUDUT_PANDANG = "Sudut Pandang"; 
	public static final String UKURAN_REKLAME = "Ukuran Reklame"; 
	
	public static final String KETINGGIAN = "Ketinggian"; 
	public static final String LEBAR = "Lebar"; 
	public static final String PANJANG = "Panjang"; 
	public static final String LUAS = "Luas";
	public static final String TEXT = "Text";
	
    private String idInti;
    private String idReklame;
	private String nama;
    private String thumbnailUrl;
	private String status;
	private String alamat;
	private String no_form;
    private String tanggalVerifikasi;
    private String jenisBillboard;
    private String jenisProduk;
    private String letak;
    private String statusTanah;
    private String lokasi;
    private String batasIjin;
    private String kecamatan;
    private String nipr;
    private String sudutPandang;
    private double longitude;
    private double latitude;
    private int ukuranReklame;
    private String[] ketinggian;
    private String[] luas;
    private String[] panjang;
    private String[] lebar;
    private String[] text;


	public Reklame() {
	}

	public Reklame(String idInti,
                   String idReklame,
                   String nama,
                   String thumbnailUrl,
                   String status,
                   String alamat,
                   String no_form,
                   String tanggalVerifikasi,
                   String jenisBillboard,
                   String jenisProduk,
                   String letak,
                   String statusTanah,
                   String lokasi,
                   String batasIjin,
                   String kecamatan,
                   String nipr,
                   String sudutPandang,
                   double longitude,
                   double latitude)
    {
		this.setIdInti(idInti);
        this.setIdReklame(idReklame);
        this.nama = nama;
		this.thumbnailUrl = thumbnailUrl;
		this.status = status;
		this.alamat = alamat;
		this.no_form = no_form;
        this.setTanggalVerifikasi(tanggalVerifikasi);
        this.setJenisBillboard(jenisBillboard);
        this.setJenisProduk(jenisProduk);
        this.setLetak(letak);
        this.setStatusTanah(statusTanah);
        this.setLokasi(lokasi);
        this.setBatasIjin(batasIjin);
        this.setKecamatan(kecamatan);
        this.setNipr(nipr);
        this.setSudutPandang(sudutPandang);
        this.setLongitude(longitude);
        this.setLatitude(latitude);
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getNo_form() {
		return no_form;
	}

	public void setNo_form(String no_form) {
		this.no_form = no_form;
	}

    public String getTanggalVerifikasi() {
        return tanggalVerifikasi;
    }

    public void setTanggalVerifikasi(String tanggalVerifikasi) {
        this.tanggalVerifikasi = tanggalVerifikasi;
    }

    public String getJenisBillboard() {
        return jenisBillboard;
    }

    public void setJenisBillboard(String jenisBillboard) {
        this.jenisBillboard = jenisBillboard;
    }

    public String getJenisProduk() {
        return jenisProduk;
    }

    public void setJenisProduk(String jenisProduk) {
        this.jenisProduk = jenisProduk;
    }

    public String getLetak() {
        return letak;
    }

    public void setLetak(String letak) {
        this.letak = letak;
    }

    public String getStatusTanah() {
        return statusTanah;
    }

    public void setStatusTanah(String statusTanah) {
        this.statusTanah = statusTanah;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getBatasIjin() {
        return batasIjin;
    }

    public void setBatasIjin(String batasIjin) {
        this.batasIjin = batasIjin;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getNipr() {
        return nipr;
    }

    public void setNipr(String nipr) {
        this.nipr = nipr;
    }

    public String getSudutPandang() {
        return sudutPandang;
    }

    public void setSudutPandang(String sudutPandang) {
        this.sudutPandang = sudutPandang;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getIdInti() {
        return idInti;
    }

    public void setIdInti(String idInti) {
        this.idInti = idInti;
    }

    public String getIdReklame() {
        return idReklame;
    }

    public void setIdReklame(String idReklame) {
        this.idReklame = idReklame;
    }

    public int getUkuranReklame() {
        return ukuranReklame;
    }

    public void setUkuranReklame(int ukuranReklame) {
        this.ukuranReklame = ukuranReklame;
    }
    
    public String[] getKetinggian(){
    	return ketinggian;
    }
    
    public void setKetinggian(String[] ketinggian) {
		this.ketinggian = ketinggian;
	}

    public String[] getLuas() {
        return luas;
    }

    public void setLuas(String[] luas) {
        this.luas = luas;
    }

    public String[] getPanjang() {
        return panjang;
    }

    public void setPanjang(String[] panjang) {
        this.panjang = panjang;
    }

    public String[] getLebar() {
        return lebar;
    }

    public void setLebar(String[] lebar) {
        this.lebar = lebar;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }
}
