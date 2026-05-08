package com.library.service;

import com.library.db.ReturnDao;

public class ReturnCopyService {

	private final ReturnDao returnDao;

	public ReturnCopyService(ReturnDao returnDao) {
		this.returnDao = returnDao;
	}

	/**
	 * Returns a borrowed copy by barcode.
	 * 
	 * @param barcode the barcode of the copy to return
	 * @return the copyId that was updated, or null if barcode was blank
	 * @throws IllegalArgumentException if the barcode is not found
	 */
	public String returnCopy(String barcode) {
		if (barcode == null || barcode.isBlank()) {
			throw new IllegalArgumentException("Barcode must not be empty.");
		}
		return returnDao.returnCopy(barcode.trim());
	}
}
