package com.example.demo.service;

import java.util.ArrayList;

import com.example.demo.model.KecamatanModel;
import com.example.demo.model.KeluargaModel;
import com.example.demo.model.KelurahanModel;
import com.example.demo.model.KotaModel;
import com.example.demo.model.PendudukModel;

public interface PendudukService {
	PendudukModel selectPendudukbyNIK(String nik);	

	KeluargaModel selectKeluarga(String nik);
	
	void addPenduduk(PendudukModel penduduk);
	
	PendudukModel pendudukTerakhir();
	
	void updateNIK(PendudukModel penduduk);

	void updatePenduduk(PendudukModel penduduk);
	
	void updateKematian(PendudukModel penduduk);
	
	Integer hitungKematian(PendudukModel penduduk);
	
	ArrayList<KotaModel> selectKota();
	
	ArrayList<KecamatanModel> selectKecamatan(Integer idKota);
	
	ArrayList<KelurahanModel> selectKelurahan(Integer idKecamatan);
	
	ArrayList<PendudukModel> selectPendudukbyCari(String idKota, String idKecamatan, String idKel);
}
