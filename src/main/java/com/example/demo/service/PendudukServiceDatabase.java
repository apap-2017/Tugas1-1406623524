package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.KeluargaMapper;
import com.example.demo.dao.PendudukMapper;
import com.example.demo.model.KecamatanModel;
import com.example.demo.model.KeluargaModel;
import com.example.demo.model.KelurahanModel;
import com.example.demo.model.KotaModel;
import com.example.demo.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {
	@Autowired
	private PendudukMapper pendudukMapper;
	
	@Autowired
	private KeluargaMapper keluargaMapper;
	
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

	@Override
	public void updatePenduduk(PendudukModel penduduk) {
		// TODO Auto-generated method stub
		pendudukMapper.updatePenduduk(penduduk);
	}

	@Override
	public void updateKematian(PendudukModel penduduk) {
		// TODO Auto-generated method stub
		log.info("update kematian "+penduduk);
		pendudukMapper.updateKematian(penduduk);
	}

	@Override
	public Integer hitungKematian(PendudukModel penduduk) {
		// TODO Auto-generated method stub
	return keluargaMapper.hitungKematian(penduduk);
	}

	@Override
	public ArrayList<KotaModel> selectKota() {
		// TODO Auto-generated method stub
		log.info("select kota ");
		return pendudukMapper.selectListKota();
	}

	@Override
	public ArrayList<KecamatanModel> selectKecamatan(Integer idKota) {
		// TODO Auto-generated method stub
		log.info("select kecamatan "+idKota);
		return pendudukMapper.selectListKecamatan(idKota);
	}

	@Override
	public ArrayList<KelurahanModel> selectKelurahan(Integer idKecamatan) {
		// TODO Auto-generated method stub
		log.info("select kelurahan "+idKecamatan);
		return pendudukMapper.selectListKelurahan(idKecamatan);
	}

	@Override
	public ArrayList<PendudukModel> selectPendudukbyCari(String idKota, String idKecamatan, String idKel) {
		// TODO Auto-generated method stub
		log.info("select kelurahan "+idKota+idKecamatan+idKel);
		return pendudukMapper.selectListPendudukbyCari(idKota, idKecamatan, idKel);
	}
	
}
