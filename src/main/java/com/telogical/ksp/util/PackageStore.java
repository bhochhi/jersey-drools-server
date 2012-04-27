package com.telogical.ksp.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PackageStore {
	private static Map<String, String> store;
	private static PackageStore instance = null;

	private PackageStore() {
		store = new HashMap<String, String>();
		initPackage();
	}

	public static Map<String, String> getStore() {
		if (instance == null) {
			instance = new PackageStore();
		}
		return store;
	}

	private static void initPackage() {
		try {

			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder b = f.newDocumentBuilder();
			
			Document doc = b.parse(PackageStore.class
					.getResourceAsStream("/rules-packages.xml"));
			NodeList packages = doc.getElementsByTagName("package");
			for (int i = 0; i < packages.getLength(); i++) {
				Element pkg = (Element) packages.item(i);
				store.put(pkg.getElementsByTagName("name").item(0)
						.getTextContent(), pkg.getElementsByTagName("uri")
						.item(0).getTextContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
