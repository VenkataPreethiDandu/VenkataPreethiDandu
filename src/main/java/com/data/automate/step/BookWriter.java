package com.data.automate.step;

import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.batch.item.ItemWriter;

import com.data.automate.model.DataResult;

public class BookWriter implements ItemWriter<DataResult> {
    private final Sheet sheet;

    public BookWriter(Sheet sheet) {
        this.sheet = sheet;
    }

    @Override
    public void write(List<? extends DataResult> list) {
        for (int i = 0; i < list.size(); i++) {
            writeRow(i, list.get(i));
        }
    }

    private void writeRow(int currentRowNumber, DataResult result) {
        List<String> columns = prepareColumns(result);
        Row row = this.sheet.createRow(currentRowNumber);
        for (int i = 0; i < columns.size(); i++) {
            writeCell(row, i, columns.get(i));
        }
    }

    private List<String> prepareColumns(DataResult result) {
        return Arrays.asList(
        		result.getEmpId(),
        		result.getDeptId()
        );
    }



	private void writeCell(Row row, int currentColumnNumber, String value) {
        Cell cell = row.createCell(currentColumnNumber);
        cell.setCellValue(value);
    }
}