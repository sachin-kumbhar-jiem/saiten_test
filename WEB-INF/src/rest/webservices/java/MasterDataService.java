package rest.webservices.java;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.saiten.dao.MstQuestionDAO;
import com.saiten.dao.MstSubjectDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.util.AESEncryptionDecryptionUtil;
import com.saiten.util.WebAppConst;

@Path("/service/masterDataService")
public class MasterDataService {

	@Context
	private ServletContext context;

	@Autowired
	private MstSubjectDAO mstSubjectDAO;

	@Autowired
	private MstQuestionDAO mstQuestionDAO;

	@Autowired
	@Qualifier("globalProperties")
	private Properties globalProperties;

	@SuppressWarnings("rawtypes")
	@GET
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	public Response get(
			@DefaultValue("") @QueryParam("masterDataId") String masterDataId)
			throws Exception {

		Response response = null;

		Map<String, String> subjectMap = null;

		masterDataId = AESEncryptionDecryptionUtil.decrypt(masterDataId);

		// Get subject list
		if (masterDataId.equals(WebAppConst.SUBJECT_LIST)) {

			subjectMap = new LinkedHashMap<String, String>();

			try {
				List subjectList = mstSubjectDAO.findSubjectList();
				if (!subjectList.isEmpty()) {
					subjectMap = buildSubjectMap(subjectList);
				}
				response = Response.status(201).entity(subjectMap).build();

			} catch (HibernateException he) {
				throw new Exception(
						globalProperties
								.getProperty(WebAppConst.ERROR_GET_SUBJECTLIST));
			} catch (SaitenRuntimeException e) {
				throw new Exception(
						globalProperties
								.getProperty(WebAppConst.ERROR_GET_SUBJECTLIST));
			} catch (Exception e) {
				throw new Exception(
						globalProperties
								.getProperty(WebAppConst.ERROR_GET_SUBJECTLIST));
			}

		} else if (masterDataId.equals(WebAppConst.SUBJECT_WISE_QUESTION_LIST)) {

			try {
				List questionList = mstQuestionDAO.findQuestionList();
				response = Response.status(201).entity(questionList).build();
			} catch (HibernateException he) {
				throw new Exception(
						globalProperties
								.getProperty(WebAppConst.ERROR_GET_SUBJECTWISE_QUESTIONLIST));
			} catch (SaitenRuntimeException e) {
				throw new Exception(
						globalProperties
								.getProperty(WebAppConst.ERROR_GET_SUBJECTWISE_QUESTIONLIST));
			} catch (Exception e) {
				throw new Exception(
						globalProperties
								.getProperty(WebAppConst.ERROR_GET_SUBJECTWISE_QUESTIONLIST));
			}

		} else {
			throw new Exception(
					globalProperties
							.getProperty(WebAppConst.ERROR_MASTERDATA_ID_INVALID));
		}

		return response;
	}

	/**
	 * @param subjectNameList
	 * @return Map<String, String>
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, String> buildSubjectMap(List subjectList) {
		Map<String, String> subjectMap = null;

		if (!subjectList.isEmpty()) {
			subjectMap = new LinkedHashMap<String, String>();

			for (Object mstSubjectObj : subjectList) {
				Object[] mstSubjectObjArray = (Object[]) mstSubjectObj;

				subjectMap.put((String) mstSubjectObjArray[0],
						(String) mstSubjectObjArray[1]);
			}
		}

		return subjectMap;
	}
}
