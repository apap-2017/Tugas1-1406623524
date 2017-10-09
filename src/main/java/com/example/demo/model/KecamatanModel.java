package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KecamatanModel {
	private Integer id;
	private Integer idKota;
	private String kodeKecamatan;
	private String namaKecamatan;
	private KotaModel kota;
}
