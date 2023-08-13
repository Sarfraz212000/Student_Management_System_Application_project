package in.saffu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.saffu.binding.DashbordForm;
import in.saffu.binding.EnquaryForm;
import in.saffu.binding.EnquarySearchCriteria;
import in.saffu.entity.CourseEntity;
import in.saffu.entity.EnqStatusEntity;
import in.saffu.entity.StudentEnqEntity;
import in.saffu.entity.UserDtlsEntity;
import in.saffu.repository.CourseRepo;
import in.saffu.repository.EnqStatusRepo;
import in.saffu.repository.StudentEnqEntityRepo;
import in.saffu.repository.UserDtlsRepo;

@Service
public class Enquiryimpl implements EnquairyService {

	@Autowired
	private UserDtlsRepo repo;

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private EnqStatusRepo statusRepo;

	@Autowired
	private StudentEnqEntityRepo enqEntityRepo;

	@Autowired
	private HttpSession session;

	@Override
	public DashbordForm getDashboardData(Integer userId) {

		DashbordForm dashForm = new DashbordForm();

		Optional<UserDtlsEntity> findById = repo.findById(userId);

		if (findById.isPresent()) {
			UserDtlsEntity entity = findById.get();

			List<StudentEnqEntity> enquiries = entity.getEnquiries();
			Integer totalCnt = enquiries.size();

			Integer enrollCont = enquiries.stream().filter(e -> e.getStatus().equals("ENROLLED"))
					.collect(Collectors.toList()).size();

			Integer lostCnt = enquiries.stream().filter(e -> e.getStatus().equals("LOST")).collect(Collectors.toList())
					.size();

			dashForm.setTotalEnquriesCnt(totalCnt);
			dashForm.setEnrolledCnt(enrollCont);
			dashForm.setLostCnt(lostCnt);
		}

		return dashForm;
	}

	@Override
	public List<String> getcourseName() {

		List<CourseEntity> findAll = courseRepo.findAll();

		List<String> names = new ArrayList<>();
		for (CourseEntity entity : findAll) {
			names.add(entity.getCoursename());

		}

		return names;
	}

	@Override
	public List<String> getEnqStatus() {

		List<EnqStatusEntity> findAll = statusRepo.findAll();

		List<String> statusList = new ArrayList<>();

		for (EnqStatusEntity entity : findAll) {
			statusList.add(entity.getStatus_name());
		}

		return statusList;
	}

	@Override
	public boolean saveEnquiry(EnquaryForm form) {

		StudentEnqEntity enqentity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, enqentity);

		Integer userId = (Integer) session.getAttribute("userId");

		UserDtlsEntity userDtlsEntity = repo.findById(userId).get();
		enqentity.setUser(userDtlsEntity);

		enqEntityRepo.save(enqentity);

		return true;
	}

	@Override
	public List<StudentEnqEntity> getFilteredEnqs(EnquarySearchCriteria criteria, Integer userId) {

		Optional<UserDtlsEntity> findById = repo.findById(userId);

		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();

			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();

			if (null != criteria.getCourseame() && !"".equals(criteria.getCourseame())) {

				enquiries = enquiries.stream().filter(e -> e.getCourse().equals(criteria.getCourseame()))
						.collect(Collectors.toList());
			}

			if (null != criteria.getClassMode()&& !"".equals(criteria.getClassMode())) {

				enquiries = enquiries.stream().filter(e -> e.getClassMode().equals(criteria.getClassMode()))
						.collect(Collectors.toList());
			}
			if (null != criteria.getEnqstatus() && !"".equals(criteria.getEnqstatus())) {

				enquiries = enquiries.stream().filter(e -> e.getStatus().equals(criteria.getEnqstatus()))
						.collect(Collectors.toList());
			}
			return enquiries;
		}

		return null;
	}

	@Override
	public List<StudentEnqEntity> getAllStudentData() {
		Integer userId = (Integer) session.getAttribute("userId");
		Optional<UserDtlsEntity> findById = repo.findById(userId);
		
		if (findById.isPresent()) {
			
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			return enquiries;
			
		}
		
		return null;
	}

}
