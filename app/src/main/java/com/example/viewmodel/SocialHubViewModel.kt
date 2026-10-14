package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SocialHubViewModel : ViewModel() {

    private val _currentTab = MutableStateFlow(NavTab.HOME)
    val currentTab: StateFlow<NavTab> = _currentTab.asStateFlow()

    private val _selectedPlatform = MutableStateFlow(PlatformType.ALL)
    val selectedPlatform: StateFlow<PlatformType> = _selectedPlatform.asStateFlow()

    private val _selectedCategory = MutableStateFlow(FeedCategory.ALL)
    val selectedCategory: StateFlow<FeedCategory> = _selectedCategory.asStateFlow()

    private val _feedItems = MutableStateFlow<List<FeedItem>>(emptyList())
    val feedItems: StateFlow<List<FeedItem>> = _feedItems.asStateFlow()

    private val _connectedPlatforms = MutableStateFlow<List<ConnectedPlatform>>(emptyList())
    val connectedPlatforms: StateFlow<List<ConnectedPlatform>> = _connectedPlatforms.asStateFlow()

    private val _activeSpace = MutableStateFlow<CustomSpace?>(null)
    val activeSpace: StateFlow<CustomSpace?> = _activeSpace.asStateFlow()

    private val _savedSpaces = MutableStateFlow<List<CustomSpace>>(emptyList())
    val savedSpaces: StateFlow<List<CustomSpace>> = _savedSpaces.asStateFlow()

    private val _activityTab = MutableStateFlow(ActivityTab.NOTIFICATIONS)
    val activityTab: StateFlow<ActivityTab> = _activityTab.asStateFlow()

    private val _activityPlatformFilter = MutableStateFlow(PlatformType.ALL)
    val activityPlatformFilter: StateFlow<PlatformType> = _activityPlatformFilter.asStateFlow()

    private val _activityItems = MutableStateFlow<List<ActivityItem>>(emptyList())
    val activityItems: StateFlow<List<ActivityItem>> = _activityItems.asStateFlow()

    private val _preferences = MutableStateFlow(AppPreferences())
    val preferences: StateFlow<AppPreferences> = _preferences.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isSearchVisible = MutableStateFlow(false)
    val isSearchVisible: StateFlow<Boolean> = _isSearchVisible.asStateFlow()

    private val _isCreateSpaceModalOpen = MutableStateFlow(false)
    val isCreateSpaceModalOpen: StateFlow<Boolean> = _isCreateSpaceModalOpen.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        val initialFeed = listOf(
            FeedItem(
                id = "tiktok_1",
                platformType = PlatformType.TIKTOK,
                authorName = "سامي الفهيد",
                authorHandle = "TikTok",
                authorAvatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBPFmV_FQ9Lv38GiZCrhKlmedn78uUgK_TTT_XElypX4GZnEE1IlxQqwOcuzJlDPabrgWhylH2Y5iZW_7v1XeOcRPUEMNVUAwo61hsEzFBVr4anrbzlKnEApFCVYhuF1BeM9TQ_ptsmS_dcIvQ1F1dPdaZ3SQA7Lzt-SEcqVfVy7yuVe7_KKveGrEkd-UXWPsjtKpUus3YtM-WZNp8UrN3_3vz5AvQfl5D4DAGdhoM9yGCk3VDCgQs",
                isVerified = true,
                timeAgo = "منذ ساعتين • TikTok",
                trendingBadge = "رائج",
                contentText = "أقوى تحديث لتقنيات الذكاء الاصطناعي التوليدي في تصميم واجهات المستخدم المعقدة 🎨✨ شاهد النتيجة قبل وبعد!",
                mediaType = MediaType.VIDEO_VERTICAL,
                mediaUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBeM6WOIrAeBJrBJ2sXM8kNPUEZxbj9PCnc9-1rfDr6cpaVw5TXBSdt_QU-6FwSm_uoVfQ7WNAArFPIJsnuwhcrx2SidhXPqxIoztD3eD1tA-Ujwm1YN4zlRVr7akFxLjD3GfpVLdAfvShZCi2eM7ngdrKF_ePK4xTPX1jGUTA_n4sZMrFFHpVRYpjB5Fi32hXA6xaXNu4J5-Ammep7gIvzBqtPdD87bH-KYjr5UP_Kbk309mx7yP8",
                videoDuration = "0:45",
                likesCount = "142K",
                commentsCount = "1.8K",
                sharesCount = "14.2K",
                category = FeedCategory.VIDEOS
            ),
            FeedItem(
                id = "x_1",
                platformType = PlatformType.X,
                authorName = "د. طارق المنصور",
                authorHandle = "@t_almansoor • 4س",
                authorAvatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDlacp41NCsjvTUEZ8J6fma3D9FwTsuEcQAZemMoMBPj_hn-t7HVpQZSHuiN94BlpI-l-V1BV0dcc_TzT7Wa2wZcURdL-KuuyN6EhLasjPOYgt7sALKYc08e_exz_NSejTBhEPxU59lDqUuR7wECWU05IlmiSDWpzJ8zQiSpZzxFLGaEuK2Ppt_1qUVuv5INfljco9t4i8deOgne-zrCjRfvZlqE7QhmxkSs2KPqNQH8UWwN5O88_s",
                isVerified = false,
                timeAgo = "4س",
                trendingBadge = "تحليل معماري",
                contentText = "التحول الحالي في معمارية الأنظمة الموزعة نحو نماذج الحوسبة الطرفية (Edge Computing) لا يتعلق فقط بسرعة الاستجابة؛ بل بإعادة ابتكار تجربة الخصوصية من الصفر.\n\nالمنصات التي تدمج المعالجة المحلية أولاً ستحقق تفوقاً استراتيجياً بنسبة تتجاوز 40% في الاحتفاظ بالمستخدمين النشطين خلال العام القادم. ثريد تفصيلي 🧵👇",
                mediaType = MediaType.POST_TEXT,
                quoteMetricBadge = "معدل الاقتباس المرتفع: +320% اليوم",
                quoteActionLabel = "قراءة التقرير",
                likesCount = "4.1K",
                commentsCount = "284",
                sharesCount = "892",
                impressionsCount = "96K",
                category = FeedCategory.POSTS
            ),
            FeedItem(
                id = "youtube_1",
                platformType = PlatformType.YOUTUBE,
                authorName = "استوديو العتاد التقني",
                authorHandle = "يوتيوب • 850 ألف مشترك",
                authorAvatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuD1TmplJO9xb9ZeQAgJFfESkUbGPczWUv4nXz_UGm7WkBPwU3-LyaCK24bDwmTGmr_RCNUmdiy84NhefRFbUNTCXWW0UiW9TdFyrx5dGP_QHdjc3ilkVUB_fWdquJD-lBFdGaJ1lQkxnWs7lDoQYAVT2Sckk9UXeh-c7e51HFuzlTssWVb1Y_E6YGSqDIZuxh6h0msZ94Kz_119VLq5jyYlUnCtXPJkQ1qXhluOw9Fd4deJ2YTm3Pc",
                isVerified = true,
                timeAgo = "منذ 5 ساعات",
                trendingBadge = "فيديو كامل",
                contentText = "المقارنة الشاملة: هل يستحق حاسوب الذكاء الاصطناعي المحلي الترقية في 2025؟\n43 ألف مشاهدة • منذ 5 ساعات • 98% تقييم إيجابي",
                mediaType = MediaType.VIDEO_WIDESCREEN,
                mediaUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuC24OBqZXXFpAjW2rUUC48yuquk2FfnQ9wecQ7kbEQ1RirY93abVhI0GO7c7jaSF2nsT_F3f5lxX_0NQPhhoS8Kup_wpTb5iHwQStO-qhZ_AIODdsyqFvc7z6PDGA3szyScipjVXC41st0EItMIwgiE_so66zKxzpGZdpVXikIYxfM8EbGzrNbuDYMVqiKv0XlvDVhYuLan_n4k3ieJIWaNqYxhmUwW3TQpuPe9vXE4nMQmKlXW2Bg",
                videoDuration = "14:20",
                resolutionBadge = "4K HDR",
                likesCount = "12K",
                commentsCount = "940",
                category = FeedCategory.VIDEOS
            ),
            FeedItem(
                id = "instagram_1",
                platformType = PlatformType.INSTAGRAM,
                authorName = "نورة السبيعي",
                authorHandle = "الرياض • إنستغرام",
                authorAvatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuChM3rl2xwUnDVIuYWMdpmmxn-TJImUlOz0bvbXFjLzh_NixKL_A_1XO1holssE345DfZjqdahNfj0bmX6O_z03YYZdCJNE4kQCsTalLj3FHol3KXXsBGVqBWZfHVxewpimTiUTCUv-jYNNBaQ3swVQP8_-Ciyk0T5RNF7FbZeQ2sFE8jegbkG6vEG_f-K2REcacvQmfr-ULU8-EVUf76luU8OsZ_VFDI-m5nNw_kMqN-1tluNtNno",
                isVerified = true,
                timeAgo = "الرياض",
                contentText = "من سحر الهدوء والخطوط البنائية في مشروع صحراء العلا.. الطبيعة دائماً تصنع أجمل التفاصيل عند امتزاجها بالنور 🕊️🏛️",
                mediaType = MediaType.CAROUSEL_IMAGES,
                carouselImages = listOf(
                    "https://lh3.googleusercontent.com/aida-public/AB6AXuBW35_RZp06sYaTzFuMTDd54DL6tn12s-BCuOhbH3q4kWJCARgpDUN66r_CylPosy1gRjEWBr6A38TVQ5F1YRnWaaOTFFdISJCIczCuVeY7t3gQx8tlZaOWJNOO9nDyQZrJrm8w9NpnTu7x0kF3iIaounrg_e0sWf4nF8quhCzETusTMSyd2bJ5AEpC7pWKNV9l_cT7YZF5f0oCMYlIuSwHz11zdII1W_Pszk4aY7Y2-Wv3mq66Rq4",
                    "https://lh3.googleusercontent.com/aida-public/AB6AXuDMztJhDH0M2kZ4NQI3c4rRkvoS_nBM60xOE9sZnT3JDmp5XWQztO8yWuamXhtL6oIhsyntsGhF5wpAXjS9q4xF44JaEd8CWiVWcKqyHL2xKmLz2pJgBoEAqDMaDske8abTVU6Ekaa6NllCEldoUD2dm5g2HwtDB101IIhCx3DyCzys7dPPZkBrxW9XXiuke7PiZiyA7S-K9vhGdsRAYzCu9B_BVHRjzeri148eUnzQ37Ja5r72bNk",
                    "https://lh3.googleusercontent.com/aida-public/AB6AXuDOCbf2nSpLHCtoicDkyBuOzsGzAg48Cl-ydKDwaz0T2L19yFRp409Mqu5SWXrlGLyJ06s0Eyp4SZhsdAMTNqWghw0Cxfu9Zq9BsO5r9wj5R7roO2irg43fMjwQuVvLp0jTfVVdeMRtDpOmsVfD5_nyVgfuDdUNdiLFvJEf2WDymFfLgHFVoxaLP3oBqhKvdVf6MZc0jun867J96bm-FZv7P2XR5oqMNul_No47_5127TgbQHuooyc"
                ),
                currentSlideIndex = 0,
                likesCount = "4,912",
                commentsCount = "84",
                category = FeedCategory.POSTS
            )
        )
        _feedItems.value = initialFeed

        _connectedPlatforms.value = listOf(
            ConnectedPlatform(
                type = PlatformType.X,
                name = "منصة X",
                handleOrTitle = "@tech_arabic",
                isConnected = true,
                isSyncActive = true,
                lastSyncText = "متصل • آخر تحديث منذ دقيقتين",
                statsBadge = "18 منشور جديد اليوم",
                subDetailText = "الظهور في الموجز: مفعل بالكامل",
                actionButtonText = "إدارة الحساب"
            ),
            ConnectedPlatform(
                type = PlatformType.INSTAGRAM,
                name = "Instagram",
                handleOrTitle = "@design.studio",
                isConnected = true,
                isSyncActive = true,
                lastSyncText = "متصل • نشط الآن",
                statsBadge = "4 قصص غير مقروءة • 12 منشور",
                subDetailText = "دمج الوسائط المرئية",
                actionButtonText = "تخصيص الظهور"
            ),
            ConnectedPlatform(
                type = PlatformType.YOUTUBE,
                name = "YouTube",
                handleOrTitle = "استوديو الإبداع العربي",
                isConnected = true,
                isSyncActive = true,
                lastSyncText = "متصل • القنوات المشتركة متزامنة",
                statsBadge = "6 فيديوهات جديدة في الاشتراكات",
                subDetailText = "إشعارات القنوات المفضلة",
                actionButtonText = "ضبط الإشعارات"
            ),
            ConnectedPlatform(
                type = PlatformType.TIKTOK,
                name = "TikTok",
                handleOrTitle = "@creator.mena",
                isConnected = true,
                isSyncActive = true,
                lastSyncText = "متصل • تلقي الخلاصات الفورية",
                statsBadge = "24 فيديو مقترح في الخلاصة",
                subDetailText = "تصفية المحتوى السريع",
                actionButtonText = "تعديل الأولويات"
            ),
            ConnectedPlatform(
                type = PlatformType.FACEBOOK,
                name = "Facebook",
                handleOrTitle = "مجتمع المطورين والمصممين",
                isConnected = true,
                isSyncActive = true,
                lastSyncText = "مفعل • مزامنة المجموعات",
                statsBadge = "8 تحديثات من المجموعات",
                subDetailText = "موجز الصفحات والمجموعات",
                actionButtonText = "إدارة المصادر"
            )
        )

        val activeSpaceModules = listOf(
            SpaceModule(
                id = "mod_1",
                title = "وحدة التغريدات الحية",
                subtitle = "تحديث فوري • مجتمع هندسة الأوامر",
                badgeText = "الارتفاع: تلقائي",
                iconType = "dynamic_feed",
                order = 0
            ),
            SpaceModule(
                id = "mod_2",
                title = "شريط الفيديوهات القصيرة (Reels & Shorts)",
                subtitle = "عرض أفقي سريع للتطبيقات العملية",
                badgeText = "كاروسيل",
                iconType = "slow_motion_video",
                order = 1
            ),
            SpaceModule(
                id = "mod_3",
                title = "مقاطع يوتيوب التحليلية الطويلة",
                subtitle = "شروحات ومناقشات أسبوعية تفصيلية",
                badgeText = "شبكة عريضة",
                iconType = "ondemand_video",
                order = 2
            )
        )

        _activeSpace.value = CustomSpace(
            id = "space_tech_ai",
            title = "مساحة التقنية والذكاء الاصطناعي",
            description = "تجمع أحدث إطلاقات النماذج اللغوية، تحليلات يوتيوب العميقة، ومنشورات مجتمع المطورين والمؤسسين.",
            isActive = true,
            subtitleTag = "المساحة النشطة حالياً في الخلاصة",
            platformChips = listOf("YouTube Tech (١٤ قناة)", "X: AI & LLMs (٢٨ حساباً)", "أوراق بحثية ومقالات"),
            modules = activeSpaceModules
        )

        _savedSpaces.value = listOf(
            CustomSpace(
                id = "space_learning",
                title = "مساحة التعلم والدراسة",
                description = "دورات يوتيوب البرمجية ومسارات الذكاء الاصطناعي مع تلخيصات نصية من مصادر موثوقة بدون إشعارات مباغتة.",
                isActive = false,
                subtitleTag = "تركيز كامل • حجب المنشورات الترفيهية",
                platformChips = listOf("٦ قنوات يوتيوب", "وضع القراءة الهادئة"),
                modules = emptyList()
            ),
            CustomSpace(
                id = "space_chill",
                title = "مساحة الترفيه والاسترخاء",
                description = "تغذية بصرية ممتعة ومقاطع قصيرة مختارة من تيك توك وإنستغرام ريلز بدون أي أخبار سياسية أو ضغوط عمل.",
                isActive = false,
                subtitleTag = "ريلز ومقاطع فيديو مرحة فقط",
                platformChips = listOf("TikTok Curated", "Instagram Creators"),
                modules = emptyList()
            ),
            CustomSpace(
                id = "space_news",
                title = "مساحة متابعة الأخبار العاجلة",
                description = "تنبيهات فورية وحسابات وكالات الأخبار الرسمية عبر منصة X مع تجنب الشائعات والردود العشوائية.",
                isActive = false,
                subtitleTag = "تحديث زمني لحظي للمصادر الرسمية",
                platformChips = listOf("وكالات رسمية (١٢)", "بث مباشر عاجل"),
                modules = emptyList()
            )
        )

        _activityItems.value = listOf(
            ActivityItem(
                id = "act_1",
                title = "أعادت نشر تحليلك الأخير",
                authorName = "سارة المهندس",
                authorHandle = "@SarahDev",
                avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCvFqCo8I6KLfNGAtJgCfEGTCeXl_-4EInSeahr7OslJRbKefPL80yuh9O9Mqse_QDuaqrz1zoPEsv0BS75293JXjk5hfTYcDVSbkQuVtnS3BkvIRLQL5k9deq4m8Ms2Xz2ytFTPfeuO_19-nyx7rmK4L3oTWTTCninv6C3I-2OFyumBnc_0DzysfTM5BgRhYNx_2YTrME1FeawfghqXLCYJ-VBZ83YkSs-AFWpH6vcaDJUnDDQ2Oo",
                platformType = PlatformType.X,
                timeAgo = "منذ 15 دقيقة",
                contentText = "أعادت نشر تحليلك الأخير: «مقارنة البنية التحتية للحوسبة الموزعة في الشرق الأوسط» مع تعليق مميز.",
                highlightedText = "«مقارنة البنية التحتية للحوسبة الموزعة في الشرق الأوسط»",
                isUnread = true,
                actionPrimaryLabel = "رد سريع"
            ),
            ActivityItem(
                id = "act_2",
                title = "فيديو جديد",
                authorName = "TechWorld",
                authorHandle = "منصة خارجية • YouTube",
                platformType = PlatformType.YOUTUBE,
                timeAgo = "منذ ساعتين",
                contentText = "نشرت فيديو جديد: «مستقبل النماذج اللغوية العملاقة وكفاءة المعالجة المحلية»",
                mediaThumbnailUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCLe0L_n-FcbZ4l5vKRqX9wux-vE8CaRZsuKVyPyFV-RJGmInBTJ4W9IjYwg7FkzAMy2Hh2EXF0Vzmnid03-Jl15ZdkIC4QMj7ThnJKsFj_UHjwkIxTtAWukqy1kJUGc5dG-WC5f38Err74VJTMtelaH68W3sasRYcAlfncWKEAizPk_u0VCtaG7jL2j0L54nXpusYADShAr6ON5xTidjYMiKj1D00cm6t_q3-h8dFTN1ZRdvBGk88",
                videoDuration = "18:42",
                isUnread = true,
                actionPrimaryLabel = "مشاهدة لاحقاً",
                actionSecondaryLabel = "فتح في التطبيق"
            ),
            ActivityItem(
                id = "act_3",
                title = "تفاعل وتعليق",
                authorName = "عمر الفن",
                authorHandle = "منصة خارجية • Instagram",
                avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCN0UkNEeevPRB5zYbmkACUzHW5m7icZFphiU7tQimxWs3ynTPaas0jGi9tbJ3fmukdM0uhZ-EP-m2LvlOywEtPxqEDrrAAz1xlV7ZJA3C0fI7ESFbsSsxWGSxKc1KCEFe6LqbFRIBcELn0cuL6CS-0UYk_M0DvfYZmfkgpsIkXzPVNeS0vd7JzVroJguoiiDbKxo-TRr79BsfkHGSPndlDSG5U8Fs5hY22x_tyOPSkpQegv-7rYyI",
                platformType = PlatformType.INSTAGRAM,
                timeAgo = "منذ 3 ساعات",
                contentText = "أعجب بمنشورك وترك تعليقاً:\n«زاوية ممتازة وتحليل منطقي دقيق، واصل هذا الإبداع!»",
                mediaThumbnailUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuARnY6UTJEypwbNO8D4iYW6thKjxX73Epi40VWZ2X1xfkwgwsGbwJlMiFW39mMzGOlNaEibrkwvUAPRAtLItSayrbQhl7GrYXBwwHfdgLMUAHZveCNuqPX7rdjOotD59LoABXcCWaC_zl4iABHC7YAblHpBeYXYMqWipzdIoG98bDEXGGqJ3H6D4IfGsSgRZC-lQxdhSZ_LXcQsxGyDD2CqpTjsJvk4oezr_81r17xPZEkikQa-kIo",
                isUnread = true,
                actionPrimaryLabel = "إعجاب بالتعليق",
                actionSecondaryLabel = "عرض المنشور"
            ),
            ActivityItem(
                id = "act_4",
                title = "تحديث الترند والانتشار",
                authorName = "تحديث الترند والانتشار",
                authorHandle = "منصة خارجية • TikTok",
                platformType = PlatformType.TIKTOK,
                timeAgo = "منذ 5 ساعات",
                contentText = "المحتوى الذي قمت بحفظه تخطى 1,000,000 مشاهدة اليوم.\nمعدل التفاعل صعد بنسبة +34%",
                isUnread = false,
                actionPrimaryLabel = "تحليلات المحتوى"
            ),
            ActivityItem(
                id = "act_5",
                title = "تنبيه النظام الموحد",
                authorName = "تنبيه النظام الموحد",
                authorHandle = "مساحة العمل • التقنية • الآن",
                platformType = PlatformType.ALL,
                timeAgo = "الآن",
                contentText = "تمت مزامنة جميع مصادر «مساحة التقنية» بنجاح. كافة التحديثات التلقائية متزامنة الآن وتعمل وفق الجدول الزمني المحدد دون أخطاء.",
                isUnread = false,
                isSystemNotification = true,
                actionPrimaryLabel = "إدارة المصادر"
            )
        )
    }

    fun setTab(tab: NavTab) {
        _currentTab.value = tab
    }

    fun setPlatform(platform: PlatformType) {
        _selectedPlatform.value = platform
    }

    fun setCategory(category: FeedCategory) {
        _selectedCategory.value = category
    }

    fun toggleLike(feedItemId: String) {
        _feedItems.update { items ->
            items.map { item ->
                if (item.id == feedItemId) {
                    item.copy(isLiked = !item.isLiked)
                } else item
            }
        }
    }

    fun toggleSave(feedItemId: String) {
        _feedItems.update { items ->
            items.map { item ->
                if (item.id == feedItemId) {
                    val nextSaved = !item.isSaved
                    showToast(if (nextSaved) "تم حفظ المنشور في الفهرس" else "تمت إزالة الحفظ")
                    item.copy(isSaved = nextSaved)
                } else item
            }
        }
    }

    fun nextSlide(feedItemId: String) {
        _feedItems.update { items ->
            items.map { item ->
                if (item.id == feedItemId && item.carouselImages.isNotEmpty()) {
                    val nextIndex = (item.currentSlideIndex + 1) % item.carouselImages.size
                    item.copy(currentSlideIndex = nextIndex)
                } else item
            }
        }
    }

    fun prevSlide(feedItemId: String) {
        _feedItems.update { items ->
            items.map { item ->
                if (item.id == feedItemId && item.carouselImages.isNotEmpty()) {
                    val prevIndex = (item.currentSlideIndex - 1 + item.carouselImages.size) % item.carouselImages.size
                    item.copy(currentSlideIndex = prevIndex)
                } else item
            }
        }
    }

    fun togglePlatformSync(type: PlatformType) {
        _connectedPlatforms.update { list ->
            list.map { platform ->
                if (platform.type == type) {
                    val nextSync = !platform.isSyncActive
                    showToast(if (nextSync) "تم تفعيل مزامنة ${platform.name}" else "تم إيقاف مزامنة ${platform.name} مؤقتاً")
                    platform.copy(isSyncActive = nextSync)
                } else platform
            }
        }
    }

    fun refreshAllSync() {
        _isRefreshing.value = true
        showToast("جارٍ تحديث المزامنة مع كافة المنصات...")
        // reset refreshing state after a short delay
        _isRefreshing.value = false
        showToast("اكتملت المزامنة بنجاح! 5 من 5 منصات متزامنة بنشاط")
    }

    fun switchActiveSpace(newSpaceId: String) {
        val currentActive = _activeSpace.value
        val target = _savedSpaces.value.find { it.id == newSpaceId } ?: return

        val updatedSaved = _savedSpaces.value.filter { it.id != newSpaceId }.toMutableList()
        if (currentActive != null) {
            updatedSaved.add(currentActive.copy(isActive = false))
        }

        _activeSpace.value = target.copy(isActive = true)
        _savedSpaces.value = updatedSaved
        showToast("تم تفعيل «${target.title}» كمساحة رئيسية")
    }

    fun moveModuleUp(moduleId: String) {
        val space = _activeSpace.value ?: return
        val modules = space.modules.toMutableList()
        val index = modules.indexOfFirst { it.id == moduleId }
        if (index > 0) {
            val item = modules.removeAt(index)
            modules.add(index - 1, item)
            _activeSpace.value = space.copy(modules = modules)
        }
    }

    fun moveModuleDown(moduleId: String) {
        val space = _activeSpace.value ?: return
        val modules = space.modules.toMutableList()
        val index = modules.indexOfFirst { it.id == moduleId }
        if (index in 0 until modules.size - 1) {
            val item = modules.removeAt(index)
            modules.add(index + 1, item)
            _activeSpace.value = space.copy(modules = modules)
        }
    }

    fun saveModulesArrangement() {
        showToast("تم حفظ وتطبيق التنسيق للوحدات بنجاح!")
    }

    fun openCreateSpaceModal() {
        _isCreateSpaceModalOpen.value = true
    }

    fun closeCreateSpaceModal() {
        _isCreateSpaceModalOpen.value = false
    }

    fun createSpace(title: String, description: String) {
        if (title.isBlank()) return
        val newSpace = CustomSpace(
            id = "space_${System.currentTimeMillis()}",
            title = title,
            description = description.ifBlank { "مساحة مخصصة جديدة تجمع مصادرك المختارة." },
            isActive = false,
            subtitleTag = "مساحة خاصة جديدة",
            platformChips = listOf("مصادر مخصصة"),
            modules = emptyList()
        )
        _savedSpaces.update { it + newSpace }
        closeCreateSpaceModal()
        showToast("تم إنشاء المساحة «$title» بنجاح")
    }

    fun setActivityTab(tab: ActivityTab) {
        _activityTab.value = tab
    }

    fun setActivityPlatformFilter(platform: PlatformType) {
        _activityPlatformFilter.value = platform
    }

    fun markAllActivityRead() {
        _activityItems.update { list ->
            list.map { it.copy(isUnread = false) }
        }
        showToast("تم تحديد جميع الإشعارات كمقروءة")
    }

    fun openSearch() {
        _isSearchVisible.value = true
    }

    fun closeSearch() {
        _isSearchVisible.value = false
        _searchQuery.value = ""
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun clearSearchQuery() {
        _searchQuery.value = ""
    }

    fun updatePreferences(transform: (AppPreferences) -> AppPreferences) {
        _preferences.update(transform)
    }

    fun clearMediaCache() {
        _preferences.update { it.copy(cacheSizeMb = 0) }
        showToast("تم تفريغ 142 ميغابايت من الذاكرة المؤقتة بنجاح")
    }

    fun clearSearchHistory() {
        showToast("تم مسح سجل البحث والاستكشاف بالكامل")
    }

    fun resetPreferencesToDefault() {
        _preferences.value = AppPreferences()
        showToast("تمت استعادة الإعدادات المعمارية الافتراضية للمنصة")
    }

    fun showToast(msg: String) {
        _toastMessage.value = msg
    }

    fun dismissToast() {
        _toastMessage.value = null
    }
}
