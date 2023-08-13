package in.saffu.service;

import java.util.List;

import in.saffu.binding.DashbordForm;
import in.saffu.binding.EnquaryForm;
import in.saffu.binding.EnquarySearchCriteria;
import in.saffu.entity.StudentEnqEntity;

public interface EnquairyService {
	
	public List<String> getcourseName();
	
	public List<String> getEnqStatus();
	
	public boolean saveEnquiry(EnquaryForm form);
	
	public DashbordForm getDashboardData(Integer userId);
	
	public List<StudentEnqEntity> getFilteredEnqs(EnquarySearchCriteria criteria,Integer userId);
	
	public List<StudentEnqEntity> getAllStudentData();	
	
	
}
