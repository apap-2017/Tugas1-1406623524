package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.KeluargaMapper;
import com.example.demo.model.KeluargaModel;
import com.example.demo.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService{
	@Autowired
	private KeluargaMapper keluargaMapper;
	
	public KeluargaModel selectKeluargabyNkk(String nkk){
		log.info("Select keluarga by nkk "+nkk);
		return keluargaMapper.selectKeluarga(nkk);
	}

	@Override
	public ArrayList<PendudukModel> selectPendudukbyKeluarga(String nkk) {
		// TODO Auto-generated method stub
		log.info("Select keluarga by nkk "+nkk);
		return keluargaMapper.selectPendudukByKeluarga(nkk);
	}

	@Override
	public void addKeluarga(KeluargaModel keluarga) {
		// TODO Auto-generated method stub
		keluargaMapper.addKeluarga(keluarga);
	}

	@Override
	public KeluargaModel keluargaTerakhir() {
		// TODO Auto-generated method stub
		return keluargaMapper.lastKeluarga();
	}

	@Override
	public KeluargaModel selectKeluargabyId(String id) {
		// TODO Auto-generated method stub
		log.info("Select keluarga by id "+id);
		return keluargaMapper.selectKeluargabyId(id);
	}
	
	@Override
	public void updateKeluarga(KeluargaModel keluarga) {
		// TODO Auto-generated method stub
		log.info("update Keluarga "+keluarga);
		keluargaMapper.updateKeluarga(keluarga);
	}

	@Override
	public void updateNKK(KeluargaModel keluarga) {
		// TODO Auto-generated method stub
		log.info("update nkk Keluarga "+keluarga);
		keluargaMapper.updateNKK(keluarga);
	}
	
}
