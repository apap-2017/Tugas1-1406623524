package com.example.demo.dao;
import java.util.ArrayList;

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
public interface KeluargaMapper {
	@Select("Select * from kota "
			+ "where id=#{id}")
	@Results(value = {		
			@Result (property = "namaKota", column = "nama_kota")			
	})
	KotaModel selectKota(@Param("id") String id);
		
	@Select("SELECT distinct kecamatan.id, kecamatan.id_kota as id_kota, kecamatan.nama_kecamatan as nama_kecamatan, "
			+ "kecamatan.kode_kecamatan as kode_kecamatan "
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
	
	@Select("SELECT distinct kelurahan.id, kelurahan.id_kecamatan as id_kecamatan, kelurahan.nama_kelurahan as nama_kelurahan "
			+ "from kelurahan "
			+ "join keluarga on kelurahan.id = keluarga.id_kelurahan "
			+ "where kelurahan.id=#{idKelurahan}")
	@Results(value = {		
			@Result (property = "idKecamatan", column = "id_kecamatan"),
			@Result (property = "namaKelurahan", column = "nama_kelurahan"),
			@Result (property = "kecamatan", column = "id_kecamatan",javaType = KecamatanModel.class,many = @Many(select="selectKecamatan"))
	})
	KelurahanModel selectKelurahan(@Param("idKelurahan") String idKelurahan);
	
	@Select("SELECT distinct keluarga.id, keluarga.id_kelurahan as id_kelurahan, keluarga.alamat as alamat, "
			+ "keluarga.rt as rt, keluarga.rw as rw, keluarga.nomor_kk as nomor_kk "
			+ "from keluarga "
			+ "left join penduduk on keluarga.id = penduduk.id_keluarga "
			+ "where keluarga.nomor_kk=#{nkk}")
	@Results(value = {		
			@Result (property = "idKelurahan", column = "id_kelurahan"),
			@Result (property = "alamat", column = "alamat"),
			@Result (property = "rt", column = "rt"),
			@Result (property = "rw", column = "rw"),
			@Result (property = "nomorKK", column = "nomor_kk"),
			@Result (property = "kelurahan", column = "id_kelurahan",javaType = KelurahanModel.class,many = @Many(select="selectKelurahan"))	
	})	
	KeluargaModel selectKeluarga(@Param("nkk") String nkk);
	
	@Select("SELECT p.nama, p.nik as nik, p.jenis_kelamin as jenis_kelamin, p.tempat_lahir as tempat_lahir, p.tanggal_lahir as tanggal_lahir, "
			+ "p.agama as agama, p.pekerjaan as pekerjaan, p.status_perkawinan as status_perkawinan, "
			+ "p.status_dalam_keluarga as status_dalam_keluarga, p.is_wni as is_wni, p.is_wafat as is_wafat "
			+ "FROM penduduk p, keluarga k "
			+ "WHERE k.id=p.id_keluarga and k.nomor_kk =#{nkk}")
	@Results(value = {		
			@Result (property = "jenisKelamin", column = "jenis_kelamin"),
			@Result (property = "tempatLahir", column = "tempat_lahir"),
			@Result (property = "tanggalLahir", column = "tanggal_lahir"),
			@Result (property = "statusPerkawinan", column = "status_perkawinan"),
			@Result (property = "isWafat", column = "is_wafat"),
			@Result (property = "statusDalamKeluarga", column = "status_dalam_keluarga"),
			@Result (property = "isWNI", column = "is_wni")
			
	})
	ArrayList<PendudukModel> selectPendudukByKeluarga(@Param("nkk") String nkk);
	
	@Select("SELECT count(p.id) "
			+ "FROM penduduk p, keluarga k "
			+ "WHERE k.id=#{idKeluarga} and #{isWafat}=1")
	Integer hitungKematian(PendudukModel penduduk);
	
	@Insert("INSERT INTO keluarga ( nomor_kk, alamat, RT, RW, id_kelurahan, is_tidak_berlaku) "
			+ "VALUES (#{nomorKK}, #{alamat}, #{rt}, #{rw}, #{idKelurahan}, 0)")
	void addKeluarga(KeluargaModel keluarga);
	
	@Select("SELECT * FROM keluarga ORDER BY id DESC LIMIT 1")
	@Results(value = {
			@Result (property = "idKelurahan", column = "id_kelurahan"),
			@Result (property = "nomorKK", column = "nomor_kk"),
			@Result (property = "kelurahan", column = "id_kelurahan",javaType = KelurahanModel.class,many = @Many(select="selectKelurahan"))	
	})	
	KeluargaModel lastKeluarga();
	
	@Select("SELECT distinct keluarga.id, keluarga.id_kelurahan as id_kelurahan, keluarga.alamat as alamat, keluarga.rt as rt, keluarga.rw as rw, keluarga.nomor_kk as nomor_kk "
			+ "from keluarga "
			+ "where keluarga.id=#{id}")
	KeluargaModel selectKeluargabyId(@Param("id") String id);
	
	@Update("UPDATE keluarga "
			+ "SET alamat=#{alamat}, RT=#{rt}, RW=#{rw}, id_kelurahan=#{idKelurahan} "
			+ "WHERE nomor_kk = #{nomorKK}")
	void updateKeluarga(KeluargaModel keluarga);
	
	@Update("UPDATE keluarga "
			+ "SET is_tidak_berlaku=#{isTidakBerlaku} "
			+ "WHERE nomor_kk = #{nomorKK}")
	void updateNKK(KeluargaModel keluarga);
}