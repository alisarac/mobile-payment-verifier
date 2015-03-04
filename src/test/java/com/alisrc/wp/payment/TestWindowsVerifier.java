package com.alisrc.wp.payment;

import static org.junit.Assert.*;

import java.nio.file.FileSystems;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Test;

public class TestWindowsVerifier {
	private String xmlData = null;
	
	@Before
	public void setUp() throws Exception {
		
		xmlData = new String(Files.readAllBytes(FileSystems.getDefault().getPath("src/test/resources", "ReceiptSHA256.xml")));
		System.out.println(xmlData);
	}

	@Test
	public void test() throws Exception {
		assertTrue(WindowsVerifier.validateWindowsPhoneStore(xmlData));
	}

}
