package com.data.automate.step;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;


import com.data.automate.model.DataResult;

@Repository
@Transactional
public class BookRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	

	//@Query(value = "CALL FIND_CARS_AFTER_YEAR(:year_in);", nativeQuery = true)
	//List<DataResult> findCarsAfterYear(@Param("year_in") Integer year_in);
	public List<DataResult> callStoreProcedure(String sheetName1, String sheetName2, String filePath1, String filePath2, String header, String table1, String table2) {
	StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SP_ImportandExportexcel");
	query.registerStoredProcedureParameter("sheetName1", String.class, ParameterMode.IN);
	query.registerStoredProcedureParameter("sheetName2", String.class, ParameterMode.IN);
	query.registerStoredProcedureParameter("filePath1", String.class, ParameterMode.IN);
	query.registerStoredProcedureParameter("filePath2", String.class, ParameterMode.IN);
	query.registerStoredProcedureParameter("header", String.class, ParameterMode.IN);
	query.registerStoredProcedureParameter("table1 ", String.class, ParameterMode.IN);
	query.registerStoredProcedureParameter("table2", String.class, ParameterMode.IN);
	query.registerStoredProcedureParameter("EmpId", String.class, ParameterMode.OUT);
	query.registerStoredProcedureParameter("DeptId", String.class, ParameterMode.OUT);
	query.execute();
	List<DataResult> result = (List<DataResult>) query.getResultList();
	return result;
	}
}
