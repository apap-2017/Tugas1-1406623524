package com.example.demo.service;

import java.util.ArrayList;

import com.example.demo.model.KeluargaModel;
import com.example.demo.model.PendudukModel;

public interface KeluargaService {
	KeluargaModel selectKeluargabyNkk(String nkk);
	ArrayList<PendudukModel> selectPendudukbyKeluarga(String nkk);
}
