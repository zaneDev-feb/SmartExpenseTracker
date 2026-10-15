package com.example.smartexpensetracker.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartexpensetracker.data.model.Transaction
import com.example.smartexpensetracker.ui.theme.CoralExpense
import java.text.DecimalFormat

// ==========================================
// COMPONENT: DIALOG XÁC NHẬN XÓA (Defensive UX)
// ==========================================
@Composable
fun DeleteConfirmationDialog(
    transaction: Transaction,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val formatter = DecimalFormat("#,###")

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        title = {
            Text(
                text = "Xác nhận xóa?",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        text = {
            Text(
                text = "Bạn có chắc chắn muốn xóa giao dịch \"${transaction.title}\" (${formatter.format(transaction.amount)} đ) không? Thao tác này không thể hoàn tác.",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = CoralExpense),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Xóa", color = Color.White, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Hủy")
            }
        }
    )
}
