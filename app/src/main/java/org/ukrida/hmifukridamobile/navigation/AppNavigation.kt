package org.ukrida.hmifukridamobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.ukrida.hmifukridamobile.ui.admin.AddEventScreen
import org.ukrida.hmifukridamobile.ui.admin.AdminDashboard
import org.ukrida.hmifukridamobile.ui.admin.AdminEventDetailScreen
import org.ukrida.hmifukridamobile.ui.admin.EditEventScreen
import org.ukrida.hmifukridamobile.ui.admin.HistoryDetailScreen
import org.ukrida.hmifukridamobile.ui.admin.HistoryScreen
import org.ukrida.hmifukridamobile.ui.admin.ManageEventsScreen
import org.ukrida.hmifukridamobile.ui.admin.UserManagementScreen
import org.ukrida.hmifukridamobile.ui.registered.QrTicketScreen
import org.ukrida.hmifukridamobile.ui.detail.DetailEventScreen
import org.ukrida.hmifukridamobile.ui.home.HomeScreen
import org.ukrida.hmifukridamobile.ui.login.LoginScreen
import org.ukrida.hmifukridamobile.ui.profile.ProfileScreen
import org.ukrida.hmifukridamobile.ui.register.RegisterScreen
import org.ukrida.hmifukridamobile.ui.registered.RegisteredScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
            DetailEventScreen(navController, eventId)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }

        composable(Screen.AdminDashboard.route) {
            AdminDashboard(navController)
        }

        composable(Screen.AddEvent.route) {
            AddEventScreen(navController)
        }

        composable(
            route = Screen.EditEvent.route,
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
            EditEventScreen(navController, eventId)
        }

        composable(Screen.Registered.route) {
            RegisteredScreen(navController)
        }

        composable(
            route = Screen.AdminEventDetail.route,
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
            AdminEventDetailScreen(navController, eventId)
        }

        composable(Screen.ManageEvents.route) {
            ManageEventsScreen(navController)
        }

        composable(Screen.UserManagement.route) {
            UserManagementScreen(navController)
        }

        composable(
            route = Screen.QrTicket.route,
            arguments = listOf(
                navArgument("registrationId") { type = NavType.IntType },
                navArgument("eventTitle") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val registrationId = backStackEntry.arguments?.getInt("registrationId") ?: return@composable
            val eventTitle = backStackEntry.arguments?.getString("eventTitle")
                ?.let { java.net.URLDecoder.decode(it, "UTF-8") } ?: ""
            QrTicketScreen(navController, eventTitle, registrationId)
        }

        composable(Screen.History.route) {
            HistoryScreen(navController)
        }

        composable(
            route = Screen.HistoryDetail.route,
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
            HistoryDetailScreen(navController, eventId)
        }
    }
}