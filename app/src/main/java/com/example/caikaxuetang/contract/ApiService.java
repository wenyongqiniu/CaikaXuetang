package com.example.caikaxuetang.contract;

import com.example.caikaxuetang.responses.AddAddressResponse;
import com.example.caikaxuetang.responses.AddressListResponse;
import com.example.caikaxuetang.responses.AddressResponse;
import com.example.caikaxuetang.responses.BindingWechatResponse;
import com.example.caikaxuetang.responses.CommitSuccessResponse;
import com.example.caikaxuetang.responses.ConfirmCourseTineResponse;
import com.example.caikaxuetang.responses.ContractResponse;
import com.example.caikaxuetang.responses.CourseTimeResponse;
import com.example.caikaxuetang.responses.DakeStepResponse;
import com.example.caikaxuetang.responses.DkCourseListResponse;
import com.example.caikaxuetang.responses.ExamInfoResponse;
import com.example.caikaxuetang.responses.ExamTaskInfoResponse;
import com.example.caikaxuetang.responses.FindNoteResponse;
import com.example.caikaxuetang.responses.GroupInfo;
import com.example.caikaxuetang.responses.GroupNoticeBean;
import com.example.caikaxuetang.responses.GroupNumberResponse;
import com.example.caikaxuetang.responses.HetongResponse;
import com.example.caikaxuetang.responses.HomeWorkResponse;
import com.example.caikaxuetang.responses.LearningCenterResponse;
import com.example.caikaxuetang.responses.LoginWxResponse;
import com.example.caikaxuetang.responses.MyClassResponse;
import com.example.caikaxuetang.responses.NoteDetailResponse;
import com.example.caikaxuetang.responses.PhoneLoginResponse;
import com.example.caikaxuetang.responses.ResetExamResponse;
import com.example.caikaxuetang.responses.SealContractResponse;
import com.example.caikaxuetang.responses.SectionDetailResponse;
import com.example.caikaxuetang.responses.ShowCourseListResponse;
import com.example.caikaxuetang.responses.StudyCardResponse;
import com.example.caikaxuetang.responses.SubmitAddressResponse;
import com.example.caikaxuetang.responses.VersionEntity;
import com.example.caikaxuetang.responses.WallPaperResponse;
import com.example.caikaxuetang.responses.XbCourseListResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiService {

    //绑定微信
    @POST("api/customer/bindWechat")
    Observable<BindingWechatResponse> getBindindWecahtResponse(@Body RequestBody response);

    //微信登录
    @POST("api/customer/wechatLogin")
    Observable<LoginWxResponse> getLoginWecahtResponse(@Body RequestBody response);

    /**
     * 用户信息
     */
    @POST("api/chat/getUserInfo")
    Observable<WallPaperResponse> getWallPaper(@Body RequestBody response);

    //群聊信息
    @GET("api/chat/getGroupInfo")
    @Headers({"Content-Type: application/json", "Cache-Control:public,max-age=120"})
    Observable<GroupInfo> groupInfomation(@QueryMap HashMap<String, String> hashMap);

    //群聊信息
    @POST("api/chat/groupQueryUser")
    @Headers({"Content-Type: application/json", "Cache-Control:public,max-age=120"})
    Observable<GroupNumberResponse> groupList(@Body RequestBody body);

    //群公告
    @GET("api/chat/getGroupAnnouncement")
    @Headers({"Content-Type: application/json", "Cache-Control:public,max-age=120"})
    Observable<GroupNoticeBean> groupNotice(@QueryMap HashMap<String, String> hashMap);

    //首页-查询直播和业务列表
    @POST("api/studycenter/index")
    Observable<LearningCenterResponse> getLearningCenter(@Body RequestBody body);

    //首页-查询课程列表
    @POST("api/studycenter/getCourse")
    Observable<ShowCourseListResponse> getShowCourseListCenter(@Body RequestBody body);

    //getContract
    @POST("api/studycenter/getProcessPageInfo")
    Observable<DakeStepResponse> getDakeStep(@Body RequestBody body);

    //合同-查询合同模板
    @POST("api/studycenter/getContractTemlate")
    Observable<ContractResponse> getContract(@Body RequestBody body);

    //合同-签署合同
    @POST("api/studycenter/sealContract")
    Observable<SealContractResponse> getSealContract(@Body RequestBody body);

    //地址-查询用户是否有地址
    @POST("api/studycenter/getAddress")
    Observable<AddressResponse> getAddress(@Body RequestBody body);

    //地址-新增地址
    @POST("api/studycenter/addAddress")
    Observable<AddAddressResponse> getEditAddress(@Body RequestBody body);

    //地址-新增/编辑地址
    @POST("api/studycenter/editAddress")
    Observable<AddAddressResponse> getChangeAddress(@Body RequestBody body);

    //地址-选择收获地址
    @POST("api/studycenter/selectAddress")
    Observable<SubmitAddressResponse> getSubmitAddress(@Body RequestBody body);

    //地址-查询地址列表
    @POST("api/studycenter/getAddressList")
    Observable<AddressListResponse> getAddressList(@Body RequestBody body);

    //地址-查删除地址
    @POST("api/studycenter/delAddress")
    Observable<CommitSuccessResponse> getDeleteAddress(@Body RequestBody body);

    //选时间-查询可用时间
    @POST("api/studycenter/getCouseTime")
    Observable<CourseTimeResponse> getCourseTime(@Body RequestBody body);

    //选时间-选择时间
    @POST("api/studycenter/selectCourseTime")
    Observable<ConfirmCourseTineResponse> getSelecttime(@Body RequestBody body);

    //体验课-查询课程信息
    @POST("api/studycenter/getXbCourseInfo")
    Observable<XbCourseListResponse> getXbCourseList(@Body RequestBody body);

    //进阶课-查询课程信息
    @POST("api/studycenter/getBigCourseInfo")
    Observable<DkCourseListResponse> getDkCourseList(@Body RequestBody body);

    //体验课|-查询图文小节详情
    @POST("api/studycenter/getTWSectionDetail")
    Observable<SectionDetailResponse> getSectionDetail(@Body RequestBody body);

    //查询课后作业信息
    @POST("api/studycenter/getTaskList")
    Observable<HomeWorkResponse> getHomeWork(@Body RequestBody body);

    //查看答案解析
    @POST("api/studycenter/getOverExamAnalysis")
    Observable<HomeWorkResponse> getExamAnswer(@Body RequestBody body);

    //提交作业答案
    @POST("api/studycenter/submitTask")
    Observable<CommitSuccessResponse> getSubmitAnswer(@Body RequestBody body);

    //记笔记/修改笔记
    @POST("api/studycenter/editNote")
    Observable<CommitSuccessResponse> getKeepNote(@Body RequestBody body);

    //查询笔记详情（记笔记回显用）
    @POST("api/studycenter/getNoteDetail")
    Observable<NoteDetailResponse> getNoteDetail(@Body RequestBody body);


    //记笔记/删除笔记
    @POST("api/studycenter/deleteNote")
    Observable<CommitSuccessResponse> getDeleteNote(@Body RequestBody body);

    //查询笔记列表
    @POST("api/studycenter/getNoteList")
    Observable<FindNoteResponse> getFindNote(@Body RequestBody body);

    //查询毕业考试情况
    @POST("api/studycenter/getOverExamInfo")
    Observable<ExamInfoResponse> getExamInfo(@Body RequestBody body);

    //查询题目
    @POST("api/studycenter/getOverExamTaskInfo")
    Observable<ExamTaskInfoResponse> getExamTaskInfo(@Body RequestBody body);

    //提交考试答案
    @POST("api/studycenter/submitOverExamTask")
    Observable<CommitSuccessResponse> getSubmitExam(@Body RequestBody body);

    //提交作业答案
    @POST("api/studycenter/resetExamInfo")
    Observable<ResetExamResponse> getResetExam(@Body RequestBody body);

    //查询我的合同
    @POST("api/personal/getContractList")
    Observable<HetongResponse> getHetong(@Body RequestBody body);

    //查询我的班级
    @POST("api/personal/getClassList")
    Observable<MyClassResponse> getMyclass(@Body RequestBody body);

    //领取毕业证
    @POST("api/studycenter/getExamCard")
    Observable<StudyCardResponse> getStudyCard(@Body RequestBody body);

    //手机号登录
    @POST("api/customer/loginForCheck")
    Observable<PhoneLoginResponse> getPhoneLogin(@Body RequestBody body);

    //App检查版本更新
    @POST("api/common/checkUpdate")
    @Headers({"Content-Type: application/json", "Cache-Control:public,max-age=120"})
    Observable<VersionEntity> getCkeckUpdate(@Body RequestBody entity);

}
