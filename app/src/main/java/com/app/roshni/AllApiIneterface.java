package com.app.roshni;

import com.app.roshni.SkillsPOJO.skillsBean;
import com.app.roshni.allWorkContrJobListPOJO.allWorkContrJobBean;
import com.app.roshni.brandDetailsPOJO.brandDetailsBean;
import com.app.roshni.contWorkerPOJO.contWorkerBeam;
import com.app.roshni.contractorJobDetailsPOJO.contractorJobDetailsBean;
import com.app.roshni.contractorPOJO.contractorBean;
import com.app.roshni.getTncPOJO.getTncBean;
import com.app.roshni.knowledgeDetailsPOJO.knowledgeDetailsBean;
import com.app.roshni.knowledgeListPOJO.knowledgeListBean;
import com.app.roshni.notificationBean.notificationBean;
import com.app.roshni.samplePOJO.sampleBean;
import com.app.roshni.sectorPOJO.sectorBean;
import com.app.roshni.verify2POJO.verifyBean2;
import com.app.roshni.verifyPOJO.verifyBean;
import com.app.roshni.workerJobListPOJO.workerJobDetailBean;
import com.app.roshni.workerJobListPOJO.workerJobListBean;
import com.app.roshni.workerListPOJO.workerListBean;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AllApiIneterface {

    @Multipart
    @POST("goodbusinessapp/api/login.php")
    Call<verifyBean> login(
            @Part("phone") String client,
            @Part("token") String token
    );

    @Multipart
    @POST("goodbusinessapp/api/login2.php")
    Call<verifyBean2> login2(
            @Part("username") String username,
            @Part("password") String password
    );

    @Multipart
    @POST("goodbusinessapp/api/register_worker.php")
    Call<verifyBean> worker_signup(
            @Part("phone") String client,
            @Part("type") String type,
            @Part("token") String token
    );

    @Multipart
    @POST("goodbusinessapp/api/verify.php")
    Call<verifyBean> verify(
            @Part("phone") String client,
            @Part("otp") String otp
    );

    @Multipart
    @POST("goodbusinessapp/api/resend.php")
    Call<verifyBean> resend(
            @Part("phone") String client
    );

    @Multipart
    @POST("goodbusinessapp/api/createPIN.php")
    Call<verifyBean> createPIN(
            @Part("user_id") String user_id,
            @Part("pin") String pin
    );

    @Multipart
    @POST("goodbusinessapp/api/verifyPIN.php")
    Call<verifyBean> verifyPIN(
            @Part("user_id") String user_id,
            @Part("pin") String pin
    );

    @Multipart
    @POST("goodbusinessapp/api/update_worker_personal.php")
    Call<verifyBean> updateWorkerPersonal(
            @Part("user_id") String user_id,
            @Part("name") String name,
            @Part("id_proof") String id_proof,
            @Part("id_number") String id_number,
            @Part("lat") String lat,
            @Part("lng") String lng,
            @Part("dob") String dob,
            @Part("gender") String gender,
            @Part("cpin") String cpin,
            @Part("cstate") String cstate,
            @Part("cdistrict") String cdistrict,
            @Part("carea") String carea,
            @Part("cstreet") String cstreet,
            @Part("ppin") String ppin,
            @Part("pstate") String pstate,
            @Part("pdistrict") String pdistrict,
            @Part("parea") String parea,
            @Part("pstreet") String pstreet,
            @Part("category") String category,
            @Part("religion") String religion,
            @Part("educational") String educational,
            @Part("marital") String marital,
            @Part("children") String children,
            @Part("belowsix") String belowsix,
            @Part("sixtofourteen") String sixtofourteen,
            @Part("fifteentoeighteen") String fifteentoeighteen,
            @Part("goingtoschool") String goingtoschool,
            @Part("age") String age,
            @Part("same") String same,
            @Part("certified") String certified,
            @Part("skill_level") String skill_level,
            @Part("certification_number") String certification_number,
            @Part("annual_income") String annual_income,
            @Part("other_source") String other_source,
            @Part MultipartBody.Part file1,
            @Part("c1") String c1,
            @Part("c2") String c2,
            @Part("c3") String c3,
            @Part("c4") String c4,
            @Part("c5") String c5
    );

    @Multipart
    @POST("goodbusinessapp/api/update_worker_personal2.php")
    Call<verifyBean> updateWorkerPersonal2(
            @Part("user_id") String user_id,
            @Part("name") String name,
            @Part("id_proof") String id_proof,
            @Part("id_number") String id_number,
            @Part("lat") String lat,
            @Part("lng") String lng,
            @Part("dob") String dob,
            @Part("gender") String gender,
            @Part("cpin") String cpin,
            @Part("cstate") String cstate,
            @Part("cdistrict") String cdistrict,
            @Part("carea") String carea,
            @Part("cstreet") String cstreet,
            @Part("ppin") String ppin,
            @Part("pstate") String pstate,
            @Part("pdistrict") String pdistrict,
            @Part("parea") String parea,
            @Part("pstreet") String pstreet,
            @Part("category") String category,
            @Part("religion") String religion,
            @Part("educational") String educational,
            @Part("marital") String marital,
            @Part("children") String children,
            @Part("belowsix") String belowsix,
            @Part("sixtofourteen") String sixtofourteen,
            @Part("fifteentoeighteen") String fifteentoeighteen,
            @Part("goingtoschool") String goingtoschool,
            @Part("age") String age,
            @Part("same") String same,
            @Part("certified") String certified,
            @Part("skill_level") String skill_level,
            @Part("certification_number") String certification_number,
            @Part("annual_income") String annual_income,
            @Part("other_source") String other_source,
            @Part MultipartBody.Part file1
    );


    @Multipart
    @POST("goodbusinessapp/api/update_brand.php")
    Call<verifyBean> updateBrand(
            @Part("user_id") String user_id,
            @Part("name") String name,
            @Part("firm_type") String firm_type,
            @Part("firm_registration_type") String firm_registration_type,
            @Part("registration_number") String registration_number,
            @Part("lat") String lat,
            @Part("lng") String lng,
            @Part("sector") String sector,
            @Part("contact_details") String contact_details,
            @Part("contact_person") String contact_person,
            @Part("cpin") String cpin,
            @Part("cstate") String cstate,
            @Part("cdistrict") String cdistrict,
            @Part("carea") String carea,
            @Part("cstreet") String cstreet,
            @Part("ppin") String ppin,
            @Part("pstate") String pstate,
            @Part("pdistrict") String pdistrict,
            @Part("parea") String parea,
            @Part("pstreet") String pstreet,
            @Part("manufacturing_units") String manufacturing_units,
            @Part("factory_outlet") String factory_outlet,
            @Part("products") String products,
            @Part("country") String country,
            @Part("workers") String workers,
            @Part("certification") String certification,
            @Part("expiry") String expiry,
            @Part("website") String website,
            @Part("email") String email,
            @Part("same") String same,
            @Part MultipartBody.Part file1,
            @Part("c1") String c1,
            @Part("c2") String c2,
            @Part("c3") String c3,
            @Part("c4") String c4,
            @Part("c5") String c5,
            @Part("business_name") String business_name
    );

    @Multipart
    @POST("goodbusinessapp/api/update_brand2.php")
    Call<verifyBean> updateBrand2(
            @Part("user_id") String user_id,
            @Part("name") String name,
            @Part("firm_type") String firm_type,
            @Part("firm_registration_type") String firm_registration_type,
            @Part("registration_number") String registration_number,
            @Part("lat") String lat,
            @Part("lng") String lng,
            @Part("sector") String sector,
            @Part("contact_details") String contact_details,
            @Part("contact_person") String contact_person,
            @Part("cpin") String cpin,
            @Part("cstate") String cstate,
            @Part("cdistrict") String cdistrict,
            @Part("carea") String carea,
            @Part("cstreet") String cstreet,
            @Part("ppin") String ppin,
            @Part("pstate") String pstate,
            @Part("pdistrict") String pdistrict,
            @Part("parea") String parea,
            @Part("pstreet") String pstreet,
            @Part("manufacturing_units") String manufacturing_units,
            @Part("factory_outlet") String factory_outlet,
            @Part("products") String products,
            @Part("country") String country,
            @Part("workers") String workers,
            @Part("certification") String certification,
            @Part("expiry") String expiry,
            @Part("website") String website,
            @Part("email") String email,
            @Part("same") String same,
            @Part("business_name") String business_name,
            @Part MultipartBody.Part file1
    );

    @Multipart
    @POST("goodbusinessapp/api/update_contractor.php")
    Call<verifyBean> update_contractor(
            @Part("user_id") String user_id,
            @Part("name") String name,
            @Part("id_proof") String id_proof,
            @Part("id_number") String id_number,
            @Part("firm_type") String firm_type,
            @Part("firm_registration_type") String firm_registration_type,
            @Part("registration_no") String registration_no,
            @Part("lat") String lat,
            @Part("lng") String lng,
            @Part("dob") String dob,
            @Part("gender") String gender,
            @Part("business_name") String business_name,
            @Part("establishment_year") String establishment_year,
            @Part("cpin") String cpin,
            @Part("cstate") String cstate,
            @Part("cdistrict") String cdistrict,
            @Part("carea") String carea,
            @Part("cstreet") String cstreet,
            @Part("ppin") String ppin,
            @Part("pstate") String pstate,
            @Part("pdistrict") String pdistrict,
            @Part("parea") String parea,
            @Part("pstreet") String pstreet,
            @Part("home_units") String home_units,
            @Part("home_location") String home_location,
            @Part("workers_male") String workers_male,
            @Part("workers_female") String workers_female,
            @Part("experience") String experience,
            @Part("work_type") String work_type,
            @Part("availability") String availability,
            @Part("employer") String employer,
            @Part("about") String about,
            @Part("sector") String sector,
            @Part("tools") String looms,
            @Part("same") String same,
            @Part MultipartBody.Part file1,
            @Part("c1") String c1,
            @Part("c2") String c2,
            @Part("c3") String c3,
            @Part("c4") String c4,
            @Part("c5") String c5,
            @Part("outsource") String outsource,
            @Part("migrant") String migrant,
            @Part("local") String local
    );


    @Multipart
    @POST("goodbusinessapp/api/post_job.php")
    Call<verifyBean> postjob(
            @Part("brand_id") String brand_id,
            @Part("title") String title,
            @Part("position") String position,
            @Part("sector") String sector,
            @Part("skill_level") String skill_level,
            @Part("skills") String skills,
            @Part("nature") String nature,
            @Part("man_days") String man_days,
            @Part("piece_rate") String piece_rate,
            @Part("place") String place,
            @Part("location") String location,
            @Part("experience") String experience,
            @Part("role") String role,
            @Part("gender") String gender,
            @Part("education") String education,
            @Part("hours") String hours,
            @Part("salary") String salary,
            @Part("stype") String stype,
            @Part("display_name") String display_name,
            @Part("display_phone") String display_phone,
            @Part("display_person") String display_person,
            @Part("display_email") String display_email
    );

    @Multipart
    @POST("goodbusinessapp/api/update_job.php")
    Call<verifyBean> UpdateWorkerJob(
            @Part("id") String id,
            @Part("title") String title,
            @Part("position") String position,
            @Part("sector") String sector,
            @Part("skill_level") String skill_level,
            @Part("skills") String skills,
            @Part("nature") String nature,
            @Part("man_days") String man_days,
            @Part("piece_rate") String piece_rate,
            @Part("place") String place,
            @Part("location") String location,
            @Part("experience") String experience,
            @Part("role") String role,
            @Part("gender") String gender,
            @Part("education") String education,
            @Part("hours") String hours,
            @Part("salary") String salary,
            @Part("stype") String stype,
            @Part("display_name") String display_name,
            @Part("display_phone") String display_phone,
            @Part("display_person") String display_person,
            @Part("display_email") String display_email
    );

    @Multipart
    @POST("goodbusinessapp/api/update_job2.php")
    Call<verifyBean> UpdateContractorJob(
            @Part("id") String id,
            @Part("sector") String sector,
            @Part("job_type") String job_type,
            @Part("experience") String experience,
            @Part("days") String days,
            @Part("rate") String rate,
            @Part("place") String place,
            @Part("display_name") String display_name,
            @Part("display_phone") String display_phone,
            @Part("display_person") String display_person,
            @Part("display_email") String display_email,
            @Part MultipartBody.Part file1
    );

    @Multipart
    @POST("goodbusinessapp/api/post_job_contractor.php")
    Call<verifyBean> post_job_contractor(
            @Part("contractor_id") String contractor_id,
            @Part("sector") String sector,
            @Part("job_type") String job_type,
            @Part("experience") String experience,
            @Part("days") String days,
            @Part("rate") String rate,
            @Part("place") String place,
            @Part("display_name") String display_name,
            @Part("display_phone") String display_phone,
            @Part("display_person") String display_person,
            @Part("display_email") String display_email,
            @Part MultipartBody.Part file1
    );

    @Multipart
    @POST("goodbusinessapp/api/apply_job.php")
    Call<verifyBean> apply_job(
            @Part("job_id") String job_id,
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("goodbusinessapp/api/apply_job2.php")
    Call<verifyBean> apply_job2(
            @Part("job_id") String job_id,
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("goodbusinessapp/api/worker_ac_inac.php")
    Call<verifyBean> worker_ac_inac(
            @Part("jid") String jid,
            @Part("status") String status
    );

    @Multipart
    @POST("goodbusinessapp/api/contractor_ac_inac.php")
    Call<verifyBean> contractor_ac_inac(
            @Part("jid") String jid,
            @Part("status") String status
    );

    @Multipart
    @POST("goodbusinessapp/api/worker_acept_reject.php")
    Call<verifyBean> worker_acept_reject(
            @Part("jid") String jid,
            @Part("id") String id,
            @Part("status") String status
    );

    @Multipart
    @POST("goodbusinessapp/api/contractor_acept_reject.php")
    Call<verifyBean> contractor_acept_reject(
            @Part("jid") String jid,
            @Part("id") String id,
            @Part("status") String status
    );

    @Multipart
    @POST("goodbusinessapp/api/update_worker_professional.php")
    Call<verifyBean> updateWorkerProfessional(
            @Part("user_id") String user_id,
            @Part("sector") String sector,
            @Part("skills") String skills,
            @Part("experience") String experience,
            @Part("employment") String employment,
            @Part("employer") String employer,
            @Part("home") String home,
            @Part("workers") String workers,
            @Part("tools") String tools,
            @Part("location") String location,
            @Part("bank") String bank,
            @Part("govt") String govt
    );

    @Multipart
    @POST("goodbusinessapp/api/getJobListForWorker.php")
    Call<workerJobListBean> getJobListForWorker(
            @Part("user_id") String user_id,
            @Part("date") String date,
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getJobListForContractor.php")
    Call<workerJobListBean> getJobListForContractor(
            @Part("user_id") String user_id,
            @Part("date") String date
    );


    @Multipart
    @POST("goodbusinessapp/api/getAllWorkerJobs.php")
    Call<allWorkContrJobBean> getAllWorkerJobs(
            @Part("brand_id") String brand_id,
            @Part("status") String status,
            @Part("date") String date
    );

    @Multipart
    @POST("goodbusinessapp/api/getAllContractorJobs.php")
    Call<allWorkContrJobBean> getAllContractorJobs(
            @Part("brand_id") String brand_id,
            @Part("status") String status,
            @Part("date") String date
    );


    @Multipart
    @POST("goodbusinessapp/api/getAppliedListForWorker.php")
    Call<workerJobListBean> getAppliedListForWorker(
            @Part("user_id") String user_id,
            @Part("date") String date
    );

    @Multipart
    @POST("goodbusinessapp/api/getAppliedListForContractor.php")
    Call<workerJobListBean> getAppliedListForContractor(
            @Part("user_id") String user_id,
            @Part("date") String date
    );

    @Multipart
    @POST("goodbusinessapp/api/getJobDetailsForWorker.php")
    Call<workerJobDetailBean> getJobDetailForWorker(
            @Part("user_id") String user_id,
            @Part("jid") String jid,
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getJobDetailsForContractor.php")
    Call<contractorJobDetailsBean> getJobDetailsForContractor(
            @Part("user_id") String user_id,
            @Part("jid") String jid
    );


    @GET("goodbusinessapp/api/getSectors.php")
    Call<sectorBean> getSectors();

    @Multipart
    @POST("goodbusinessapp/api/getSectors.php")
    Call<sectorBean> getSectors2(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getCerts.php")
    Call<sectorBean> getCerts(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getSkillLevel.php")
    Call<sectorBean> getSkillLevel(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getGender.php")
    Call<sectorBean> getGender(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getCategories.php")
    Call<sectorBean> getCategories(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getReligion.php")
    Call<sectorBean> getReligion(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getEducation.php")
    Call<sectorBean> getEducation(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getMarital.php")
    Call<sectorBean> getMarital(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getProof.php")
    Call<sectorBean> getProof(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getExperience.php")
    Call<sectorBean> getExperience(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getGenderPreference.php")
    Call<sectorBean> getGenderPreference(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getSkillLevelJob.php")
    Call<sectorBean> getSkillLevelJob(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getNature.php")
    Call<sectorBean> getNature(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getPlace.php")
    Call<sectorBean> getPlace(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getEducationJob.php")
    Call<sectorBean> getEducationJob(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getSalaryType.php")
    Call<sectorBean> getSalaryType(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getEmployment.php")
    Call<sectorBean> getEmployment(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getBank.php")
    Call<sectorBean> getBank(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getAvailability.php")
    Call<sectorBean> getAvailability(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getFirmTypes.php")
    Call<sectorBean> getFirmTypes(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getFirmRegistyrationTypes.php")
    Call<sectorBean> getFirmRegistyrationTypes(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getRoles.php")
    Call<skillsBean> getRoles(
            @Part("sector_id") String sector_id,
            @Part("lang") String lang
    );

    @GET("goodbusinessapp/api/getSkills.php")
    Call<sectorBean> getSkills();

    @Multipart
    @POST("goodbusinessapp/api/getSkills.php")
    Call<skillsBean> getSkills1(
            @Part("sector_id") String sector_id,
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getLocations.php")
    Call<sectorBean> getLocations(
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getAllWorkers.php")
    Call<workerListBean> getAllWorkers(
            @Part("user_id") String user_id,
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getContWorkers.php")
    Call<contWorkerBeam> getContWorkers(
            @Part("cuid") String cuid
    );

    @Multipart
    @POST("goodbusinessapp/api/getAllConttractors.php")
    Call<workerListBean> getAllConttractors(
            @Part("user_id") String user_id,
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getAppliedWorkers.php")
    Call<workerListBean> getAppliedWorkers(
            @Part("jid") String jid
    );

    @Multipart
    @POST("goodbusinessapp/api/getAppliedContractors.php")
    Call<workerListBean> getAppliedContractors(
            @Part("jid") String jid
    );

    @Multipart
    @POST("goodbusinessapp/api/getWorkerById.php")
    Call<workerListBean> getWorkerById(
            @Part("id") String id
    );

    @Multipart
    @POST("goodbusinessapp/api/getWorkerById.php")
    Call<WorkerByIdListBean> getWorkerById1(
            @Part("id") String id,
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getBrandNoti.php")
    Call<notificationBean> getBrandNoti(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("goodbusinessapp/api/getContractorNoti.php")
    Call<notificationBean> getContractorNoti(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("goodbusinessapp/api/getWorkerNoti.php")
    Call<notificationBean> getWorkerNoti(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("goodbusinessapp/api/getSamples.php")
    Call<sampleBean> getSamples(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("goodbusinessapp/api/uploadSample.php")
    Call<sampleBean> uploadSample(
            @Part("user_id") String user_id,
            @Part MultipartBody.Part file1
    );

    @Multipart
    @POST("goodbusinessapp/api/deleteSample.php")
    Call<sampleBean> deleteSample(
            @Part("id") String id
    );

    @Multipart
    @POST("goodbusinessapp/api/getContractorById.php")
    Call<contractorBean> getContractorById(
            @Part("id") String id,
            @Part("lang") String lang
    );

    @Multipart
    @POST("goodbusinessapp/api/getKnowledgeList.php")
    Call<knowledgeListBean> getKnowledgeList(
            @Part("type") String type
    );

    @Multipart
    @POST("goodbusinessapp/api/getKnowledgeById.php")
    Call<knowledgeDetailsBean> getKnowledgeById(
            @Part("id") String id
    );

    @Multipart
    @POST("goodbusinessapp/api/getBrandById.php")
    Call<brandDetailsBean> getBrandById(
            @Part("id") String id
    );

    @Multipart
    @POST("goodbusinessapp/api/getOngoingSurveys.php")
    Call<OngoingListBean> getOngoingSurvey(
            @Part("officer_id") String officer_id
    );

    @Multipart
    @POST("goodbusinessapp/api/update_worker_professional3.php")
    Call<verifyBean> rejectWorkerProfessional(
            @Part("survey_id") String user_id,
            @Part("sector") String sector,
            @Part("skills") String skills,
            @Part("experience") String experience,
            @Part("employment") String employment,
            @Part("employer") String employer,
            @Part("home") String home,
            @Part("workers") String workers,
            @Part("tools") String tools,
            @Part("location") String location,
            @Part("reason") String reason,
            @Part("bank") String bank,
            @Part("id") String id,
            @Part("govt") String govt
    );

    @Multipart
    @POST("goodbusinessapp/api/update_worker_professional2.php")
    Call<verifyBean> updateWorkerProfessional2(
            @Part("survey_id") String user_id,
            @Part("sector") String sector,
            @Part("skills") String skills,
            @Part("experience") String experience,
            @Part("employment") String employment,
            @Part("employer") String employer,
            @Part("home") String home,
            @Part("workers") String workers,
            @Part("tools") String tools,
            @Part("location") String location,
            @Part("bank") String bank,
            @Part("id") String id,
            @Part("govt") String govt
    );

    @Multipart
    @POST("goodbusinessapp/api/getCompletedSurveys.php")
    Call<CompletedListBean> getCompletedSurvey(
            @Part("officer_id") String officer_id

    );

    @Multipart
    @POST("goodbusinessapp/api/submit_contactor.php")
    Call<contractorBean> submit_contactor(
            @Part("survey_id") String survey_id,
            @Part("id") String id
    );

    @Multipart
    @POST("goodbusinessapp/api/submit_brand.php")
    Call<contractorBean> submit_brand(
            @Part("survey_id") String survey_id,
            @Part("id") String id
    );

    @Multipart
    @POST("goodbusinessapp/api/reject_contactor.php")
    Call<contractorBean> reject_contactor(
            @Part("survey_id") String survey_id,
            @Part("reason") String reason,
            @Part("id") String id
    );

    @Multipart
    @POST("goodbusinessapp/api/reject_brand.php")
    Call<contractorBean> reject_brand(
            @Part("survey_id") String survey_id,
            @Part("reason") String reason,
            @Part("id") String id
    );

    @Multipart
    @POST("goodbusinessapp/api/update_contractor2.php")
    Call<verifyBean> update_contractor2(
            @Part("user_id") String user_id,
            @Part("name") String name,
            @Part("id_proof") String id_proof,
            @Part("id_number") String id_number,
            @Part("firm_type") String firm_type,
            @Part("firm_registration_type") String firm_registration_type,
            @Part("registration_no") String registration_no,
            @Part("lat") String lat,
            @Part("lng") String lng,
            @Part("dob") String dob,
            @Part("gender") String gender,
            @Part("business_name") String business_name,
            @Part("establishment_year") String establishment_year,
            @Part("cpin") String cpin,
            @Part("cstate") String cstate,
            @Part("cdistrict") String cdistrict,
            @Part("carea") String carea,
            @Part("cstreet") String cstreet,
            @Part("ppin") String ppin,
            @Part("pstate") String pstate,
            @Part("pdistrict") String pdistrict,
            @Part("parea") String parea,
            @Part("pstreet") String pstreet,
            @Part("home_units") String home_units,
            @Part("home_location") String home_location,
            @Part("workers_male") String workers_male,
            @Part("workers_female") String workers_female,
            @Part("experience") String experience,
            @Part("work_type") String work_type,
            @Part("availability") String availability,
            @Part("employer") String employer,
            @Part("about") String about,
            @Part("sector") String sector,
            @Part("tools") String looms,
            @Part("same") String same,
            @Part("outsource") String outsource,
            @Part("migrant") String migrant,
            @Part("local") String local,
            @Part MultipartBody.Part file1
    );

    @Multipart
    @POST("goodbusinessapp/api/raiseComplaint.php")
    Call<contractorBean> raiseComplaint(
            @Part("user_id") String user_id,
            @Part("subject") String subject,
            @Part("body") String body
    );

    @Multipart
    @POST("goodbusinessapp/api/giveFeedback.php")
    Call<contractorBean> giveFeedback(
            @Part("user_id") String user_id,
            @Part("subject") String subject,
            @Part("body") String body
    );

    @Multipart
    @POST("goodbusinessapp/api/unsubscribe.php")
    Call<contractorBean> unsubscribe(
            @Part("user_id") String user_id,
            @Part("feedback") String feedback
    );

    @Multipart
    @POST("goodbusinessapp/api/gettnc.php")
    Call<getTncBean> gettnc(
            @Part("id") String id
    );

    @Multipart
    @POST("goodbusinessapp/api/update_tnc.php")
    Call<getTncBean> update_tnc(
            @Part("user_id") String user_id,
            @Part("c1") String c1,
            @Part("c2") String c2,
            @Part("c3") String c3,
            @Part("c4") String c4,
            @Part("c5") String c5
    );

}
