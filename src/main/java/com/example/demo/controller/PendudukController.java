package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.KecamatanModel;
import com.example.demo.model.KeluargaModel;
import com.example.demo.model.KelurahanModel;
import com.example.demo.model.KotaModel;
import com.example.demo.model.PendudukModel;
import com.example.demo.service.KeluargaService;
import com.example.demo.service.PendudukService;

@Controller
public class PendudukController {

	@Autowired
	PendudukService pendudukService;

	@Autowired
	KeluargaService keluargaService;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/penduduk")
	public String viewHalamanPenduduk(Model model) {
		return "index-penduduk";
	}

	@RequestMapping("/penduduk/tambah")
	public String add(Model model) {
		PendudukModel penduduk = new PendudukModel();
		model.addAttribute("penduduk", penduduk);
		return "form-add-penduduk";
	}

	@PostMapping("/penduduk/tambah/submit")
	public String addSubmit(Model model, @ModelAttribute PendudukModel pendudukModel) {
		//
		pendudukService.addPenduduk(pendudukModel);
		PendudukModel finalPenduduk = pendudukService.pendudukTerakhir();
		KeluargaModel keluarga = pendudukService.selectKeluarga(pendudukModel.getIdKeluarga().toString());
		System.out.println(keluarga);
		System.out.println("fp "+finalPenduduk);
		String kodeCamat = keluarga.getKelurahan().getKecamatan().getKodeKecamatan();
		//
		String[] ultah = pendudukModel.getTanggalLahir().split("-");
		String nikFinal ="";
		//
		if(finalPenduduk.getJenisKelamin()==0){
		nikFinal = kodeCamat.substring(0, kodeCamat.length() - 1) + ultah[0].substring(2, 4) + ultah[1]
				+ ultah[2] + "0001";
		}else{
		int tanggal=Integer.parseInt(ultah[2])+40;
		nikFinal = kodeCamat.substring(0, kodeCamat.length() - 1) + ultah[0].substring(2, 4) + ultah[1]
					+ tanggal+ "0001";
		}
		System.out.println(nikFinal);
		finalPenduduk.setNik(nikFinal);
		pendudukService.updateNIK(finalPenduduk);
		model.addAttribute("nik", nikFinal);
		return "success-add";
	}

	@RequestMapping("/penduduk/view")
	public String view(Model model, @RequestParam(value = "nik", required = false) String nik) {
		PendudukModel penduduk = pendudukService.selectPendudukbyNIK(nik);
		System.out.println(penduduk);
		if (penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			return "viewPenduduk";
		} else {
			model.addAttribute("nik", nik);
			return "not-found";
		}
	}

	@RequestMapping("/penduduk/view/")
	public String viewPath(Model model, @RequestParam(value = "nik", required = false) String nik) {
		PendudukModel penduduk = pendudukService.selectPendudukbyNIK(nik);

		if (penduduk != null) {
			String kelurahan = penduduk.getKeluarga().getKelurahan().getNamaKelurahan();
			String kecamatan = penduduk.getKeluarga().getKelurahan().getKecamatan().getNamaKecamatan();
			String kota = penduduk.getKeluarga().getKelurahan().getKecamatan().getKota().getNamaKota();
			System.out.println(penduduk);
			System.out.println(kelurahan);
			System.out.println(kecamatan);
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kota", kota);
			return "viewPenduduk";
		} else {
			model.addAttribute("nik", nik);
			return "notFound";
		}
	}

	@RequestMapping("/penduduk/ubah")
	public String updateObject(Model model, @RequestParam(value = "nik") String nik) {
		// model.addAttribute("greeting", new Greeting());
		PendudukModel penduduk = pendudukService.selectPendudukbyNIK(nik);
		if (penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			System.out.println("penduduk " + penduduk);
			;
			return "form-update-penduduk";
		} else {
			model.addAttribute("nik", nik);
			return "notFound";
		}
	}

	@PostMapping(value = "/penduduk/ubah/submit")
	public String updateObjectSubmit(Model model, @ModelAttribute PendudukModel penduduk) {
		// PendudukModel pendudukLama =
		// pendudukService.selectPendudukbyNIK(penduduk.getNik());
		System.out.println("penduduk " + penduduk);
		// System.out.println("pendudukLama "+pendudukLama);
		pendudukService.updatePenduduk(penduduk);
		model.addAttribute("penduduk", penduduk);
		return "success-update-penduduk";
	}

