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
public interface PendudukMapper {

	@Select("Select * from kota " + "where id=#{id}")
	@Results(value = { @Result(property = "namaKota", column = "nama_kota"),
			@Result(property = "kodeKota", column = "kode_kota") })
	KotaModel selectKota(@Param("id") String id);

	@Select("SELECT distinct kecamatan.id, kecamatan.id_kota as id_kota, kecamatan.nama_kecamatan as nama_kecamatan, "
			+ "kecamatan.kode_kecamatan as kode_kecamatan "
			+ "from kecamatan " + "join kota on kecamatan.id_kota=kota.id " + "where kecamatan.id=#{idKecamatan}")
	@Results(value = { @Result(property = "idKecamatan", column = "id_kecamatan"),
			@Result(property = "namaKecamatan", column = "nama_kecamatan"),
			@Result(property = "kodeKecamatan", column = "kode_kecamatan"),
			@Result(property = "kota", column = "id_kota", javaType = KotaModel.class, many = @Many(select = "selectKota")) })
	KecamatanModel selectKecamatan(@Param("idKecamatan") String idKecamatan);

	@Select("SELECT distinct kelurahan.id, kelurahan.id_kecamatan as id_kecamatan, kelurahan.nama_kelurahan as nama_kelurahan, "
			+ "kelurahan.kode_kelurahan as kode_kelurahan "
			+ "from kelurahan " + "join keluarga on kelurahan.id = keluarga.id_kelurahan "
			+ "where kelurahan.id=#{idKelurahan}")
	@Results(value = { @Result(property = "idKecamatan", column = "id_kecamatan"),
			@Result(property = "namaKelurahan", column = "nama_kelurahan"),
			@Result(property = "kodeKelurahan", column = "kode_kelurahan"),
			@Result(property = "kecamatan", column = "id_kecamatan", javaType = KecamatanModel.class, many = @Many(select = "selectKecamatan")) })
	KelurahanModel selectKelurahan(@Param("idKelurahan") String idKelurahan);

	@Select("SELECT distinct keluarga.id, keluarga.id_kelurahan as id_kelurahan, keluarga.alamat as alamat, keluarga.rt as rt, "
			+ "keluarga.rw as rw,keluarga.nomor_kk as nomor_kk "
			+ "from keluarga " + "join penduduk on keluarga.id = penduduk.id_keluarga "
			+ "where keluarga.id=#{idKeluarga}")
	@Results(value = { @Result(property = "idKelurahan", column = "id_kelurahan"),
			@Result(property = "alamat", column = "alamat"), @Result(property = "rt", column = "rt"),
			@Result(property = "rw", column = "rw"), @Result(property = "nomorKK", column = "nomor_kk"),
			@Result(property = "kelurahan", column = "id_kelurahan", javaType = KelurahanModel.class, many = @Many(select = "selectKelurahan")) })
	KeluargaModel selectKeluarga(@Param("idKeluarga") String idKeluarga);

	@Select("select * from penduduk where nik = #{nik}")
	@Results(value = { @Result(property = "tempatLahir", column = "tempat_lahir"),
			@Result(property = "tanggalLahir", column = "tanggal_lahir"),
			@Result(property = "golonganDarah", column = "golongan_darah"),
			@Result(property = "jenisKelamin", column = "jenis_kelamin"),
			@Result(property = "idKeluarga", column = "id_keluarga"),
			@Result(property = "statusDalamKeluarga", column = "status_dalam_keluarga"),
			@Result(property = "statusPerkawinan", column = "status_perkawinan"),
			@Result(property = "isWNI", column = "is_wni"), @Result(property = "isWafat", column = "is_wafat"),
			@Result(property = "keluarga", column = "id_keluarga", javaType = KeluargaModel.class, many = @Many(select = "selectKeluarga")) })
	PendudukModel selectPenduduk(@Param("nik") String nik);

	@Insert("INSERT INTO penduduk (nik, nama, tempat_lahir, tanggal_lahir, golongan_darah, agama, jenis_kelamin, "
			+ "pekerjaan, is_wni, is_wafat, status_dalam_keluarga, status_perkawinan, id_keluarga) "
			+ "VALUES ('tes', #{nama}, #{tempatLahir}, #{tanggalLahir}, #{golonganDarah}, #{agama}, #{jenisKelamin}, "
			+ "#{pekerjaan}, #{isWNI}, #{isWafat}, #{statusDalamKeluarga}, #{statusPerkawinan}, #{idKeluarga})")
	void addPenduduk(PendudukModel penduduk);

	@Update("UPDATE penduduk SET nik = #{nik} where penduduk.id=#{id}")
	void updateNIK(PendudukModel penduduk);

