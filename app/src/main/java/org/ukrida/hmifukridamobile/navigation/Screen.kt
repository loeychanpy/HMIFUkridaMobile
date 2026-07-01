package org.ukrida.hmifukridamobile.navigation

sealed class Screen(val route: String) {

    object Login : Screen("login")

    object Register : Screen("register")

    object Home : Screen("home")

    object Detail : Screen("detail/{eventId}") {
        fun createRoute(eventId: Int) = "detail/$eventId"
    }

    object RegisterEvent : Screen("register_event")

    object Profile : Screen("profile")

    object AdminDashboard : Screen("admin_dashboard")

    object AddEvent : Screen("add_event")

    object EditEvent : Screen("edit_event")

    object Registered : Screen("registered")

    object Participant : Screen("participant")

    object History : Screen("history")

    object ParticipantDetail : Screen("participant_detail")

    object AdminEventDetail : Screen("admin_event_detail/{eventId}") {
        fun createRoute(eventId: Int) = "admin_event_detail/$eventId"
    }

    object HistoryDetail : Screen("history_detail")

}
