package com.workersjoint.app;

import com.workersjoint.app.SkillsPOJO.skillsBean;
import com.workersjoint.app.allWorkContrJobListPOJO.allWorkContrJobBean;
import com.workersjoint.app.brandDetailsPOJO.brandDetailsBean;
import com.workersjoint.app.contWorkerPOJO.contWorkerBeam;
import com.workersjoint.app.contractorJobDetailsPOJO.contractorJobDetailsBean;
import com.workersjoint.app.contractorPOJO.contractorBean;
import com.workersjoint.app.getTncPOJO.getTncBean;
import com.workersjoint.app.knowledgeDetailsPOJO.knowledgeDetailsBean;
import com.workersjoint.app.knowledgeListPOJO.knowledgeListBean;
import com.workersjoint.app.notificationBean.notificationBean;
import com.workersjoint.app.samplePOJO.sampleBean;
import com.workersjoint.app.sectorPOJO.sectorBean;
import com.workersjoint.app.verify2POJO.verifyBean2;
import com.workersjoint.app.verifyPOJO.verifyBean;
import com.workersjoint.app.workerJobListPOJO.workerJobDetailBean;
import com.workersjoint.app.workerJobListPOJO.workerJobListBean;
import com.workersjoint.app.workerListPOJO.workerListBean;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AllApiIneterface {

    @Multipart
    @POST("api/login.php")
    Call<verifyBean> login(
            @Part("phone") String client,
            @Part("token") String token
    );

    @Multipart
    @POST("api/login2.php")
    Call<verifyBean2> login2(
            @Part("username") String username,
            @Part("password") String password
    );

    @Multipart
    @POST("api/register_worker.php")
    Call<verifyBean> worker_signup(
            @Part("phone") String client,
            @Part("type") String type,
            @Part("token") String token
    );

    @Multipart
    @POST("api/verify.php")
    Call<verifyBean> verify(
            @Part("phone") String client,
            @Part("otp") String otp
    );

    @Multipart
    @POST("api/resend.php")
    Call<verifyBean> resend(
            @Part("phone") String client
    );

    @Multipart
    @POST("api/createPIN.php")
    Call<verifyBean> createPIN(
            @Part("user_id") String user_id,
            @Part("pin") String pin
    );

    @Multipart
    @POST("api/verifyPIN.php")
    Call<verifyBean> verifyPIN(
            @Part("user_id") String user_id,
            @Part("pin") String pin
    );

    @Multipart
    @POST("api/update_worker_personal.php")
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
            @Part("goingtoschool2") String goingtoschool2,
            @Part("age") String age,
            @Part("same") String same,
            @Part("certified") String certified,
            @Part("skill_level") String skill_level,
            @Part("certification_number") String certification_number,
            @Part("annual_income") String annual_income,
            @Part("other_source") String other_source,
            @Part MultipartBody.Part file1,
            @Part MultipartBody.Part file2,
            @Part("c1") String c1,
            @Part("c2") String c2,
            @Part("c3") String c3,
            @Part("c4") String c4,
            @Part("c5") String c5
    );

    @Multipart
    @POST("api/update_worker_personal2.php")
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
            @Part("goingtoschool2") String goingtoschool2,
            @Part("age") String age,
            @Part("same") String same,
            @Part("certified") String certified,
            @Part("skill_level") String skill_level,
            @Part("certification_number") String certification_number,
            @Part("annual_income") String annual_income,
            @Part("other_source") String other_source,
            @Part MultipartBody.Part file1,
            @Part MultipartBody.Part file2
    );


    @Multipart
    @POST("api/update_brand.php")
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
            @Part("business_name") String business_name,
            @Part("processes") String processes,
            @Part("otherwork") String otherwork,
            @Part("market") String market,
            @Part("certification_number") String certification_number,
            @Part("outsourcing") String outsourcing,
            @Part("child_labour") String child_labour,
            @Part("supply_chain") String supply_chain
    );

    @Multipart
    @POST("api/update_brand2.php")
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
            @Part("processes") String processes,
            @Part("otherwork") String otherwork,
            @Part("market") String market,
            @Part("certification_number") String certification_number,
            @Part("outsourcing") String outsourcing,
            @Part("child_labour") String child_labour,
            @Part("supply_chain") String supply_chain,
            @Part MultipartBody.Part file1
    );

    @Multipart
    @POST("api/update_contractor.php")
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
            @Part("otherwork") String otherwork,
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
            @Part("local") String local,
            @Part("without_bank") String without_bank,
            @Part("school") String school,
            @Part("non_school") String non_school,
            @Part("email") String email,
            @Part("govt") String govt,
            @Part("child_labour") String child_labour,
            @Part("supply_chain") String supply_chain
    );


    @Multipart
    @POST("api/post_job.php")
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
    @POST("api/update_job.php")
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
    @POST("api/update_job2.php")
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
            @Part MultipartBody.Part file1,
            @Part MultipartBody.Part file2,
            @Part MultipartBody.Part file3,
            @Part MultipartBody.Part file4,
            @Part MultipartBody.Part file5
    );

    @Multipart
    @POST("api/post_job_contractor.php")
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
            @Part MultipartBody.Part file1,
            @Part MultipartBody.Part file2,
            @Part MultipartBody.Part file3,
            @Part MultipartBody.Part file4,
            @Part MultipartBody.Part file5
    );

    @Multipart
    @POST("api/apply_job.php")
    Call<verifyBean> apply_job(
            @Part("job_id") String job_id,
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("api/withdrawJob.php")
    Call<verifyBean> withdrawJob(
            @Part("job_id") String job_id,
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("api/apply_job2.php")
    Call<verifyBean> apply_job2(
            @Part("job_id") String job_id,
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("api/withdrawJob2.php")
    Call<verifyBean> withdrawJob2(
            @Part("job_id") String job_id,
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("api/worker_ac_inac.php")
    Call<verifyBean> worker_ac_inac(
            @Part("jid") String jid,
            @Part("status") String status
    );

    @Multipart
    @POST("api/contractor_ac_inac.php")
    Call<verifyBean> contractor_ac_inac(
            @Part("jid") String jid,
            @Part("status") String status
    );

    @Multipart
    @POST("api/worker_acept_reject.php")
    Call<verifyBean> worker_acept_reject(
            @Part("jid") String jid,
            @Part("id") String id,
            @Part("status") String status
    );

    @Multipart
    @POST("api/contractor_acept_reject.php")
    Call<verifyBean> contractor_acept_reject(
            @Part("jid") String jid,
            @Part("id") String id,
            @Part("status") String status
    );

    @Multipart
    @POST("api/update_worker_professional.php")
    Call<verifyBean> updateWorkerProfessional(
            @Part("user_id") String user_id,
            @Part("sector") String sector,
            @Part("skills") String skills,
            @Part("otherwork") String otherwork,
            @Part("experience") String experience,
            @Part("availability") String availability,
            @Part("employment") String employment,
            @Part("employer") String employer,
            @Part("home") String home,
            @Part("workers") String workers,
            @Part("tools") String tools,
            @Part("location") String location,
            @Part("bank") String bank,
            @Part("govt") String govt,
            @Part("child_labour") String child_labour,
            @Part("supply_chain") String supply_chain,
            @Part("area") String area,
            @Part("factory_home") String factory_home
    );

    @Multipart
    @POST("api/getJobListForWorker.php")
    Call<workerJobListBean> getJobListForWorker(
            @Part("user_id") String user_id,
            @Part("date") String date,
            @Part("lang") String lang,
            @Part("sort") String sort
    );

    @Multipart
    @POST("api/getJobListForContractor.php")
    Call<workerJobListBean> getJobListForContractor(
            @Part("user_id") String user_id,
            @Part("date") String date,
            @Part("lang") String lang,
            @Part("sort") String sort
    );


    @Multipart
    @POST("api/getAllWorkerJobs.php")
    Call<allWorkContrJobBean> getAllWorkerJobs(
            @Part("brand_id") String brand_id,
            @Part("status") String status,
            @Part("date") String date
    );

    @Multipart
    @POST("api/getAllContractorJobs.php")
    Call<allWorkContrJobBean> getAllContractorJobs(
            @Part("brand_id") String brand_id,
            @Part("status") String status,
            @Part("date") String date
    );


    @Multipart
    @POST("api/getAppliedListForWorker.php")
    Call<workerJobListBean> getAppliedListForWorker(
            @Part("user_id") String user_id,
            @Part("date") String date,
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getAppliedListForContractor.php")
    Call<workerJobListBean> getAppliedListForContractor(
            @Part("user_id") String user_id,
            @Part("date") String date,
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getJobDetailsForWorker.php")
    Call<workerJobDetailBean> getJobDetailForWorker(
            @Part("user_id") String user_id,
            @Part("jid") String jid,
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getJobDetailsForContractor.php")
    Call<contractorJobDetailsBean> getJobDetailsForContractor(
            @Part("user_id") String user_id,
            @Part("jid") String jid
    );


    @GET("api/getSectors.php")
    Call<sectorBean> getSectors();

    @Multipart
    @POST("api/getSectors.php")
    Call<sectorBean> getSectors2(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getSectors3.php")
    Call<sectorBean> getSectors3(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getContractorSector.php")
    Call<sectorBean> getContractorSector(
            @Part("lang") String lang,
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("api/getWorkerSector.php")
    Call<sectorBean> getWorkerSector(
            @Part("lang") String lang,
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("api/getBrandSector.php")
    Call<sectorBean> getBrandSector(
            @Part("lang") String lang,
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("api/getCerts.php")
    Call<sectorBean> getCerts(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getChild.php")
    Call<sectorBean> getChild(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getFactoryHome.php")
    Call<sectorBean> getFactoryHome(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getSkillLevel.php")
    Call<sectorBean> getSkillLevel(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getGender.php")
    Call<sectorBean> getGender(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getCategories.php")
    Call<sectorBean> getCategories(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getReligion.php")
    Call<sectorBean> getReligion(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getEducation.php")
    Call<sectorBean> getEducation(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getMarital.php")
    Call<sectorBean> getMarital(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getProof.php")
    Call<sectorBean> getProof(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getExperience.php")
    Call<sectorBean> getExperience(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getGenderPreference.php")
    Call<sectorBean> getGenderPreference(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getSkillLevelJob.php")
    Call<sectorBean> getSkillLevelJob(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getNature.php")
    Call<sectorBean> getNature(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getPlace.php")
    Call<sectorBean> getPlace(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getEducationJob.php")
    Call<sectorBean> getEducationJob(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getSalaryType.php")
    Call<sectorBean> getSalaryType(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getEmployment.php")
    Call<sectorBean> getEmployment(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getBank.php")
    Call<sectorBean> getBank(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getGovt.php")
    Call<sectorBean> getGovt(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getAvailability.php")
    Call<sectorBean> getAvailability(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getFirmTypes.php")
    Call<sectorBean> getFirmTypes(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getFirmRegistyrationTypes.php")
    Call<sectorBean> getFirmRegistyrationTypes(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getRoles.php")
    Call<skillsBean> getRoles(
            @Part("sector_id") String sector_id,
            @Part("lang") String lang
    );

    @GET("api/getSkills.php")
    Call<sectorBean> getSkills();

    @Multipart
    @POST("api/getSkills.php")
    Call<skillsBean> getSkills1(
            @Part("sector_id") String sector_id,
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getSkills2.php")
    Call<skillsBean> getSkills2(
            @Part("sector_id") String sector_id,
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getLocations.php")
    Call<sectorBean> getLocations(
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getAllWorkers.php")
    Call<workerListBean> getAllWorkers(
            @Part("user_id") String user_id,
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getContWorkers.php")
    Call<contWorkerBeam> getContWorkers(
            @Part("cuid") String cuid
    );

    @Multipart
    @POST("api/getAllConttractors.php")
    Call<workerListBean> getAllConttractors(
            @Part("user_id") String user_id,
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getAppliedWorkers.php")
    Call<workerListBean> getAppliedWorkers(
            @Part("jid") String jid,
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getAppliedContractors.php")
    Call<workerListBean> getAppliedContractors(
            @Part("jid") String jid,
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getWorkerById.php")
    Call<workerListBean> getWorkerById(
            @Part("id") String id
    );

    @Multipart
    @POST("api/getWorkerById.php")
    Call<WorkerByIdListBean> getWorkerById1(
            @Part("id") String id,
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getBrandNoti.php")
    Call<notificationBean> getBrandNoti(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("api/getContractorNoti.php")
    Call<notificationBean> getContractorNoti(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("api/getWorkerNoti.php")
    Call<notificationBean> getWorkerNoti(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("api/getSamples.php")
    Call<sampleBean> getSamples(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("api/uploadSample.php")
    Call<sampleBean> uploadSample(
            @Part("user_id") String user_id,
            @Part MultipartBody.Part file1
    );

    @Multipart
    @POST("api/deleteSample.php")
    Call<sampleBean> deleteSample(
            @Part("id") String id
    );

    @Multipart
    @POST("api/getContractorById.php")
    Call<contractorBean> getContractorById(
            @Part("id") String id,
            @Part("lang") String lang
    );

    @Multipart
    @POST("api/getKnowledgeList.php")
    Call<knowledgeListBean> getKnowledgeList(
            @Part("type") String type
    );

    @Multipart
    @POST("api/getKnowledgeById.php")
    Call<knowledgeDetailsBean> getKnowledgeById(
            @Part("id") String id
    );

    @Multipart
    @POST("api/getBrandById.php")
    Call<brandDetailsBean> getBrandById(
            @Part("id") String id
    );

    @Multipart
    @POST("api/getOngoingSurveys.php")
    Call<OngoingListBean> getOngoingSurvey(
            @Part("officer_id") String officer_id
    );

    @Multipart
    @POST("api/update_worker_professional3.php")
    Call<verifyBean> rejectWorkerProfessional(
            @Part("survey_id") String user_id,
            @Part("sector") String sector,
            @Part("skills") String skills,
            @Part("otherwork") String otherwork,
            @Part("experience") String experience,
            @Part("availability") String availability,
            @Part("employment") String employment,
            @Part("employer") String employer,
            @Part("home") String home,
            @Part("workers") String workers,
            @Part("tools") String tools,
            @Part("location") String location,
            @Part("reason") String reason,
            @Part("bank") String bank,
            @Part("id") String id,
            @Part("govt") String govt,
            @Part("child_labour") String child_labour,
            @Part("supply_chain") String supply_chain,
            @Part("area") String area,
            @Part("factory_home") String factory_home
    );

    @Multipart
    @POST("api/update_worker_professional2.php")
    Call<verifyBean> updateWorkerProfessional2(
            @Part("survey_id") String user_id,
            @Part("sector") String sector,
            @Part("skills") String skills,
            @Part("otherwork") String otherwork,
            @Part("experience") String experience,
            @Part("availability") String availability,
            @Part("employment") String employment,
            @Part("employer") String employer,
            @Part("home") String home,
            @Part("workers") String workers,
            @Part("tools") String tools,
            @Part("location") String location,
            @Part("bank") String bank,
            @Part("id") String id,
            @Part("govt") String govt,
            @Part("child_labour") String child_labour,
            @Part("supply_chain") String supply_chain,
            @Part("area") String area,
            @Part("factory_home") String factory_home
    );


    @Multipart
    @POST("api/update_worker_professional4.php")
    Call<verifyBean> updateWorkerProfessional4(
            @Part("user_id") String user_id,
            @Part("sector") String sector,
            @Part("skills") String skills,
            @Part("otherwork") String otherwork,
            @Part("experience") String experience,
            @Part("availability") String availability,
            @Part("employment") String employment,
            @Part("employer") String employer,
            @Part("home") String home,
            @Part("workers") String workers,
            @Part("tools") String tools,
            @Part("location") String location,
            @Part("bank") String bank,
            @Part("id") String id,
            @Part("govt") String govt,
            @Part("child_labour") String child_labour,
            @Part("supply_chain") String supply_chain,
            @Part("area") String area,
            @Part("factory_home") String factory_home
    );


    @Multipart
    @POST("api/getCompletedSurveys.php")
    Call<CompletedListBean> getCompletedSurvey(
            @Part("officer_id") String officer_id

    );

    @Multipart
    @POST("api/submit_contactor.php")
    Call<contractorBean> submit_contactor(
            @Part("survey_id") String survey_id,
            @Part("id") String id
    );

    @Multipart
    @POST("api/submit_brand.php")
    Call<contractorBean> submit_brand(
            @Part("survey_id") String survey_id,
            @Part("id") String id
    );

    @Multipart
    @POST("api/reject_contactor.php")
    Call<contractorBean> reject_contactor(
            @Part("survey_id") String survey_id,
            @Part("reason") String reason,
            @Part("id") String id
    );

    @Multipart
    @POST("api/reject_brand.php")
    Call<contractorBean> reject_brand(
            @Part("survey_id") String survey_id,
            @Part("reason") String reason,
            @Part("id") String id
    );

    @Multipart
    @POST("api/update_contractor2.php")
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
            @Part("otherwork") String otherwork,
            @Part("availability") String availability,
            @Part("employer") String employer,
            @Part("about") String about,
            @Part("sector") String sector,
            @Part("tools") String looms,
            @Part("same") String same,
            @Part("outsource") String outsource,
            @Part("migrant") String migrant,
            @Part("local") String local,
            @Part("without_bank") String without_bank,
            @Part("school") String school,
            @Part("non_school") String non_school,
            @Part("email") String email,
            @Part("govt") String govt,
            @Part("child_labour") String child_labour,
            @Part("supply_chain") String supply_chain,
            @Part MultipartBody.Part file1
    );

    @Multipart
    @POST("api/raiseComplaint.php")
    Call<contractorBean> raiseComplaint(
            @Part("user_id") String user_id,
            @Part("subject") String subject,
            @Part("body") String body
    );

    @Multipart
    @POST("api/giveFeedback.php")
    Call<contractorBean> giveFeedback(
            @Part("user_id") String user_id,
            @Part("subject") String subject,
            @Part("body") String body
    );

    @Multipart
    @POST("api/unsubscribe.php")
    Call<contractorBean> unsubscribe(
            @Part("user_id") String user_id,
            @Part("feedback") String feedback
    );

    @Multipart
    @POST("api/gettnc.php")
    Call<getTncBean> gettnc(
            @Part("id") String id
    );

    @Multipart
    @POST("api/update_tnc.php")
    Call<getTncBean> update_tnc(
            @Part("user_id") String user_id,
            @Part("c1") String c1,
            @Part("c2") String c2,
            @Part("c3") String c3,
            @Part("c4") String c4,
            @Part("c5") String c5
    );

}
