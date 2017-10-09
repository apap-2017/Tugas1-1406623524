package com.example.demo.service;

import com.example.demo.model.KeluargaModel;
import com.example.demo.model.PendudukModel;

public interface PendudukService {
	PendudukModel selectPendudukbyNIK(String nik);	

	KeluargaModel selectKeluarga(String nik);
	
	void addPenduduk(PendudukModel penduduk);
	
	PendudukModel pendudukTerakhir();
	
	void updateNIK(PendudukModel penduduk);

	
}
