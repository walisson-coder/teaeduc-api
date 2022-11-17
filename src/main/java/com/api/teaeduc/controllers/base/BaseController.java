package com.api.teaeduc.controllers.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    	
	protected ResponseEntity<Object> response(Object obj) {
        if (obj == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		// else if (obj instanceof FileDTO) {
		// 	FileDTO file = (FileDTO) obj;
		// 	String mineType = file.getMimeType();
		// 	MediaType mediaType = MediaType.parseMediaType(mineType);
		// 	return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFileName()+"\"; filename*=UTF-8''"+file.getFileName()+"").contentType(mediaType).body(file.getBytes());
		// }
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
}
