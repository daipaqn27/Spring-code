package org.java.spring.view;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.OutputStream;

public abstract class AbstractPdfView extends AbstractView{

	public AbstractPdfView() {
		super();
		setContentType("application/pdf");
	}
	
	@Override
	protected boolean generatesDownloadContent() {
		return false;
	}
	
	protected Document newDocument(){
		return new Document(PageSize.A4);
	}
	
	protected PdfWriter newWriter(Document document, OutputStream outputStream) throws DocumentException{
		return PdfWriter.getInstance(document, outputStream);
	}
	
	protected void prepareWriter(Map<String, Object> model, PdfWriter pdfWriter, HttpServletRequest request){
		pdfWriter.setViewerPreferences(getViewerPreferences());
	}
	
	protected int getViewerPreferences(){
		return PdfWriter.ALLOW_PRINTING|PdfWriter.PageLayoutSinglePage;
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, 
			HttpServletResponse response)
			throws Exception {
		ByteArrayOutputStream byteOutput = createTemporaryOutputStream();
		
		Document document = new Document();
		PdfWriter writer = newWriter(document, byteOutput);
		prepareWriter(model, writer, request);
		buildPdfMetadata(model, document, request);
		
		document.open();
		buildPdfDocument(model, document, writer, request, response);
		document.close();
		
		writeToResponse(response, byteOutput);
		
	}

	protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response);

	protected abstract void buildPdfMetadata(Map<String, Object> model, 
			Document document, HttpServletRequest request);
}
