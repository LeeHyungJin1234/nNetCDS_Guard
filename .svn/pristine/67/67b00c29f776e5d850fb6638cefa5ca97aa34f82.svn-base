package nnsp.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.web.servlet.view.AbstractView;

import nnsp.vo.NcLog;
 
public class NcLogCsv extends AbstractView {
 
    @SuppressWarnings({ "unchecked", "unused", "resource" })
	@Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String mode = (String) model.get("mode");
	
    	String fileName = createFileName(mode+"Log"); // 파일 이름은 -+영문으로 할 것
		setFileNameToResponse(request, response, fileName);
    	
		CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(), CSVFormat.DEFAULT);

        int row = 1;

        String[] header = NcLog.getCsvHeader(mode);
        if (header == null) {
            // TODO: 로그?
            return ;
        }

        // 일반 utf-8의 경우 excel 에서 정상 인식하지 못하기 때문에 BOM 추가
        // header[0] = "\ufeff" + header[0];
        
        csvPrinter.printRecord((Object[]) header);

        List<NcLog> searchList = (List<NcLog>) model.get("searchList");

        if(searchList!=null && searchList.size()>0) {
        	csvPrinter.printRecords(searchList.stream().map(t -> t.getCsvData(mode)).collect(Collectors.toList()));
        }

        csvPrinter.close(true);
    }
    
    private void setFileNameToResponse(HttpServletRequest request, HttpServletResponse response, String fileName) {
    	response.setContentType("text/csv; charset=MS949");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	}
    
    private String createFileName(String fileName) {
		SimpleDateFormat fileFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return new StringBuilder(fileName).append("_").append(fileFormat.format(new Date())).append(".csv").toString();
	}
 
}