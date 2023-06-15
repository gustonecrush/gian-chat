package umn.ac.id.myapplication.ui.api

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import umn.ac.id.myapplication.ui.data.*
import umn.ac.id.myapplication.ui.model.ChatData
import umn.ac.id.myapplication.ui.model.MessageData

interface ApiInterface {
    @Multipart
    @POST("uploadCV")
   fun uploadCV(
        @Part pdfFile: MultipartBody.Part
    ): Call<UploadCVResponse>

    @FormUrlEncoded
    @POST("loginApplicant")
    fun postLogin(
        @Field("Username") Username: String,
        @Field("Password") Password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("loginCompany")
    fun postLoginCompany(
        @Field("Username") Username: String,
        @Field("Password") Password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("signupApplicant")
    fun postSignUp(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<SignUpResponse>

    @FormUrlEncoded
    @POST("signupCompany")
    fun postSignUpCompany(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call <SignUpResponse>

    @POST("setProfileApplicant")
    fun setProfileApplicant(
        @Header("Authorization") token: String,
        @Field("Name") Name: String,
        @Field("DateOfBirth") DateOfBirth: String,
        @Field("Email") Email: String,
        @Field("Language") Language: String,
        @Field("Summary") Summary: String,
        @Field("Education") Education: String,
        @Field("Skills") Skills: String,
        @Field("SalaryMin") SalaryMin: Int,
        @Field("Location") Location: String,
        @Field("Degree") Degree: String,
        @Field("MobilePhone") MobilePhone: String
    ): Call<ProfileApplicantResponse>

    @GET("getProfile")
    fun getProfileApplicant (
        @Header("Authorization") token: String
    ): Call<GetProfileApplicantResponse>

    @GET("getProfileCompany")
    fun getProfileCompany(
        @Header("Authorization") token: String
    ): Call<GetCompanyProfileResponse>

    @POST("api/chat/newchat")
    fun createNewChat(
        @Header("Authorization") token: String,
        @Body chatData: ChatData
    ): Call<GetChatResponse>

    @POST("api/messages/sendfromapplicant")
    fun sendMessageFromApplicant(
        @Header("Authorization") token: String,
        @Body messageData: MessageData
    ): Call<GetChatResponse>

    @POST("api/messages/sendfromcompany")
    fun sendMessageFromCompany(
        @Header("Authorization") token: String,
        @Body messageData: MessageData
    ): Call<GetChatResponse>

}