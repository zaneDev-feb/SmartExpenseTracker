package com.example.smartexpensetracker.data.model

// ==========================================
// 1. KHUÔN MẪU DỮ LIỆU CỦA 1 TRANG ONBOARDING
// ==========================================
data class OnboardingPage(
    val emoji: String,       // Biểu tượng linh vật Mèo Thần Tài
    val badge: String,       // Huy hiệu phân loại (VD: MANEKI-NEKO, TIẾT KIỆM...)
    val title: String,       // Tiêu đề lớn
    val description: String  // Đoạn văn miêu tả
)

// ==========================================
// 2. DANH SÁCH 5 TRANG ONBOARDING ĐẦY ĐỦ
// ==========================================
val onboardingPagesList = listOf(
    // Trang 1: Chào đón & Kiểm soát tiền bạc
    OnboardingPage(
        emoji = "🐱🏮",
        badge = "CHIÊU TÀI HÚT LỘC",
        title = "Kiểm Soát Tiền Của Bạn\nNgay Hôm Nay",
        description = "Theo dõi chi phí trong vài giây, xem tiền của bạn được chi vào đâu và kiểm soát ngân sách mà không bị căng thẳng."
    ),

    // Trang 2: Nỗi đau quên ghi chép
    OnboardingPage(
        emoji = "😿💸",
        badge = "TIẾT KIỆM THÔNG MINH",
        title = "Tiền Biến Mất Trước Khi\nBạn Kịp Nhận Ra?",
        description = "Bạn quên theo dõi chi phí, hóa đơn ngày càng nhiều và bạn không bao giờ biết chắc tiền của mình thực sự đi về đâu."
    ),

    // Trang 3: Ghi chép chi tiêu siêu tốc
    OnboardingPage(
        emoji = "📝🐾",
        badge = "SIÊU NHANH GỌN",
        title = "Thêm Chi Tiêu Dễ Dàng",
        description = "Ghi lại chi tiêu của bạn nhanh chóng, đơn giản và trực quan như một cái vẫy móng vuốt may mắn."
    ),

    // Trang 4: Phân loại hóa đơn tự động
    OnboardingPage(
        emoji = "🧾✨",
        badge = "QUẢN LÝ TỰ ĐỘNG",
        title = "Phân Loại Khoản Chi\nNgay Lập Tức",
        description = "Mọi khoản chi tiêu từ ăn uống, mua sắm đến sinh hoạt phí đều được Mèo Thần Tài tổng hợp gọn gàng cho bạn."
    ),

    // Trang 5: Nhận phân tích thông minh (Mới thêm)
    OnboardingPage(
        emoji = "📊🐱",
        badge = "TRỢ LÝ TÀI CHÍNH",
        title = "Nhận Phân Tích\nThông Minh",
        description = "Phân tích thói quen chi tiêu của bạn, phát hiện các khoản ăn ngoài bất thường và nhận lời khuyên ngân sách cá nhân hóa."
    )
)
