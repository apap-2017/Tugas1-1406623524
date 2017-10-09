package com.example.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.model.KecamatanModel;
import com.example.demo.model.KeluargaModel;
import com.example.demo.model.KelurahanModel;
import com.example.demo.model.KotaModel;
import com.example.demo.model.PendudukModel;

@Mapper
public interface PendudukMapper {

	@Select("Select * from kota "
			+ "where id=#{id}")
	@Results(value = {		
			@Result (property = "namaKota", column = "nama_kota"),
			@Result (property = "kodeKota", column = "kode_kota")
	})
	KotaModel selectKota(@Param("id") String id);
	
	
	@Select("SELECT distinct kecamatan.id, kecamatan.id_kota as id_kota, kecamatan.nama_kecamatan as nama_kecamatan, kecamatan.kode_kecamatan as kode_kecamatan "
			+ "from kecamatan "
			+ "join kota on kecamatan.id_kota=kota.id "
			+ "where kecamatan.id=#{idKecamatan}")
	@Results(value = {		
			@Result (property = "idKecamatan", column = "id_kecamatan"),
			@Result (property = "namaKecamatan", column = "nama_kecamatan"),
			@Result (property = "kodeKecamatan", column = "kode_kecamatan"),
			@Result (property = "kota", column = "id_kota",javaType = KotaModel.class,many = @Many(select="selectKota"))
	})
	KecamatanModel selectKecamatan(@Param("idKecamatan") String idKecamatan);
	
	@Select("SELECT distinct kelurahan.id, kelurahan.id_kecamatan as id_kecamatan, kelurahan.nama_kelurahan as nama_kelurahan, kelurahan.kode_kelurahan as kode_kelurahan "
			+ "from kelurahan "
			+ "join keluarga on kelurahan.id = keluarga.id_kelurahan "
			+ "where kelurahan.id=#{idKelurahan}")
	@Results(value = {		
			@Result (property = "idKecamatan", column = "id_kecamatan"),
			@Result (property = "namaKelurahan", column = "nama_kelurahan"),
			@Result (property = "kodeKelurahan", column = "kode_kelurahan"),
			@Result (property = "kecamatan", column = "id_kecamatan",javaType = KecamatanModel.class,many = @Many(select="selectKecamatan"))
	})
	KelurahanModel selectKelurahan(@Param("idKelurahan") String idKelurahan);
	
	@Select("SELECT distinct keluarga.id, keluarga.id_kelurahan as id_kelurahan, keluarga.alamat as alamat, keluarga.rt as rt, keluarga.rw as rw "
			+ "from keluarga "
			+ "join penduduk on keluarga.id = penduduk.id_keluarga "
			+ "where keluarga.id=#{idKeluarga}")
	@Results(value = {		
			@Result (property = "idKelurahan", column = "id_kelurahan"),
			@Result (property = "alamat", column = "alamat"),
			@Result (property = "rt", column = "rt"),
			@Result (property = "rw", column = "rw"),
			@Result (property = "kelurahan", column = "id_kelurahan",javaType = KelurahanModel.class,many = @Many(select="selectKelurahan"))	
	})	
	KeluargaModel selectKeluarga(@Param("idKeluarga") String idKeluarga); 
	
	@Select("select * from penduduk where nik = #{nik}")
	@Results(value = {		
			@Result (property = "tempatLahir", column = "tempat_lahir"),
			@Result (property = "tanggalLahir", column = "tanggal_lahir"),
			@Result (property = "golonganDarah", column = "golongan_darah"),
			@Result (property = "statusPerkawinan", column = "status_perkawinan"),
			@Result (property = "isWNI", column = "is_wni"),
			@Result (property = "isWafat", column = "is_wafat"),
			@Result (property = "keluarga", column = "id_keluarga",javaType = KeluargaModel.class,many = @Many(select="selectKeluarga"))
	})	
	PendudukModel selectPenduduk(@Param("nik") String nik);
	
	@Insert("INSERT INTO penduduk (nik, nama, tempat_lahir, tanggal_lahir, golongan_darah, agama, jenis_kelamin, pekerjaan, is_wni, is_wafat, status_dalam_keluarga, status_perkawinan, id_keluarga) "
			+ "VALUES ('tes', #{nama}, #{tempatLahir}, #{tanggalLahir}, #{golonganDarah}, #{agama}, #{jenisKelamin}, #{pekerjaan}, #{isWNI}, #{isWafat}, #{statusDalamKeluarga}, #{statusPerkawinan}, #{idKeluarga})")
	void addPenduduk(PendudukModel penduduk);
	
	@Update("UPDATE penduduk SET nik = #{nik}")
	void updateNIK(PendudukModel penduduk);
	
	@Select("SELECT * FROM penduduk ORDER BY id DESC LIMIT 1")
	PendudukModel lastPenduduk();
}
