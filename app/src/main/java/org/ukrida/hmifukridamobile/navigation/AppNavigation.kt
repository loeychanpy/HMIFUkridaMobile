package org.ukrida.hmifukridamobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.ukrida.hmifukridamobile.ui.detail.DetailEventScreen
import org.ukrida.hmifukridamobile.ui.home.HomeScreen
import org.ukrida.hmifukridamobile.ui.login.LoginScreen
import org.ukrida.hmifukridamobile.ui.profile.ProfileScreen
import org.ukrida.hmifukridamobile.ui.register.RegisterEventScreen
import org.ukrida.hmifukridamobile.ui.register.RegisterScreen
import org.ukrida.hmifukridamobile.ui.registered.RegisteredScreen
import org.ukrida.hmifukridamobile.ui.admin.AddEventScreen
import org.ukrida.hmifukridamobile.ui.admin.AdminDashboard
import org.ukrida.hmifukridamobile.ui.admin.EditEventScreen
import org.ukrida.hmifukridamobile.ui.admin.AdminEventDetailScreen
import org.ukrida.hmifukridamobile.ui.admin.HistoryDetailScreen

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

        composable(Screen.Detail.route) {
            DetailEventScreen(navController)
        }

        composable(Screen.RegisterEvent.route) {
            RegisterEventScreen(navController)
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

        composable(Screen.EditEvent.route) {
            EditEventScreen(navController)
        }

        composable(Screen.Registered.route){
            RegisteredScreen(navController)
        }

        composable(Screen.AdminEventDetail.route) {
            AdminEventDetailScreen(navController)
        }

        composable(

            Screen.HistoryDetail.route

        ){

            HistoryDetailScreen(

                navController

            )

        }
    }
}