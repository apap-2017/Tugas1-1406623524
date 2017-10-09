package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel {
	private Integer id;
	private Integer idKeluarga;
	private String nik;
	private String nama;
	private String tempatLahir;
	private String tanggalLahir;
	private Integer jenisKelamin;
	private Integer isWNI;
	private String agama;
	private String pekerjaan;
	private String statusPerkawinan;
	private String statusDalamKeluarga;
	private String golonganDarah;
	private Integer isWafat;
	private KeluargaModel keluarga;
}
