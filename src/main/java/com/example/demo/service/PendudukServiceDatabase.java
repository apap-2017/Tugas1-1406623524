package com.example.demo.service;

import org.springframework.stereotype.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PendudukMapper;
import com.example.demo.model.KeluargaModel;
import com.example.demo.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {
	@Autowired
	private PendudukMapper pendudukMapper;
	
	public PendudukModel selectPendudukbyNIK(String nik) {		
		log.info("select penduduk with npm {}", nik);
		return pendudukMapper.selectPenduduk(nik);
	}

	@Override
	public void addPenduduk(PendudukModel penduduk) {
		// TODO Auto-generated method stub
		 pendudukMapper.addPenduduk(penduduk);
	}

	@Override
	public PendudukModel pendudukTerakhir() {
		// TODO Auto-generated method stub
		return pendudukMapper.lastPenduduk();
	}

	@Override
	public void updateNIK(PendudukModel penduduk) {
		// TODO Auto-generated method stub
		pendudukMapper.updateNIK(penduduk);
	}
	
	@Override
	public KeluargaModel selectKeluarga(String idKeluarga){
		return pendudukMapper.selectKeluarga(idKeluarga);
	}
	
}
