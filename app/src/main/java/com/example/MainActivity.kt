package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.NavTab
import com.example.ui.components.SocialHubBottomNav
import com.example.ui.components.SocialHubTopBar
import com.example.ui.components.ToastNotificationBanner
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.viewmodel.SocialHubViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SocialHubViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val preferences by viewModel.preferences.collectAsStateWithLifecycle()
            val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
            val selectedPlatform by viewModel.selectedPlatform.collectAsStateWithLifecycle()
            val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
            val feedItems by viewModel.feedItems.collectAsStateWithLifecycle()
            val connectedPlatforms by viewModel.connectedPlatforms.collectAsStateWithLifecycle()
            val activeSpace by viewModel.activeSpace.collectAsStateWithLifecycle()
            val savedSpaces by viewModel.savedSpaces.collectAsStateWithLifecycle()
            val activityTab by viewModel.activityTab.collectAsStateWithLifecycle()
            val activityFilter by viewModel.activityPlatformFilter.collectAsStateWithLifecycle()
            val activityItems by viewModel.activityItems.collectAsStateWithLifecycle()
            val isSearchVisible by viewModel.isSearchVisible.collectAsStateWithLifecycle()
            val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
            val isCreateSpaceModalOpen by viewModel.isCreateSpaceModalOpen.collectAsStateWithLifecycle()
            val toastMessage by viewModel.toastMessage.collectAsStateWithLifecycle()
            val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

            val accentColor = when (preferences.accentColorName) {
                "indigo" -> AccentIndigo
                "emerald" -> AccentEmerald
                "amber" -> AccentAmber
                else -> PrimaryContainerViolet
            }

            // Always provide authentic RTL layout for Arabic
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                MyApplicationTheme(
                    themeMode = preferences.themeMode,
                    customAccent = accentColor
                ) {
                    // Back handler: pop search or return to home
                    BackHandler(enabled = isSearchVisible || currentTab != NavTab.HOME) {
                        if (isSearchVisible) {
                            viewModel.closeSearch()
                        } else {
                            viewModel.setTab(NavTab.HOME)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        // Ambient luminous orbs for iOS-style frosted glass effect
                        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                            // Top-right soft violet aura
                            drawCircle(
                                brush = androidx.compose.ui.graphics.Brush.radialGradient(
                                    colors = listOf(PrimaryContainerViolet.copy(alpha = 0.22f), Color.Transparent),
                                    center = androidx.compose.ui.geometry.Offset(size.width * 0.85f, size.height * 0.12f),
                                    radius = size.width * 0.75f
                                )
                            )
                            // Mid-left cyan-neon glow
                            drawCircle(
                                brush = androidx.compose.ui.graphics.Brush.radialGradient(
                                    colors = listOf(ColorPlatformTikTok.copy(alpha = 0.14f), Color.Transparent),
                                    center = androidx.compose.ui.geometry.Offset(size.width * 0.10f, size.height * 0.45f),
                                    radius = size.width * 0.65f
                                )
                            )
                            // Center-right subtle warm rose luminescence
                            drawCircle(
                                brush = androidx.compose.ui.graphics.Brush.radialGradient(
                                    colors = listOf(ColorPlatformInstagram.copy(alpha = 0.08f), Color.Transparent),
                                    center = androidx.compose.ui.geometry.Offset(size.width * 0.90f, size.height * 0.60f),
                                    radius = size.width * 0.55f
                                )
                            )
                            // Bottom-center electric indigo glow
                            drawCircle(
                                brush = androidx.compose.ui.graphics.Brush.radialGradient(
                                    colors = listOf(AccentIndigo.copy(alpha = 0.16f), Color.Transparent),
                                    center = androidx.compose.ui.geometry.Offset(size.width * 0.50f, size.height * 0.92f),
                                    radius = size.width * 0.70f
                                )
                            )
                        }

                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            containerColor = Color.Transparent,
                            topBar = {
                                if (!isSearchVisible) {
                                    SocialHubTopBar(
                                        unreadCount = activityItems.count { it.isUnread },
                                        onOpenSearch = { viewModel.openSearch() },
                                        onOpenNotifications = { viewModel.setTab(NavTab.ACTIVITY) }
                                    )
                                }
                            },
                            bottomBar = {
                                if (!isSearchVisible) {
                                    SocialHubBottomNav(
                                        currentTab = currentTab,
                                        onTabSelected = { viewModel.setTab(it) }
                                    )
                                }
                            }
                        ) { innerPadding ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                            AnimatedContent(
                                targetState = Pair(currentTab, isSearchVisible),
                                transitionSpec = {
                                    fadeIn() togetherWith fadeOut()
                                },
                                label = "screen_transition"
                            ) { (tab, searchOpen) ->
                                if (searchOpen) {
                                    SearchScreen(
                                        searchQuery = searchQuery,
                                        onQueryChange = { viewModel.updateSearchQuery(it) },
                                        onClearQuery = { viewModel.clearSearchQuery() },
                                        onCloseSearch = { viewModel.closeSearch() }
                                    )
                                } else {
                                    when (tab) {
                                        NavTab.HOME -> HomeScreen(
                                            feedItems = feedItems,
                                            selectedPlatform = selectedPlatform,
                                            selectedCategory = selectedCategory,
                                            onSelectPlatform = { viewModel.setPlatform(it) },
                                            onSelectCategory = { viewModel.setCategory(it) },
                                            onToggleLike = { viewModel.toggleLike(it) },
                                            onToggleSave = { viewModel.toggleSave(it) },
                                            onNextSlide = { viewModel.nextSlide(it) },
                                            onPrevSlide = { viewModel.prevSlide(it) },
                                            onRefreshFeed = { viewModel.refreshAllSync() }
                                        )
                                        NavTab.PLATFORMS -> PlatformsScreen(
                                            platforms = connectedPlatforms,
                                            onTogglePlatform = { viewModel.togglePlatformSync(it) },
                                            onRefreshSync = { viewModel.refreshAllSync() },
                                            isRefreshing = isRefreshing,
                                            onManagePlatform = { name -> viewModel.showToast("فتح إعدادات الربط المتقدم لمنصة $name") }
                                        )
                                        NavTab.SPACES -> SpacesScreen(
                                            activeSpace = activeSpace,
                                            savedSpaces = savedSpaces,
                                            onSwitchSpace = { viewModel.switchActiveSpace(it) },
                                            onMoveModuleUp = { viewModel.moveModuleUp(it) },
                                            onMoveModuleDown = { viewModel.moveModuleDown(it) },
                                            onSaveArrangement = { viewModel.saveModulesArrangement() },
                                            onOpenCreateSpaceModal = { viewModel.openCreateSpaceModal() }
                                        )
                                        NavTab.ACTIVITY -> ActivityScreen(
                                            items = activityItems,
                                            currentTab = activityTab,
                                            selectedFilter = activityFilter,
                                            onTabSelected = { viewModel.setActivityTab(it) },
                                            onFilterSelected = { viewModel.setActivityPlatformFilter(it) },
                                            onMarkAllRead = { viewModel.markAllActivityRead() },
                                            onItemAction = { label -> viewModel.showToast("تم تنفيذ الإجراء: $label") }
                                        )
                                        NavTab.SETTINGS -> SettingsScreen(
                                            preferences = preferences,
                                            onUpdatePreferences = { viewModel.updatePreferences(it) },
                                            onClearCache = { viewModel.clearMediaCache() },
                                            onClearSearchHistory = { viewModel.clearSearchHistory() },
                                            onResetDefaults = { viewModel.resetPreferencesToDefault() },
                                            onSavePreferences = { viewModel.showToast("تم حفظ وتطبيق التفضيلات بنجاح") }
                                        )
                                    }
                                }
                            }

                            // Toast notification overlay
                            ToastNotificationBanner(
                                message = toastMessage,
                                onDismiss = { viewModel.dismissToast() },
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 12.dp)
                            )

                            // Create Space Dialog
                            CreateSpaceBottomSheet(
                                isOpen = isCreateSpaceModalOpen,
                                onDismiss = { viewModel.closeCreateSpaceModal() },
                                onCreate = { title, desc -> viewModel.createSpace(title, desc) }
                            )
                        }
                    }
                }
            }
        }
    }
    }
}
