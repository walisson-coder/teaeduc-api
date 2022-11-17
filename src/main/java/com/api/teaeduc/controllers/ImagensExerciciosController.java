package com.api.teaeduc.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.teaeduc.application.ImagensExerciciosApplication;
import com.api.teaeduc.controllers.base.BaseCRUDController;
import com.api.teaeduc.dtos.ImagensExerciciosDTO;
import com.api.teaeduc.dtos.ImagensExerciciosFiltroDTO;
import com.api.teaeduc.models.ImagensExercicios;
import com.api.teaeduc.utils.BusinessException;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("${privateApi}/imagens")
public class ImagensExerciciosController extends BaseCRUDController<ImagensExerciciosDTO, ImagensExercicios, ImagensExerciciosFiltroDTO> {
    @Autowired
	ImagensExerciciosApplication imagensExerciciosApplication;

    @GetMapping(value = "/buscar-imagem/{idAnexo}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] download(@PathVariable(value = "idAnexo") Long idAnexo) throws BusinessException, IOException {
		Resource resource = imagensExerciciosApplication.loadAsResource(idAnexo);
		InputStream in = resource.getInputStream();
		return IOUtils.toByteArray(in);
	}
    
}
