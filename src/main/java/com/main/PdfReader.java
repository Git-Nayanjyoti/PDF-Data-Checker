package com.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.base.ReadFiles;
import com.base.ReadPropertiesFile;

public class PdfReader {

	public static void main(String[] args) throws Exception {
		ReadPropertiesFile readProperty = new ReadPropertiesFile();
		ReadFiles readFiles = new ReadFiles();
		List<String> filePath = readFiles.filenames;
		List<Integer> percentage = new ArrayList<Integer>();
		for (String fname : filePath) {
			String path = readFiles.dir + "\\" + fname;
			File tempfile = new File(path);
			PDDocument pdf = Loader.loadPDF(tempfile);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			String docText = pdfStripper.getText(pdf);

			int noOfKeywords = readProperty.prop.size();
			int percentageMatch = 0;
			for (int i = 0; i < noOfKeywords; i++) {
				String key = "keyword" + Integer.toString(i + 1);
				if (docText.toLowerCase().contains(readProperty.prop.getProperty(key))) {
					percentageMatch += (100 / noOfKeywords);
				}
			}
			percentage.add(percentageMatch);
			tempfile = null;
		}
		System.out.println("Resume Name" + "\t" + "-" + "\t" + "PercentageMatch");
		for (int i = 0; i < filePath.size(); i++) {
			System.out.println(readFiles.filenames.get(i) + "\t" + "-" + "\t" + percentage.get(i));
		}

	}

}
