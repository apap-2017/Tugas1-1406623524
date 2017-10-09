package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	private Integer id;
	private Integer idKelurahan;
	private String nomorKK;
	private String alamat;
	private String rt;
	private String rw;
	private Integer isTidakBerlaku;
	private KelurahanModel kelurahan;
}
