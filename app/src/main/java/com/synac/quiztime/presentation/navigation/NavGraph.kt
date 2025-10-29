//package com.synac.quiztime.presentation.navigation
//
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.navOptions
//import com.synac.quiztime.presentation.dashboard.DashboardScreen
//import com.synac.quiztime.presentation.dashboard.DashboardViewModel
//import com.synac.quiztime.presentation.issue_report.IssueReportScreen
//import com.synac.quiztime.presentation.issue_report.IssueReportViewModel
//import com.synac.quiztime.presentation.quiz.QuizScreen
//import com.synac.quiztime.presentation.quiz.QuizViewModel
//import com.synac.quiztime.presentation.result.ResultScreen
//import com.synac.quiztime.presentation.result.ResultViewModel
//import org.koin.androidx.compose.koinViewModel
//
//@Composable
//fun NavGraph(
//    navController: NavHostController,
//    paddingValues: PaddingValues
//) {
//    NavHost(
//        modifier = Modifier.padding(paddingValues),
//        navController = navController,
//        startDestination = Route.DashboardScreen
//    ) {
//        composable<Route.DashboardScreen> {
//            val viewModel = koinViewModel<DashboardViewModel>()
//            val state by viewModel.state.collectAsStateWithLifecycle()
//            DashboardScreen(
//                state = state,
//                onAction = viewModel::onAction,
//                onTopicCardClick = {
//                    navController.navigate(Route.QuizScreen(it))
//                }
//            )
//        }
//        composable<Route.QuizScreen> {
//            val viewModel = koinViewModel<QuizViewModel>()
//            val state by viewModel.state.collectAsStateWithLifecycle()
//            QuizScreen(
//                state = state,
//                onAction = viewModel::onAction,
//                event = viewModel.event,
//                navigationToDashboardScreen = {
//                    navController.navigateUp()
//                },
//                navigationToResultScreen = {
//                    val options = navOptions {
//                        popUpTo(Route.QuizScreen) { inclusive = true }
//                    }
//                    navController.navigate(Route.ResultScreen, options)
//                }
//            )
//        }
//        composable<Route.ResultScreen> {
//            val viewModel = koinViewModel<ResultViewModel>()
//            val state by viewModel.state.collectAsStateWithLifecycle()
//            ResultScreen(
//                state = state,
//                event = viewModel.event,
//                onReportIconClick = { questionId ->
//                    navController.navigate(Route.IssueReportScreen(questionId))
//                },
//                onStartNewQuiz = {
//                    val options = navOptions {
//                        popUpTo(Route.ResultScreen) { inclusive = true }
//                    }
//                    navController.navigate(Route.DashboardScreen, options)
//                }
//            )
//        }
//        composable<Route.IssueReportScreen> {
//            val viewModel = koinViewModel<IssueReportViewModel>()
//            val state by viewModel.state.collectAsStateWithLifecycle()
//            IssueReportScreen(
//                state = state,
//                event = viewModel.event,
//                onAction = viewModel::onAction,
//                navigateBack = {
//                    navController.navigateUp()
//                }
//            )
//        }
//    }
//}

package com.synac.quiztime.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.synac.quiztime.presentation.dashboard.DashboardScreen
import com.synac.quiztime.presentation.dashboard.DashboardViewModel
import com.synac.quiztime.presentation.issue_report.IssueReportScreen
import com.synac.quiztime.presentation.issue_report.IssueReportViewModel
import com.synac.quiztime.presentation.quiz.QuizScreen
import com.synac.quiztime.presentation.quiz.QuizViewModel
import com.synac.quiztime.presentation.result.ResultScreen
import com.synac.quiztime.presentation.result.ResultViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument


@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        startDestination = Route.DashboardScreen
    ) {
        composable<Route.DashboardScreen> {
            val viewModel = koinViewModel<DashboardViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            DashboardScreen(
                state = state,
                onAction = viewModel::onAction,
                onTopicCardClick = { topicCode ->
                    navController.navigate(Route.QuizScreen(topicCode))
                }
            )
        }
        composable<Route.QuizScreen> {
            val viewModel = koinViewModel<QuizViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            QuizScreen(
                state = state,
                onAction = viewModel::onAction,
                event = viewModel.event,
                navigationToDashboardScreen = {
                    navController.navigateUp()
                },
                navigationToResultScreen = {
                    navController.navigate(Route.ResultScreen) {
                        popUpTo<Route.QuizScreen> { inclusive = true }
                    }
                }
            )
        }
        composable<Route.ResultScreen> {
            val viewModel = koinViewModel<ResultViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            ResultScreen(
                state = state,
                event = viewModel.event,
                onReportIconClick = { questionId ->
//                    navController.navigate(Route.IssueReportScreen(questionId))
                    navController.navigate("issue_report_screen/$questionId")
                },
                onStartNewQuiz = {
                    navController.navigate(Route.DashboardScreen) {
                        popUpTo<Route.ResultScreen> { inclusive = true }
                    }
                }
            )
        }
//        composable<Route.IssueReportScreen> {
//            val viewModel = koinViewModel<IssueReportViewModel>()
//            val state by viewModel.state.collectAsStateWithLifecycle()
//            IssueReportScreen(
//                state = state,
//                event = viewModel.event,
//                onAction = viewModel::onAction,
//                navigateBack = {
//                    navController.navigateUp()
//                }
//            )
//        }
        composable(
            route = "issue_report_screen/{questionId}",
            arguments = listOf(navArgument("questionId") { type = NavType.StringType })
        ) {
            val viewModel = koinViewModel<IssueReportViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            IssueReportScreen(
                state = state,
                event = viewModel.event,
                onAction = viewModel::onAction,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}