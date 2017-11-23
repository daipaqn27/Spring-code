package org.java.spring.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.java.spring.model.JavaClazz;
import org.java.spring.model.Student;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfView extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) {
		 JavaClazz clazz = (JavaClazz) model.get("clazzObj");
		 PdfPTable table = new PdfPTable(3);
		 table.setWidthPercentage(100.0f);
		 try {
			table.setWidths(new float[]{2.0f, 3.0f, 1.5f});
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		table.setSpacingBefore(10);
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.WHITE);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.CYAN);
		cell.setPadding(5);
		cell.setPhrase(new Phrase("ID", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Age", font));
		table.addCell(cell);
		
		table.completeRow();
		 for(Student student : clazz.getStudents()){
			 table.addCell(String.valueOf(student.getId()));
			 table.addCell(student.getName());
			 table.addCell(student.getAge());
			 table.completeRow();
		 }
		 
		 try {
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected void buildPdfMetadata(Map<String, Object> model, Document document, 
			HttpServletRequest request) {
		
	}

}
