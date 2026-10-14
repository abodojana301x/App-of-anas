package com.example.model

enum class NavTab(val title: String, val iconName: String) {
    HOME("الرئيسية", "dynamic_feed"),
    PLATFORMS("المنصات", "share"),
    SPACES("مساحتي", "dashboard_customize"),
    ACTIVITY("النشاط", "insights"),
    SETTINGS("التخصيص", "tune")
}

enum class PlatformType(val displayName: String, val brandTag: String) {
    ALL("الكل", "all"),
    X("إكس", "x"),
    INSTAGRAM("إنستغرام", "instagram"),
    TIKTOK("تيك توك", "tiktok"),
    YOUTUBE("يوتيوب", "youtube"),
    FACEBOOK("فيسبوك", "facebook"),
    LINKEDIN("LinkedIn", "linkedin"),
    THREADS("Threads", "threads")
}

enum class FeedCategory(val label: String) {
    ALL("الكل (48)"),
    VIDEOS("فيديوهات"),
    POSTS("منشورات"),
    BOOKMARKS("المحفوظات"),
    TOP_ENGAGED("الأعلى تفاعلاً")
}

enum class MediaType {
    VIDEO_VERTICAL,
    POST_TEXT,
    VIDEO_WIDESCREEN,
    CAROUSEL_IMAGES
}

data class FeedItem(
    val id: String,
    val platformType: PlatformType,
    val authorName: String,
    val authorHandle: String,
    val authorAvatarUrl: String,
    val isVerified: Boolean = false,
    val timeAgo: String,
    val trendingBadge: String? = null,
    val contentText: String,
    val mediaType: MediaType,
    val mediaUrl: String? = null,
    val carouselImages: List<String> = emptyList(),
    val currentSlideIndex: Int = 0,
    val videoDuration: String? = null,
    val resolutionBadge: String? = null,
    val likesCount: String,
    val commentsCount: String,
    val sharesCount: String? = null,
    val impressionsCount: String? = null,
    val quoteMetricBadge: String? = null,
    val quoteActionLabel: String? = null,
    val isLiked: Boolean = false,
    val isSaved: Boolean = false,
    val category: FeedCategory = FeedCategory.ALL
)

data class ConnectedPlatform(
    val type: PlatformType,
    val name: String,
    val handleOrTitle: String,
    val isConnected: Boolean = true,
    val isSyncActive: Boolean = true,
    val lastSyncText: String,
    val statsBadge: String,
    val subDetailText: String,
    val actionButtonText: String
)

data class SpaceModule(
    val id: String,
    val title: String,
    val subtitle: String,
    val badgeText: String,
    val iconType: String,
    val order: Int
)

data class CustomSpace(
    val id: String,
    val title: String,
    val description: String,
    val isActive: Boolean,
    val subtitleTag: String,
    val platformChips: List<String>,
    val modules: List<SpaceModule>
)

enum class ActivityTab(val title: String, val iconName: String) {
    NOTIFICATIONS("الإشعارات", "notifications"),
    INTERACTIONS("التفاعلات", "forum"),
    HISTORY("سجل المشاهدة", "history"),
    ACCOUNTS("الحسابات", "link")
}

data class ActivityItem(
    val id: String,
    val title: String,
    val authorName: String,
    val authorHandle: String? = null,
    val avatarUrl: String? = null,
    val platformType: PlatformType,
    val timeAgo: String,
    val contentText: String,
    val highlightedText: String? = null,
    val mediaThumbnailUrl: String? = null,
    val videoDuration: String? = null,
    val isUnread: Boolean = false,
    val actionPrimaryLabel: String? = null,
    val actionSecondaryLabel: String? = null,
    val isSystemNotification: Boolean = false
)

data class AppPreferences(
    val themeMode: String = "dark", // "dark", "amoled", "light"
    val accentColorName: String = "violet", // "violet", "indigo", "emerald", "amber"
    val blurIntensity: String = "high", // "high" (قوي), "low" (ضعيف/سلس), "off" (إيقاف)
    val density: String = "spacious", // "spacious", "standard", "compact"
    val cornerRadiusStyle: String = "rounded", // "rounded", "soft", "sharp"
    val verticalVideosEnabled: Boolean = true,
    val silentPreviewEnabled: Boolean = true,
    val hideDuplicates: Boolean = false,
    val feedPriority: String = "chronological", // "chronological", "engagement", "affinity", "media"
    val startupScreen: NavTab = NavTab.HOME,
    val cacheSizeMb: Int = 142
)
