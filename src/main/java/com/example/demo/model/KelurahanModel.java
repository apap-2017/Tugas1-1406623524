package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KelurahanModel {
	private Integer id;
	private Integer idKecamatan;
	private String kodeKelurahan;
	private String namaKelurahan;
	private String kodePos;
	private KecamatanModel kecamatan;
}
