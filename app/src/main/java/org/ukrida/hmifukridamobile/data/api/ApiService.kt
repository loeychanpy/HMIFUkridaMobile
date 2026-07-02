package org.ukrida.hmifukridamobile.data.api

import org.ukrida.hmifukridamobile.data.model.Announcement
import org.ukrida.hmifukridamobile.data.model.ApiResponse
import org.ukrida.hmifukridamobile.data.model.AuthResponse
import org.ukrida.hmifukridamobile.data.model.Event
import org.ukrida.hmifukridamobile.data.model.EventRegistrant
import org.ukrida.hmifukridamobile.data.model.LoginRequest
import org.ukrida.hmifukridamobile.data.model.RegisterRequest
import org.ukrida.hmifukridamobile.data.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    // --- Auth ---

    @POST("routes/login.php")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("routes/users.php")
    suspend fun register(@Body request: RegisterRequest): ApiResponse<Unit>

    @POST("routes/logout.php")
    suspend fun logout(@Header("Authorization") token: String): ApiResponse<Unit>

    // --- Users ---

    @GET("routes/users.php")
    suspend fun getAllUsers(@Header("Authorization") token: String): ApiResponse<List<User>>

    // --- Events ---

    @GET("routes/events.php")
    suspend fun getEvents(@Header("Authorization") token: String): ApiResponse<List<Event>>

    @GET("routes/event_history.php")
    suspend fun getEventHistory(@Header("Authorization") token: String): ApiResponse<List<Event>>

    @GET("routes/event_detail.php")
    suspend fun getEventById(
        @Header("Authorization") token: String,
        @Query("id") eventId: Int
    ): ApiResponse<Event>

    @POST("routes/events.php")
    suspend fun createEvent(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>
    ): ApiResponse<Unit>

    @POST("routes/delete_event.php")
    suspend fun deleteEvent(
        @Header("Authorization") token: String,
        @Body body: Map<String, Int>
    ): ApiResponse<Unit>

    // --- Event Registrations ---

    @POST("routes/event_register.php")
    suspend fun registerForEvent(
        @Header("Authorization") token: String,
        @Body body: Map<String, Int>
    ): ApiResponse<Unit>

    @GET("routes/event_registrants.php")
    suspend fun getEventRegistrants(
        @Header("Authorization") token: String,
        @Query("event_id") eventId: Int
    ): ApiResponse<List<EventRegistrant>>

    // --- Announcements ---

    @GET("routes/announcements.php")
    suspend fun getAnnouncements(@Header("Authorization") token: String): ApiResponse<List<Announcement>>

    @POST("routes/announcements.php")
    suspend fun createAnnouncement(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>
    ): ApiResponse<Unit>

    @POST("routes/delete_announcement.php")
    suspend fun deleteAnnouncement(
        @Header("Authorization") token: String,
        @Body body: Map<String, Int>
    ): ApiResponse<Unit>
}