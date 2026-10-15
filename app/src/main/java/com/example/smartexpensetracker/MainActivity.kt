package com.example.smartexpensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartexpensetracker.ui.screens.AddTransactionScreen
import com.example.smartexpensetracker.ui.screens.HomeScreen
import com.example.smartexpensetracker.ui.screens.OnboardingScreen
import com.example.smartexpensetracker.ui.screens.RegisterScreen
import com.example.smartexpensetracker.ui.theme.SmartExpenseTrackerTheme
import com.example.smartexpensetracker.viewmodel.ExpenseViewModel

// ==========================================
// CỔNG VÀO CHÍNH CỦA ỨNG DỤNG (MainActivity)
// ==========================================
class MainActivity : ComponentActivity() {

    // Khởi tạo Não bộ ViewModel
    private val expenseViewModel by viewModels<ExpenseViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Khoác lên Theme giao diện Fintech
            SmartExpenseTrackerTheme {
                AppNavigation(viewModel = expenseViewModel)
            }
        }
    }
}

// ==========================================
// BẾN XE TRUNG TÂM (Navigation)
// ==========================================
@Composable
fun AppNavigation(viewModel: ExpenseViewModel) {
    // 1. Bác tài xế dẫn đường
    val navController = rememberNavController()

    // 2. Bến xe chứa các trạm dừng (Khởi hành từ Onboarding)
    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        // Trạm 0: 5 Màn hình chào mừng Onboarding
        composable(route = "onboarding") {
            OnboardingScreen(navController = navController)
        }

        // Trạm 0.5: Màn hình Bắt đầu / Đăng ký
        composable(route = "register") {
            RegisterScreen(navController = navController)
        }

        // Trạm 1: Màn hình chính
        composable(route = "home") {
            HomeScreen(viewModel = viewModel, navController = navController)
        }

        // Trạm 2: Màn hình thêm giao dịch
        composable(route = "add_transaction") {
            AddTransactionScreen(viewModel = viewModel, navController = navController)
        }
    }
}
