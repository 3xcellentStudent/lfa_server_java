package com.server.pdf.services.pdf;

import java.awt.Color;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class Tables {
  private int numColumns;
  private float[] tableWidth;

  public Tables(int numColumns, float tableWidth){
    this.numColumns = numColumns;
    this.tableWidth = new float[]{tableWidth};
  }

  public Tables(int numColumns, float[] tableWidth){
    this.numColumns = numColumns;
    this.tableWidth = tableWidth;
  }

  public PdfPTable createHeader(String text, int alighment){
    PdfPTable table = new Tables(numColumns, tableWidth).createTable();

    Font font = new Font(Font.HELVETICA, 16, Font.BOLD, new Color(0, 0, 0));
    Paragraph paragraph = new Paragraph(text, font);
    PdfPCell cell = new Cells(paragraph).createCell(new float[]{15f, 0f, 10f, 1f}, alighment);
    table.addCell(cell);
    return table;
  }

  public PdfPTable createTable(){
    PdfPTable table = new PdfPTable(numColumns);
    table.setHorizontalAlignment(Element.ALIGN_LEFT);
    if(tableWidth.length > 1) table.setWidths(tableWidth);
    else table.setWidthPercentage(tableWidth[0]);
    return table;
  }

  public PdfPTable createTable(int alighment){
    PdfPTable table = new PdfPTable(numColumns);
    table.setHorizontalAlignment(alighment);
    if(tableWidth.length > 1) table.setWidths(tableWidth);
    else table.setWidthPercentage(tableWidth[0]);
    return table;
  }

  public PdfPTable twoColums(String leftContent, String rightContent, int rightAligh, float[] paddings){
    PdfPTable table = createTable();
    table.addCell(new Cells(leftContent).createCell(paddings, PdfPCell.ALIGN_LEFT));
    table.addCell(new Cells(rightContent).createCell(rightAligh));
    return table;
  }

  public PdfPTable twoColums(String leftContent, PdfPCell cell){
    PdfPTable table = createTable();
    table.addCell(new Cells(leftContent).createCell(PdfPCell.ALIGN_LEFT));
    table.addCell(cell);
    return table;
  }

  public PdfPTable multipleRows(String[] content, int[] alighment){
    PdfPTable table = createTable();
    for(int i = 0; i < content.length; i++){
      if(i % 2 == 0) table.addCell(new Cells(content[i]).createCell(PdfPCell.ALIGN_LEFT));
      else table.addCell(new Cells(content[i]).createCell(PdfPCell.ALIGN_RIGHT));
    }
    return table;
  }

  public PdfPTable multipleRows(String[] content){
    PdfPTable table = createTable();
    for(int i = 0; i < content.length; i++){
      table.addCell(content[i]);
    }
    return table;
  }
}
