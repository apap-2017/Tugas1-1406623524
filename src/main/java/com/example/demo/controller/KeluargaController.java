package com.example.demo.controller;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
import com.example.demo.service.KeluargaService;

@Controller
public class KeluargaController {

	@Autowired
	KeluargaService keluargaService;

	@RequestMapping("/keluarga")
	public String viewHalamanKeluargak(Model model) {			
		return "index-keluarga";
	}
	
	@RequestMapping("/keluarga/tambah")
	public String add(Model model) {
		KeluargaModel keluarga = new KeluargaModel();
		model.addAttribute("keluarga", keluarga);
		return "form-add-keluarga";
	}

	@PostMapping("/keluarga/tambah/submit")
	public String addSubmit(Model model, @ModelAttribute KeluargaModel keluargaModel) {
		//
		// Ngambil variabel terakhir buat compare tanggal
		System.out.println("udah diinsert " + keluargaModel);
		KeluargaModel keluargaTerakhir = keluargaService.keluargaTerakhir();
		String tanggalAkhir = keluargaTerakhir.getNomorKK().substring(6, 12);
		System.out.println("keluarga terakhir " + keluargaTerakhir + tanggalAkhir);

		// bikin tanggal
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyy");
		LocalDate localDate = LocalDate.now();
		String tanggal = dtf.format(localDate);
		System.out.println("tanggal " + tanggal);
		//
		String nkkAsli = "";
		// compare tanggal untuk buat nkk
		if (tanggalAkhir.equalsIgnoreCase(tanggal)) {
			Integer nkkAkhir = Integer.parseInt(keluargaTerakhir.getNomorKK().substring(12, 16));
			nkkAkhir = nkkAkhir + 1;
			String count = new DecimalFormat("0000").format(nkkAkhir);
			nkkAsli = keluargaTerakhir.getKelurahan().getKecamatan().getKodeKecamatan().substring(0, 6) + tanggal
					+ count;
		} else {
			nkkAsli = keluargaTerakhir.getKelurahan().getKecamatan().getKodeKecamatan().substring(0, 6) + tanggal
					+ "0001";
		}
		// String nkk = camat.substring(0, camat.length()-1);
		System.out.println("nkk " + nkkAsli);
		keluargaModel.setNomorKK(nkkAsli);
		keluargaService.addKeluarga(keluargaModel);
		model.addAttribute("nomorKK", nkkAsli);
		return "success-add-keluarga";
	}

	@RequestMapping("/keluarga/view")
	public String view(Model model, @RequestParam(value = "nkk", required = false) String nkk) {
		KeluargaModel keluarga = keluargaService.selectKeluargabyNkk(nkk);
		System.out.println("keluargaview1 "+keluarga);
		if (keluarga != null) {
			model.addAttribute("keluarga", keluarga);
			return "viewKeluarga";
		} else {
			model.addAttribute("nkk", nkk);
			return "notFound-Keluarga";
		}
	}

	@RequestMapping("/keluarga/view/")
	public String viewPath(Model model, @RequestParam(value = "nkk", required = false) String nkk) {
		KeluargaModel keluarga = keluargaService.selectKeluargabyNkk(nkk);
		System.out.println("keluargaview2 "+keluarga);
		ArrayList<PendudukModel> pendudukbyKeluarga = keluargaService.selectPendudukbyKeluarga(nkk);
		
		if (keluarga != null) {
			String kelurahan = keluarga.getKelurahan().getNamaKelurahan();
			String kecamatan = keluarga.getKelurahan().getKecamatan().getNamaKecamatan();
			String kota = keluarga.getKelurahan().getKecamatan().getKota().getNamaKota();
			System.out.println(kelurahan);
			System.out.println(kecamatan);
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("penduduk", pendudukbyKeluarga);
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kota", kota);
			return "viewKeluarga";
		} else {
			model.addAttribute("nkk", nkk);
			return "notFound-Keluarga";
		}
	}

	@RequestMapping("/keluarga/ubah")
	public String updateObject(Model model, @RequestParam(value = "nkk") String nkk) {
		// model.addAttribute("greeting", new Greeting());
		System.out.println("nkk " + nkk);
		KeluargaModel keluarga = keluargaService.selectKeluargabyNkk(nkk);
		System.out.println("keluarga " + keluarga);
		if (keluarga != null) {
			model.addAttribute("keluarga", keluarga);
			System.out.println("keluarga " + keluarga);
			return "form-update-keluarga";
		} else {
			model.addAttribute("nkk", nkk);
			return "notFound-Keluarga";
		}
	}

	@PostMapping(value = "/keluarga/ubah/submit")
	public String updateObjectSubmit(Model model, @ModelAttribute KeluargaModel keluarga) {
		// KeluargaModel keluargaLama =
		// keluargaService.selectKeluargabyNkk(keluarga.getNomorKK());
		System.out.println("keluarga " + keluarga);
		// System.out.println("keluargaLama "+keluargaLama);		
		keluargaService.updateKeluarga(keluarga);
		model.addAttribute("keluarga", keluarga);
		return "success-update-keluarga";
	}

}
