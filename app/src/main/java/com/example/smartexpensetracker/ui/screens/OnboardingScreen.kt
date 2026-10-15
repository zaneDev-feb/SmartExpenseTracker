package com.example.smartexpensetracker.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartexpensetracker.data.model.onboardingPagesList
import com.example.smartexpensetracker.ui.theme.BackgroundLight
import com.example.smartexpensetracker.ui.theme.PrimaryDark
import com.example.smartexpensetracker.ui.theme.PrimaryIndigo
import com.example.smartexpensetracker.ui.theme.TextDark
import com.example.smartexpensetracker.ui.theme.TextMuted
import kotlinx.coroutines.launch

// ==========================================
// MÀN HÌNH ONBOARDING (5 TRANG VUỐT LẬT NGANG)
// ==========================================
@Composable
fun OnboardingScreen(navController: NavController) {
    val pages = onboardingPagesList
    // Khởi tạo trạng thái vuốt lật 5 trang
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == pages.size - 1

    Scaffold(
        containerColor = BackgroundLight
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            // Hàng trên cùng: Nút "Bỏ qua"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (!isLastPage) {
                    TextButton(
                        onClick = {
                            // Bỏ qua: Nhảy thẳng đến màn hình Đăng ký / Bắt đầu
                            navController.navigate("register") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        }
                    ) {
                        Text(
                            text = "Bỏ qua",
                            color = TextMuted,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }

            // ==========================================
            // PHẦN VUỐT TRƯỢT NGANG (HorizontalPager)
            // ==========================================
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { pageIndex ->
                val page = pages[pageIndex]
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // 1. Huy hiệu nhỏ phía trên
                    Surface(
                        color = PrimaryIndigo.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = page.badge,
                            color = PrimaryIndigo,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }

                    // 2. Tiêu đề lớn
                    Text(
                        text = page.title,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextDark,
                        textAlign = TextAlign.Center,
                        lineHeight = 36.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // 3. Đoạn mô tả
                    Text(
                        text = page.description,
                        fontSize = 15.sp,
                        color = TextMuted,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    // 4. Linh vật Mèo Thần Tài (Bọc trong khung tròn tỏa ánh sáng)
                    Box(
                        modifier = Modifier
                            .size(190.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        PrimaryIndigo.copy(alpha = 0.15f),
                                        Color.Transparent
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = page.emoji, fontSize = 88.sp)
                    }
                }
            }

            // ==========================================
            // PHẦN ĐÁY: DẢI GẠCH CHỈ BÁO & NÚT TIẾP TỤC
            // ==========================================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 36.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // DẢI GẠCH CHỈ SỐ TRANG (5 gạch)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(pages.size) { index ->
                        val isSelected = pagerState.currentPage == index
                        // Gạch trang đang xem sẽ tự động dài ra (28dp), các trang khác ngắn (8dp)
                        val width by animateDpAsState(
                            targetValue = if (isSelected) 28.dp else 8.dp,
                            label = "indicatorWidth"
                        )
                        Box(
                            modifier = Modifier
                                .height(6.dp)
                                .width(width)
                                .clip(RoundedCornerShape(3.dp))
                                .background(
                                    if (isSelected) PrimaryDark else Color.LightGray.copy(alpha = 0.5f)
                                )
                        )
                    }
                }

                // NÚT TRÒN TIẾP THEO (Next Button)
                FloatingActionButton(
                    onClick = {
                        if (isLastPage) {
                            // Khi hết trang 5: Chở người dùng sang màn hình Đăng ký / Bắt đầu
                            navController.navigate("register") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        } else {
                            // Chưa hết: Trượt sang trang tiếp theo
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                    containerColor = PrimaryDark,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Tiếp theo",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
