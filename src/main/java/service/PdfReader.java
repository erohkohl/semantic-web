package service;

import model.State;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PdfReader {

    private PDDocument pdDocument;
    private String pdfText = null;
    private PDFTextStripper pdfStripper = null;
    private List<State> stateList;

    public PdfReader(List<State> stateList, String url, String filePath) {
        this.stateList = stateList;

        //Disabled, because file is already downloaded to local file system.
        //downloadPdfReportFromUrl(url, filePath);
        extractDataFromPdf(stateList, filePath);
    }

    /**
     * Method loads number of registered weapons from u.s. report. Therefore method is customized
     * and only able to load data from report of 2011.
     *
     * @param stateList
     * @param filePath
     */
    private void extractDataFromPdf(List<State> stateList, String filePath) {
        File file = new File(filePath);
        try {
            pdDocument = PDDocument.load(file);
            pdfStripper = new PDFTextStripper();
            pdfText = pdfStripper.getText(pdDocument);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfText.trim();
        String exhibit_8 = pdfText.substring(pdfText.indexOf("Exhibit 8."), pdfText.indexOf("Exhibit 9.")).trim();
        String[] splitted = exhibit_8.split("\\n");

        for (String s : splitted) {
            stateList.stream().forEach((State state) -> {
                if (s.contains(state.getName())) {
                    String[] numbers = s.split("\\s+");
                    state.setRegisteredWeapons(numbers[numbers.length - 1]);
                }
            });
        }
    }

    /**
     * Loads pdf file from url and save it to local file system.
     * ATTENTION: I executed this method only once, because i did not want to start lots of request to u.s. federal
     * administration.
     *
     * @param pdfUrl
     * @param pdfName
     */
    private void downloadPdfReportFromUrl(String pdfUrl, String pdfName) {
        URL url;
        OutputStream outputStream = null;
        byte[] ba1 = new byte[1024];
        int baLength;
        try {
            outputStream = new FileOutputStream(pdfName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            url = new URL(pdfUrl);
            url.openConnection();
            InputStream inputStream = url.openStream();
            while ((baLength = inputStream.read(ba1)) != -1) {
                outputStream.write(ba1, 0, baLength);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PDDocument getPdDocument() {
        return pdDocument;
    }
}
