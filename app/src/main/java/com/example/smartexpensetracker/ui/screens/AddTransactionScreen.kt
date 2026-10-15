package com.example.smartexpensetracker.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartexpensetracker.ui.theme.*
import com.example.smartexpensetracker.viewmodel.ExpenseViewModel

// ==========================================
// MÀN HÌNH NHẬP GIAO DỊCH (Add Transaction Screen)
// ==========================================
@Composable
fun AddTransactionScreen(
    viewModel: ExpenseViewModel,
    navController: NavController
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var isIncome by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = BackgroundLight,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Quay lại",
                        tint = TextDark
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Thêm giao dịch mới",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 1. THANH CHỌN LOẠI THU / CHI (Segmented Control)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Nút chọn Thu Nhập
                OutlinedButton(
                    onClick = { isIncome = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isIncome) MintIncome else Color.Transparent
                    ),
                    border = BorderStroke(
                        width = 1.5.dp,
                        color = if (isIncome) MintIncome else BorderSubtle
                    )
                ) {
                    Text(
                        text = "💰 Thu Nhập",
                        fontWeight = FontWeight.Bold,
                        color = if (isIncome) Color.White else TextMuted
                    )
                }

                // Nút chọn Chi Tiêu
                OutlinedButton(
                    onClick = { isIncome = false },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (!isIncome) CoralExpense else Color.Transparent
                    ),
                    border = BorderStroke(
                        width = 1.5.dp,
                        color = if (!isIncome) CoralExpense else BorderSubtle
                    )
                ) {
                    Text(
                        text = "💸 Chi Tiêu",
                        fontWeight = FontWeight.Bold,
                        color = if (!isIncome) Color.White else TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // 2. Ô NHẬP TIÊU ĐỀ
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Tiêu đề") },
                placeholder = { Text("VD: Ăn phở, Mua sắm, Lương...") },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryIndigo,
                    unfocusedBorderColor = BorderSubtle
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 3. Ô NHẬP SỐ TIỀN (Bàn phím tự nhảy sang số)
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Số tiền (VNĐ)") },
                placeholder = { Text("VD: 50000") },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryIndigo,
                    unfocusedBorderColor = BorderSubtle
                )
            )

            Spacer(modifier = Modifier.height(36.dp))

            // 4. NÚT LƯU GIAO DỊCH
            val parsedAmount = amount.toDoubleOrNull() ?: 0.0
            val isValid = title.isNotBlank() && parsedAmount > 0

            Button(
                onClick = {
                    if (isValid) {
                        viewModel.addTransaction(title.trim(), parsedAmount, isIncome)
                        navController.popBackStack()
                    }
                },
                enabled = isValid,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryIndigo,
                    disabledContainerColor = Color(0xFFCBD5E1)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Lưu Giao Dịch",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
