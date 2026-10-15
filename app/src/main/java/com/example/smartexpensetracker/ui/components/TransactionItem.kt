package com.example.smartexpensetracker.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartexpensetracker.data.model.Transaction
import com.example.smartexpensetracker.ui.theme.*
import java.text.DecimalFormat

// ==========================================
// COMPONENT: THẺ GIAO DỊCH (Transaction Item)
// ==========================================
@Composable
fun TransactionItem(
    transaction: Transaction,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formatter = DecimalFormat("#,###")
    val isIncome = transaction.isIncome
    val amountColor = if (isIncome) MintIncome else CoralExpense
    val iconBg = if (isIncome) MintIncomeBg else CoralExpenseBg
    val sign = if (isIncome) "+" else "-"
    val emoji = if (isIncome) "💰" else "💸"

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        border = BorderStroke(1.dp, BorderSubtle),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Phần bên trái: Icon tròn và Tiêu đề
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = emoji, fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = transaction.title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = TextDark
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (isIncome) "Khoản thu" else "Khoản chi",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
            }

            // Phần bên phải: Số tiền và nút xóa
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$sign${formatter.format(transaction.amount)} đ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = amountColor
                )

                Spacer(modifier = Modifier.width(6.dp))

                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Xóa",
                        tint = Color(0xFF94A3B8) // Xám vừa, trang nhã
                    )
                }
            }
        }
    }
}
