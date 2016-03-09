package com.alisrc.wp.payment;

import static org.junit.Assert.*;

import java.nio.file.FileSystems;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Test;

public class TestWindowsVerifier {
	private String xmlData = null;
	private String windowsStoreXmlData = null;
	
	@Before
	public void setUp() throws Exception {
		
		xmlData = new String(Files.readAllBytes(FileSystems.getDefault().getPath("src/test/resources", "ReceiptSHA256.xml")));
		windowsStoreXmlData = new String(Files.readAllBytes(FileSystems.getDefault().getPath("src/test/resources", "ReceiptWindowsSHA256.xml")));
		
	}

	@Test
	public void testWindowsPhoneStore() throws Exception {
		assertTrue(WindowsVerifier.validateWindowsPhoneStore(xmlData));
	}

	@Test
	public void testWindowsStore() throws Exception {
		assertTrue(WindowsVerifier.validateWindowsStore(windowsStoreXmlData));
	}

}
