package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.KeluargaModel;
import com.example.demo.model.PendudukModel;
import com.example.demo.service.KeluargaService;


@Controller
public class KeluargaController {

	@Autowired
	KeluargaService keluargaService;

	@RequestMapping("/keluarga/view")
	public String view(Model model, @RequestParam(value = "nkk", required = false) String nkk) {
		KeluargaModel keluarga = keluargaService.selectKeluargabyNkk(nkk);
		System.out.println(keluarga);
		if (keluarga != null) {
			model.addAttribute("keluarga", keluarga);
			return "viewKeluarga";
		} else {
			model.addAttribute("nkk", nkk);
			return "not-found";
		}
	}

	@RequestMapping("/keluarga/view/{nkk}")
	public String viewPath(Model model, @PathVariable(value = "nkk") String nkk) {
		KeluargaModel keluarga = keluargaService.selectKeluargabyNkk(nkk);
		ArrayList<PendudukModel> pendudukbyKeluarga = keluargaService.selectPendudukbyKeluarga(nkk);
		String kelurahan=keluarga.getKelurahan().getNamaKelurahan();
		String kecamatan=keluarga.getKelurahan().getKecamatan().getNamaKecamatan();
		String kota=keluarga.getKelurahan().getKecamatan().getKota().getNamaKota();
		System.out.println(keluarga);
		System.out.println(kelurahan);
		System.out.println(kecamatan);
		if (keluarga != null) {
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("penduduk", pendudukbyKeluarga);
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kota", kota);
			return "viewKeluarga";
		} else {
			model.addAttribute("nkk", nkk);
			return "not-found";
		}
	}
}
