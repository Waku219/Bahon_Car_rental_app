package com.example.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app.data.AuthRepository
import com.example.app.data.UserRole
import com.example.app.ui.auth.RegisterScreen
import com.example.app.ui.auth.RoleSelectScreen
import com.example.app.ui.auth.SignInScreen
import com.example.app.ui.owner.AddCarScreen
import com.example.app.ui.owner.BookingRequestDetailScreen
import com.example.app.ui.owner.BookingRequestsScreen
import com.example.app.ui.owner.OwnerHomeScreen
import com.example.app.ui.passenger.BookingDetailsScreen
import com.example.app.ui.passenger.BookingSuccessScreen
import com.example.app.ui.passenger.CarDetailsScreen
import com.example.app.ui.passenger.PassengerHomeScreen
import com.example.app.ui.passenger.SearchResultsScreen
import com.example.app.ui.passenger.SearchScreen

/**
 * Every route string in the app lives here. Keeping them in one object means
 * a typo becomes a compile error instead of a screen that silently never opens.
 */
object Routes {
    const val ROLE_SELECT = "role_select"

    // {role} is "PASSENGER" or "OWNER" — one screen serves both, per the wireframes
    const val REGISTER = "register/{role}"
    const val SIGN_IN = "signin/{role}"
    fun register(role: UserRole) = "register/${role.name}"
    fun signIn(role: UserRole) = "signin/${role.name}"

    const val PASSENGER_HOME = "passenger/home"
    const val SEARCH = "passenger/search"
    const val RESULTS = "passenger/results"

    const val CAR_DETAILS = "passenger/car/{vehicleId}"
    fun carDetails(vehicleId: String) = "passenger/car/$vehicleId"

    const val BOOKING_DETAILS = "passenger/booking/{vehicleId}"
    fun bookingDetails(vehicleId: String) = "passenger/booking/$vehicleId"

    const val BOOKING_SUCCESS = "passenger/success/{vehicleId}"
    fun bookingSuccess(vehicleId: String) = "passenger/success/$vehicleId"

    const val OWNER_HOME = "owner/home"
    const val ADD_CAR = "owner/add_car"
    const val BOOKING_REQUESTS = "owner/requests"

    const val REQUEST_DETAIL = "owner/request/{bookingId}"
    fun requestDetail(bookingId: String) = "owner/request/$bookingId"
}

@Composable
fun CholoNavigation(navController: NavHostController = rememberNavController()) {

    val authRepository = remember { AuthRepository() }

    // Signing out clears the ENTIRE back stack (popUpTo(0)) so pressing back
    // from the role-select screen can't walk into a logged-out home screen.
    val signOut: () -> Unit = {
        authRepository.signOut()
        navController.navigate(Routes.ROLE_SELECT) {
            popUpTo(0) { inclusive = true }
        }
    }

    NavHost(navController = navController, startDestination = Routes.ROLE_SELECT) {

        /* ---------------- Auth ---------------- */

        composable(Routes.ROLE_SELECT) {
            RoleSelectScreen(
                onOwnerClick = { navController.navigate(Routes.register(UserRole.OWNER)) },
                onPassengerClick = { navController.navigate(Routes.register(UserRole.PASSENGER)) }
            )
        }

        composable(Routes.REGISTER) { backStackEntry ->
            val role = backStackEntry.roleArg()
            RegisterScreen(
                role = role,
                onBack = { navController.popBackStack() },
                onRegistered = { navController.navigateToHome(role) },
                onSignInClick = { navController.navigate(Routes.signIn(role)) }
            )
        }

        composable(Routes.SIGN_IN) { backStackEntry ->
            val role = backStackEntry.roleArg()
            SignInScreen(
                role = role,
                onBack = { navController.popBackStack() },
                onSignedIn = { navController.navigateToHome(role) },
                onSignUpClick = { navController.navigate(Routes.register(role)) }
            )
        }

        /* ---------------- Passenger ---------------- */

        composable(Routes.PASSENGER_HOME) {
            PassengerHomeScreen(
                onSearchClick = { navController.navigate(Routes.SEARCH) },
                onCategoryClick = { navController.navigate(Routes.RESULTS) },
                onBookingClick = { navController.navigate(Routes.carDetails(it)) },
                onSignOut = signOut
            )
        }

        composable(Routes.SEARCH) {
            SearchScreen(
                onBack = { navController.popBackStack() },
                onSearch = { navController.navigate(Routes.RESULTS) }
            )
        }

        composable(Routes.RESULTS) {
            SearchResultsScreen(
                onBack = { navController.popBackStack() },
                onEdit = { navController.popBackStack() },
                onCarClick = { navController.navigate(Routes.carDetails(it)) }
            )
        }

        composable(Routes.CAR_DETAILS) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")
            CarDetailsScreen(
                vehicleId = vehicleId,
                onBack = { navController.popBackStack() },
                onBookNow = { navController.navigate(Routes.bookingDetails(vehicleId ?: "v1")) }
            )
        }

        composable(Routes.BOOKING_DETAILS) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")
            BookingDetailsScreen(
                vehicleId = vehicleId,
                onBack = { navController.popBackStack() },
                onConfirm = { navController.navigate(Routes.bookingSuccess(vehicleId ?: "v1")) }
            )
        }

        composable(Routes.BOOKING_SUCCESS) { backStackEntry ->
            BookingSuccessScreen(
                vehicleId = backStackEntry.arguments?.getString("vehicleId"),
                onGoHome = {
                    navController.navigate(Routes.PASSENGER_HOME) {
                        popUpTo(Routes.PASSENGER_HOME) { inclusive = true }
                    }
                }
            )
        }

        /* ---------------- Owner ---------------- */

        composable(Routes.OWNER_HOME) {
            OwnerHomeScreen(
                onAddCar = { navController.navigate(Routes.ADD_CAR) },
                onBookingRequests = { navController.navigate(Routes.BOOKING_REQUESTS) },
                onSignOut = signOut
            )
        }

        composable(Routes.ADD_CAR) {
            AddCarScreen(
                onBack = { navController.popBackStack() },
                onSubmit = { navController.popBackStack() }
            )
        }

        composable(Routes.BOOKING_REQUESTS) {
            BookingRequestsScreen(
                onBack = { navController.popBackStack() },
                onRequestClick = { navController.navigate(Routes.requestDetail(it)) }
            )
        }

        composable(Routes.REQUEST_DETAIL) { backStackEntry ->
            BookingRequestDetailScreen(
                bookingId = backStackEntry.arguments?.getString("bookingId"),
                onBack = { navController.popBackStack() },
                onDecision = { navController.popBackStack() }
            )
        }
    }
}

/* ---------------- helpers ---------------- */

private fun androidx.navigation.NavBackStackEntry.roleArg(): UserRole =
    runCatching { UserRole.valueOf(arguments?.getString("role") ?: "") }
        .getOrDefault(UserRole.PASSENGER)

/**
 * After login we jump to the right home screen and wipe the auth screens from
 * the back stack — pressing back from home should exit, not return to the form.
 */
private fun NavHostController.navigateToHome(role: UserRole) {
    val destination =
        if (role == UserRole.OWNER) Routes.OWNER_HOME else Routes.PASSENGER_HOME
    navigate(destination) {
        popUpTo(Routes.ROLE_SELECT) { inclusive = true }
    }
}
