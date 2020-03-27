package com.data.automate.step;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.data.automate.model.DataResult;

public class SqlItemReader implements ItemReader<List<DataResult>> {
	
	@Autowired
	private BookRepository bookRepository;
	
	
	@Override
    public List<DataResult> read() throws Exception {
	
		// you can customize code here to read excels from a folder and pass it to required stored procedure,might need to autowire resource 
		
	List<DataResult> result = bookRepository.callStoreProcedure("Sheet1$","Sheet1$",
			  "C:\\Users\\kamis\\Downloads\\EMP.xls","C:\\Users\\kamis\\Downloads\\DEP.xls","YES", "Employee","Department");      
        return result;
    }

}
