package com.data.automate.config;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.data.automate.listener.JobListener;
import com.data.automate.model.DataResult;
import com.data.automate.step.BookRepository;
import com.data.automate.step.BookWriter;
import com.data.automate.step.SqlItemReader;

@Configuration
@EnableBatchProcessing
@EnableJpaRepositories
public class BatchConfig {
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	
    private static final int CHUNK = 100;

    @Bean
    public ItemReader<List<DataResult>> bookReader() {
        return new SqlItemReader();
    }
    

    @Bean
    public SXSSFWorkbook workbook() {
        return new SXSSFWorkbook(CHUNK);
    }
    
    @Bean
    public FileOutputStream fileOutputStream() throws FileNotFoundException {
        return new FileOutputStream("E:\\testout.xlsx");
    }
    
    
    @Bean
    public ItemWriter<DataResult> bookWriter(SXSSFWorkbook workbook) {
        SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet("Result");
        return new BookWriter(sheet);
    }
    
    @Bean
    public JobExecutionListener listener(SXSSFWorkbook workbook,FileOutputStream fileOutputStream) {
        return new JobListener(workbook,fileOutputStream);
    }

    @Bean
    public Step step(ItemReader<List<DataResult>> bookReader, ItemWriter<DataResult> bookWriter) {
        return stepBuilderFactory.get("export")
                .<List<DataResult>, DataResult>chunk(CHUNK)
                .reader(bookReader)
                .writer(bookWriter)
                .build();
    }

    @Bean
    public Job job(Step step, JobExecutionListener listener) {
        return jobBuilderFactory.get("exportBooksToXlsx")
                .start(step)
                .listener(listener)
                .build();
    }


}



