package com.library.service;

import com.library.db.ReturnDao;

public class ReturnCopyService {

	private final ReturnDao returnDao;

	public ReturnCopyService(ReturnDao returnDao) {
		this.returnDao = returnDao;
	}

	public String returnCopy(String barcode) {
		if (barcode == null || barcode.isBlank()) {
			throw new IllegalArgumentException("Barcode must not be empty.");
		}
		return returnDao.returnCopy(barcode.trim());
	}
}