	// Mengubah kematian penduduk
	@RequestMapping("/penduduk/{nik}")
	public String updateKematian(Model model, @PathVariable(value = "nik") String nik) {
		// model.addAttribute("greeting", new Greeting());
		PendudukModel penduduk = pendudukService.selectPendudukbyNIK(nik);
		if (penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			System.out.println("penduduk " + penduduk);
			;
			return "update-wafat-penduduk";
		} else {
			model.addAttribute("nik", nik);
			return "notFound";
		}
	}

	@PostMapping(value = "/penduduk/submit")
	public String updateKematian(Model model, @ModelAttribute PendudukModel penduduk) {
		//
		pendudukService.updateKematian(penduduk);
		System.out.println("penduduk edit" + penduduk);
		PendudukModel pendudukLama = pendudukService.selectPendudukbyNIK(penduduk.getNik());
		System.out.println("pendudukLama " + pendudukLama);
		ArrayList<PendudukModel> anggotaKeluarga = keluargaService
				.selectPendudukbyKeluarga(pendudukLama.getKeluarga().getNomorKK());
		System.out.println("anggota keluarga " + anggotaKeluarga);
		int counterKematian = 0;
		for (int i = 0; i < anggotaKeluarga.size(); i++) {
			if (anggotaKeluarga.get(i).getIsWafat() == 1) {
				counterKematian++;
				System.out.println(counterKematian);
				System.out.println("size " + anggotaKeluarga.size());
			}
		}
		if (counterKematian == anggotaKeluarga.size()) {
			System.out.println("semua anggota keluarga mati. NKK tidak berlaku lagi." + counterKematian + " "
					+ anggotaKeluarga.size());
			pendudukLama.getKeluarga().setIsTidakBerlaku(1);
			keluargaService.updateNKK(pendudukLama.getKeluarga());
		}
		//
		model.addAttribute("penduduk", penduduk);
		return "success-update-kematian";
	}

	@RequestMapping("/penduduk/cari")
	public String cariKota(@RequestParam(value = "idKota", required = false) Integer idKota,
			@RequestParam(value = "idKecamatan", required = false) Integer idKecamatan,
			@RequestParam(value = "idKel", required = false) Integer idKel, Model model) {
		ArrayList<KotaModel> listKota = pendudukService.selectKota();
		// String namaKota = null;
		// model.addAttribute("namaKota", namaKota);
		ArrayList<KecamatanModel> listKecamatan = pendudukService.selectKecamatan(idKota);
		ArrayList<KelurahanModel> listKelurahan = pendudukService.selectKelurahan(idKecamatan);
		model.addAttribute("listKota", listKota);
		model.addAttribute("listKecamatan", listKecamatan);
		model.addAttribute("listKelurahan", listKelurahan);
		KotaModel kota = new KotaModel();
		model.addAttribute("kota", kota);
		return "cari-penduduk";
	}

	@RequestMapping("/penduduk/cari/result")
	public String cariKotaResult(@RequestParam(value = "idKota", required = false) Integer idKota,
			@RequestParam(value = "idKecamatan", required = false) Integer idKecamatan,
			@RequestParam(value = "idKel", required = false) Integer idKel, Model model) {
		ArrayList<PendudukModel> listPenduduk = pendudukService.selectPendudukbyCari(idKota.toString(),
				idKecamatan.toString(), idKel.toString());
		String kota=listPenduduk.get(0).getKeluarga().getKelurahan().getKecamatan().getKota().getNamaKota();
		String kecamatan=listPenduduk.get(0).getKeluarga().getKelurahan().getKecamatan().getNamaKecamatan();
		String kelurahan=listPenduduk.get(0).getKeluarga().getKelurahan().getNamaKelurahan();
		System.out.println(kota+kecamatan+kelurahan);
		model.addAttribute("kota", kota);
		model.addAttribute("kec", kecamatan);
		model.addAttribute("kel", kelurahan);
		model.addAttribute("listPenduduk", listPenduduk);
		return "cari-penduduk-result";
	}

}