package com.server.pdf.services.pdf;

import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;

public class Cells {
  private Phrase phrase;
  
  public Cells(String phrase){
    this.phrase = new Phrase(phrase);
  }

  public Cells(Phrase phrase){
    this.phrase = phrase;
  }

  public PdfPCell createCell(float[] paddings, int alighment){
    PdfPCell cell = new PdfPCell(phrase);
    
    cell.setBorder(0);
    cell.setHorizontalAlignment(alighment);
    cell.setPaddingTop(paddings[0]);
    cell.setPaddingRight(paddings[1]);
    cell.setPaddingBottom(paddings[2]);
    cell.setPaddingLeft(paddings[3]);

    return cell;
  }

  public PdfPCell createCell(float[] paddings){
    PdfPCell cell = new PdfPCell(phrase);
    
    cell.setBorder(0);
    cell.setPaddingTop(paddings[0]);
    cell.setPaddingRight(paddings[1]);
    cell.setPaddingBottom(paddings[2]);
    cell.setPaddingLeft(paddings[3]);

    return cell;
  }

  public PdfPCell createCell(int alighment){
    PdfPCell cell = new PdfPCell(new Phrase(phrase));
    cell.setBorder(0);
    cell.setHorizontalAlignment(alighment);
    return cell;
  }
}