package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.KeluargaModel;
import com.example.demo.model.PendudukModel;
import com.example.demo.service.PendudukService;

@Controller
public class PendudukController {

	@Autowired
	PendudukService pendudukService;
	

	@RequestMapping("/penduduk/tambah")
	public String add(Model model) {
		PendudukModel penduduk = new PendudukModel();
		model.addAttribute("penduduk",penduduk);
		return "form-add-penduduk";
	}

	@PostMapping("/penduduk/tambah/submit")
	public String addSubmit(Model model, @ModelAttribute PendudukModel pendudukModel){			
//		PendudukModel penduduk = new PendudukModel(npm, name, Double.parseDouble(gpa), null);
		pendudukService.addPenduduk(pendudukModel);
		PendudukModel finalPenduduk=pendudukService.pendudukTerakhir();
		KeluargaModel keluarga=pendudukService.selectKeluarga(pendudukModel.getIdKeluarga().toString());
		System.out.println(keluarga);
		String kodeKota=keluarga.getKelurahan().getKecamatan().getKota().getKodeKota();
		String kodeCamat=keluarga.getKelurahan().getKecamatan().getKodeKecamatan();
		String kodeLurah=keluarga.getKelurahan().getKodeKelurahan();
		String nikFinal=kodeKota+kodeCamat+kodeLurah+finalPenduduk.getId();
		System.out.println(nikFinal);
//		pendudukModel.setNik(nikFinal);
//		pendudukService.updateNIK(pendudukModel);
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

	@RequestMapping("/penduduk/view/{nik}")
	public String viewPath(Model model, @PathVariable(value = "nik") String nik) {
		PendudukModel penduduk = pendudukService.selectPendudukbyNIK(nik);
		String kelurahan=penduduk.getKeluarga().getKelurahan().getNamaKelurahan();
		String kecamatan=penduduk.getKeluarga().getKelurahan().getKecamatan().getNamaKecamatan();
		String kota=penduduk.getKeluarga().getKelurahan().getKecamatan().getKota().getNamaKota();
		System.out.println(penduduk);
		System.out.println(kelurahan);
		System.out.println(kecamatan);
		if (penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kota", kota);
			return "viewPenduduk";
		} else {
			model.addAttribute("nik", nik);
			return "not-found";
		}
	}
}
