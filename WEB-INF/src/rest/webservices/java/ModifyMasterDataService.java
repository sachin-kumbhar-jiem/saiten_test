package rest.webservices.java;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.MstScorerInfo;
import com.saiten.util.AESEncryptionDecryptionUtil;
import com.saiten.util.WebAppConst;

@Path("/service/modifyMasterDataService")
public class ModifyMasterDataService {

	@Context
	private ServletContext context;

	@Inject
	private UserRegistration userRegistration;

	@Autowired
	@Qualifier("globalProperties")
	private Properties globalProperties;

	@POST
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(String data,
			@DefaultValue("") @QueryParam("serviceId") String serviceId)
			throws Exception {
		Response response = null;
		serviceId = AESEncryptionDecryptionUtil.decrypt(serviceId);

		if (serviceId.equals(WebAppConst.WG_REGISTRATION)) {

			// prepare subject code list.
			List<String> subjectCodeList = new ArrayList<String>();
			prepareSubjectCodeList(subjectCodeList, data);

			// get user specific data.
			JSONArray array = new JSONArray(data);
			JSONArray userJSONArray = array.getJSONArray(1);
			JSONObject userJSONObject = userJSONArray.getJSONObject(0);
			MstScorerInfo mstScorerInfo = new MstScorerInfo();
			prepareMstScorerInfo(mstScorerInfo, userJSONObject);

			try {
				boolean isRegister = userRegistration.registerWG(mstScorerInfo,
						subjectCodeList);

				response = Response.status(201).entity(isRegister).build();

			} catch (SaitenRuntimeException e) {
				throw new Exception(
						globalProperties
								.getProperty(WebAppConst.ERROR_WG_REGISTRATION));
			} catch (Exception e) {
				throw new Exception(
						globalProperties
								.getProperty(WebAppConst.ERROR_WG_REGISTRATION));
			}
		} else if (serviceId.equals(WebAppConst.SAITENSHA_REGISTRATION)) {

			// prepare questionseq list
			List<Integer> questionSeqList = new ArrayList<Integer>();
			prepareQuestionSeqList(questionSeqList, data);

			// get user specific data.
			JSONArray array = new JSONArray(data);
			JSONArray userJSONArray = array.getJSONArray(1);
			JSONObject userJSONObject = userJSONArray.getJSONObject(0);
			MstScorerInfo mstScorerInfo = new MstScorerInfo();
			prepareMstScorerInfo(mstScorerInfo, userJSONObject);

			try {
				boolean isRegister = userRegistration.registerSaitensha(
						mstScorerInfo, questionSeqList);

				response = Response.status(201).entity(isRegister).build();

			} catch (SaitenRuntimeException e) {
				throw new Exception(
						globalProperties
								.getProperty(WebAppConst.ERROR_SAITENSHA_KANTOKUSHA_REGISTRATION));
			} catch (Exception e) {
				throw new Exception(
						globalProperties
								.getProperty(WebAppConst.ERROR_SAITENSHA_KANTOKUSHA_REGISTRATION));
			}

		} else if (serviceId.equals(WebAppConst.ADMIN_REGISTRATION)) {

			// get user specific data.
			JSONArray array = new JSONArray(data);
			JSONArray userJSONArray = array.getJSONArray(0);
			JSONObject userJSONObject = userJSONArray.getJSONObject(0);
			MstScorerInfo mstScorerInfo = new MstScorerInfo();
			prepareMstScorerInfo(mstScorerInfo, userJSONObject);

			try {
				boolean isRegister = userRegistration
						.registerAdmin(mstScorerInfo);

				response = Response.status(201).entity(isRegister).build();

			} catch (SaitenRuntimeException e) {
				throw new Exception(
						globalProperties
								.getProperty(WebAppConst.ERROR_ADMIN_REGISTRATION));
			} catch (Exception e) {
				throw new Exception(
						globalProperties
								.getProperty(WebAppConst.ERROR_ADMIN_REGISTRATION));
			}

		} else {
			throw new Exception(
					globalProperties
							.getProperty(WebAppConst.ERROR_SERVICE_ID_INVALID));
		}

		return response;
	}

	private static void prepareSubjectCodeList(List<String> subjectCodeList,
			String data) throws JSONException {
		JSONArray array = new JSONArray(data);
		JSONArray subjectsJSONArray = array.getJSONArray(0);

		for (int i = 0; i < subjectsJSONArray.length(); i++) {
			subjectCodeList.add(((String) subjectsJSONArray.get(i)));
		}
	}

	private static void prepareQuestionSeqList(List<Integer> questionSeqList,
			String data) throws JSONException {
		JSONArray array = new JSONArray(data);
		JSONArray questionsJSONArray = array.getJSONArray(0);

		for (int i = 0; i < questionsJSONArray.length(); i++) {
			questionSeqList.add(Integer.valueOf(questionsJSONArray.get(i)
					.toString()));
		}
	}

	private void prepareMstScorerInfo(MstScorerInfo mstScorerInfo,
			JSONObject userJSONObject) throws JSONException {

		mstScorerInfo.setScorerId((String) userJSONObject.get("login"));
		mstScorerInfo.setPassword((String) userJSONObject.get("password"));
		String userType = (String) userJSONObject.get("user_type");
		Integer isKantokusha = Integer.valueOf(userJSONObject.get(
				"is_kantokusha").toString());
		if (userType.equals(WebAppConst.SCORER_ROLE)
				&& isKantokusha.equals(WebAppConst.IS_KANTOKUSHA)) {
			userType = WebAppConst.SV_ROLE;
		} else if (userType.equals(WebAppConst.ADMINISTRATOR)) {
			userType = WebAppConst.ADMIN_ROLE;
		}
		mstScorerInfo.setUserType(userType);
		mstScorerInfo.setScorerName((String) userJSONObject.get("surname")
				+ " " + (String) userJSONObject.get("name"));
		mstScorerInfo.setNoDbUpdate('F');
		mstScorerInfo.setDeleteFlag('F');
		mstScorerInfo
				.setUpdatePersonId(WebAppConst.USER_REGISTRATION_UPDATE_PERSON_ID);
		/*
		 * Timestamp timeStamp = new Timestamp( Long.parseLong((String)
		 * userJSONObject.get("timestamp")));
		 */
		mstScorerInfo.setCreateDate(new Date());
		mstScorerInfo.setUpdateDate(new Date());
	}
}
