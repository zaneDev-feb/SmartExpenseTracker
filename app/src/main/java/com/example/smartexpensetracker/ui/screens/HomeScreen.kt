package com.example.smartexpensetracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartexpensetracker.data.model.Transaction
import com.example.smartexpensetracker.ui.components.DeleteConfirmationDialog
import com.example.smartexpensetracker.ui.components.HeroBalanceCard
import com.example.smartexpensetracker.ui.components.TransactionItem
import com.example.smartexpensetracker.ui.theme.BackgroundLight
import com.example.smartexpensetracker.ui.theme.PrimaryIndigo
import com.example.smartexpensetracker.ui.theme.TextDark
import com.example.smartexpensetracker.ui.theme.TextMuted
import com.example.smartexpensetracker.viewmodel.ExpenseViewModel

// ==========================================
// MÀN HÌNH CHÍNH (Home Screen)
// ==========================================
@Composable
fun HomeScreen(
    viewModel: ExpenseViewModel,
    navController: NavController
) {
    // State lưu giao dịch đang chờ người dùng xác nhận xóa
    var transactionToDelete by remember { mutableStateOf<Transaction?>(null) }

    // Hộp thoại xác nhận xóa (Chỉ hiện khi transactionToDelete khác null)
    transactionToDelete?.let { tx ->
        DeleteConfirmationDialog(
            transaction = tx,
            onConfirm = {
                viewModel.deleteTransaction(tx)
                transactionToDelete = null
            },
            onDismiss = {
                transactionToDelete = null
            }
        )
    }

    Scaffold(
        containerColor = BackgroundLight,
        // Nút tròn nổi (Floating Action Button) thêm giao dịch nhanh bằng 1 tay
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_transaction") },
                containerColor = PrimaryIndigo,
                contentColor = Color.White,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Thêm giao dịch",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 24.dp, bottom = 80.dp)
        ) {
            // 1. HEADER CHÀO MỪNG
            item {
                Text(
                    text = "Xin chào! 👋",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Text(
                    text = "Quản lý tài chính thông minh",
                    fontSize = 14.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }

            // 2. HERO BALANCE CARD (Thẻ số dư ví điện tử)
            item {
                HeroBalanceCard(
                    balance = viewModel.balance,
                    income = viewModel.income,
                    expense = viewModel.expense,
                    tyGiaVND = viewModel.tyGiaVND,
                    onRefreshRate = { viewModel.capNhatTyGia() },
                    modifier = Modifier.padding(bottom = 28.dp)
                )
            }

            // 3. TIÊU ĐỀ DANH SÁCH
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Lịch sử giao dịch",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        text = "${viewModel.history.size} mục",
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                }
            }

            // 4. DANH SÁCH HOẶC TRẠNG THÁI RỖNG (EMPTY STATE)
            if (viewModel.history.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "🍃", fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Chưa có giao dịch nào",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextDark
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Bấm nút dấu '+' bên dưới để bắt đầu ghi chép nhé!",
                                fontSize = 13.sp,
                                color = TextMuted
                            )
                        }
                    }
                }
            } else {
                items(
                    items = viewModel.history,
                    key = { it.id }
                ) { tx ->
                    TransactionItem(
                        transaction = tx,
                        onDeleteClick = { transactionToDelete = tx },
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
            }
        }
    }
}