	@Update("UPDATE penduduk "
			+ "SET nama=#{nama}, tempat_lahir=#{tempatLahir}, tanggal_lahir=#{tanggalLahir}, "
			+ "golongan_darah=#{golonganDarah}, agama=#{agama}, jenis_kelamin=#{jenisKelamin}, pekerjaan=#{pekerjaan}, "
			+ "is_wni=#{isWNI}, status_dalam_keluarga=#{statusDalamKeluarga}, status_perkawinan=#{statusPerkawinan}, id_keluarga=#{idKeluarga} "
			+ "WHERE nik = #{nik}")
	void updatePenduduk(PendudukModel penduduk);

	@Update("UPDATE penduduk " + "SET is_wafat= #{isWafat} " + "WHERE nik = #{nik}")
	void updateKematian(PendudukModel penduduk);

	@Select("SELECT * FROM penduduk ORDER BY id DESC LIMIT 1")
	@Results(value = { @Result(property = "tempatLahir", column = "tempat_lahir"),
			@Result(property = "tanggalLahir", column = "tanggal_lahir"),
			@Result(property = "golonganDarah", column = "golongan_darah"),
			@Result(property = "jenisKelamin", column = "jenis_kelamin"),
			@Result(property = "idKeluarga", column = "id_keluarga"),
			@Result(property = "statusDalamKeluarga", column = "status_dalam_keluarga"),
			@Result(property = "statusPerkawinan", column = "status_perkawinan"),
			@Result(property = "isWNI", column = "is_wni"), @Result(property = "isWafat", column = "is_wafat") })
	PendudukModel lastPenduduk();

	@Select("Select * from kota order by nama_kota")
	@Results(value = { @Result(property = "id", column = "id"), @Result(property = "namaKota", column = "nama_kota"),
			@Result(property = "kodeKota", column = "kode_kota") })
	ArrayList<KotaModel> selectListKota();

	@Select("SELECT distinct kecamatan.id, kecamatan.id_kota as id_kota, kecamatan.nama_kecamatan as nama_kecamatan, kecamatan.kode_kecamatan as kode_kecamatan "
			+ "from kecamatan " + "join kota on kecamatan.id_kota=kota.id order by nama_kecamatan")
	@Results(value = { @Result(property = "id", column = "id"),
			@Result(property = "idKecamatan", column = "id_kecamatan"),
			@Result(property = "namaKecamatan", column = "nama_kecamatan"),
			@Result(property = "kodeKecamatan", column = "kode_kecamatan") })
	ArrayList<KecamatanModel> selectListKecamatan(@Param("idKota") Integer idKota);

	@Select("SELECT distinct kelurahan.id, kelurahan.id_kecamatan as id_kecamatan, kelurahan.nama_kelurahan as nama_kelurahan, kelurahan.kode_kelurahan as kode_kelurahan "
			+ "from kelurahan " + "join keluarga on kelurahan.id = keluarga.id_kelurahan order by nama_kelurahan ")
	@Results(value = { @Result(property = "id", column = "id"),
			@Result(property = "idKecamatan", column = "id_kecamatan"),
			@Result(property = "namaKelurahan", column = "nama_kelurahan"),
			@Result(property = "kodeKelurahan", column = "kode_kelurahan") })
	ArrayList<KelurahanModel> selectListKelurahan(@Param("idKecamatan") Integer idKecamatan);

	@Select("select distinct penduduk.nik as nik, penduduk.nama as nama, penduduk.jenis_kelamin as kelamin, kota.nama_kota as nama_kota, kecamatan.nama_kecamatan as nama_kec, kelurahan.nama_kelurahan as nama_kel "
			+ "from penduduk, kecamatan, kelurahan, kota, keluarga " + "where penduduk.id_keluarga = keluarga.id "
			+ "and keluarga.id_kelurahan = kelurahan.id " + "and kelurahan.id_kecamatan = kecamatan.id "
			+ "and kecamatan.id_kota = kota.id "
			+ "and kota.id=#{idKota} and kelurahan.id=#{idKel} and kecamatan.id=#{idKecamatan}")
	@Results(value = { @Result(property = "jenisKelamin", column = "kelamin"),
			@Result(property = "keluarga.kelurahan.kecamatan.kota.namaKota", column = "nama_kota"),
			@Result(property = "keluarga.kelurahan.kecamatan.namaKecamatan", column = "nama_kec"),
			@Result(property = "keluarga.kelurahan.namaKelurahan", column = "nama_kel"),
			})
	ArrayList<PendudukModel> selectListPendudukbyCari(@Param("idKota") String idKota,
			@Param("idKecamatan") String idKecamatan, @Param("idKel") String idKel);

}
