package com.example.demo.service;

import java.util.ArrayList;

import com.example.demo.model.KeluargaModel;
import com.example.demo.model.PendudukModel;

public interface KeluargaService {
	KeluargaModel selectKeluargabyNkk(String nkk);

	KeluargaModel selectKeluargabyId(String id);

	KeluargaModel keluargaTerakhir();

	void addKeluarga(KeluargaModel keluarga);
	
	void updateNKK(KeluargaModel keluarga);
	
	void updateKeluarga(KeluargaModel keluarga);

	ArrayList<PendudukModel> selectPendudukbyKeluarga(String nkk);
}
