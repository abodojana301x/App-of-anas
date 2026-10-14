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
import com.example.ui.components.LocalBlurIntensity
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
                "indigo" -> AppleSystemIndigo
                "emerald" -> AppleSystemGreen
                "amber" -> AppleSystemOrange
                "purple" -> AppleSystemPurple
                else -> AppleSystemBlue
            }

            // Always provide authentic RTL layout for Arabic & propagate blur mode
            CompositionLocalProvider(
                LocalLayoutDirection provides LayoutDirection.Rtl,
                LocalBlurIntensity provides preferences.blurIntensity
            ) {
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
                        // 1. Ambient luminous orbs for Apple OLED frosted glass depth
                        // Automatically scaled or disabled for low-spec devices based on user's blur preference
                        if (preferences.blurIntensity != "off") {
                            val auraMultiplier = if (preferences.blurIntensity == "low") 0.45f else 1.0f
                            androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                                // Top-right soft Apple Blue aura
                                drawCircle(
                                    brush = androidx.compose.ui.graphics.Brush.radialGradient(
                                        colors = listOf(accentColor.copy(alpha = 0.18f * auraMultiplier), Color.Transparent),
                                        center = androidx.compose.ui.geometry.Offset(size.width * 0.85f, size.height * 0.10f),
                                        radius = size.width * 0.75f
                                    )
                                )
                                // Mid-left subtle Apple Indigo luminescence
                                drawCircle(
                                    brush = androidx.compose.ui.graphics.Brush.radialGradient(
                                        colors = listOf(AppleSystemIndigo.copy(alpha = 0.12f * auraMultiplier), Color.Transparent),
                                        center = androidx.compose.ui.geometry.Offset(size.width * 0.10f, size.height * 0.45f),
                                        radius = size.width * 0.65f
                                    )
                                )
                                // Bottom-center electric glow
                                drawCircle(
                                    brush = androidx.compose.ui.graphics.Brush.radialGradient(
                                        colors = listOf(AppleSystemPurple.copy(alpha = 0.10f * auraMultiplier), Color.Transparent),
                                        center = androidx.compose.ui.geometry.Offset(size.width * 0.50f, size.height * 0.90f),
                                        radius = size.width * 0.70f
                                    )
                                )
                            }
                        }

                        // 2. Full-bleed Content Layer (scrolls smoothly behind frosted glass bars)
                        AnimatedContent(
                            targetState = Pair(currentTab, isSearchVisible),
                            transitionSpec = {
                                fadeIn() togetherWith fadeOut()
                            },
                            label = "screen_transition",
                            modifier = Modifier.fillMaxSize()
                        ) { (tab, searchOpen) ->
                            if (searchOpen) {
                                SearchScreen(
                                    searchQuery = searchQuery,
                                    onQueryChange = { viewModel.updateSearchQuery(it) },
                                    onClearQuery = { viewModel.clearSearchQuery() },
                                    onCloseSearch = { viewModel.closeSearch() },
                                    modifier = Modifier.fillMaxSize()
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
                                        onRefreshFeed = { viewModel.refreshAllSync() },
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    NavTab.PLATFORMS -> PlatformsScreen(
                                        platforms = connectedPlatforms,
                                        onTogglePlatform = { viewModel.togglePlatformSync(it) },
                                        onRefreshSync = { viewModel.refreshAllSync() },
                                        isRefreshing = isRefreshing,
                                        onManagePlatform = { name -> viewModel.showToast("فتح إعدادات الربط المتقدم لمنصة $name") },
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    NavTab.SPACES -> SpacesScreen(
                                        activeSpace = activeSpace,
                                        savedSpaces = savedSpaces,
                                        onSwitchSpace = { viewModel.switchActiveSpace(it) },
                                        onMoveModuleUp = { viewModel.moveModuleUp(it) },
                                        onMoveModuleDown = { viewModel.moveModuleDown(it) },
                                        onSaveArrangement = { viewModel.saveModulesArrangement() },
                                        onOpenCreateSpaceModal = { viewModel.openCreateSpaceModal() },
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    NavTab.ACTIVITY -> ActivityScreen(
                                        items = activityItems,
                                        currentTab = activityTab,
                                        selectedFilter = activityFilter,
                                        onTabSelected = { viewModel.setActivityTab(it) },
                                        onFilterSelected = { viewModel.setActivityPlatformFilter(it) },
                                        onMarkAllRead = { viewModel.markAllActivityRead() },
                                        onItemAction = { label -> viewModel.showToast("تم تنفيذ الإجراء: $label") },
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    NavTab.SETTINGS -> SettingsScreen(
                                        preferences = preferences,
                                        onUpdatePreferences = { viewModel.updatePreferences(it) },
                                        onClearCache = { viewModel.clearMediaCache() },
                                        onClearSearchHistory = { viewModel.clearSearchHistory() },
                                        onResetDefaults = { viewModel.resetPreferencesToDefault() },
                                        onSavePreferences = { viewModel.showToast("تم حفظ وتطبيق التفضيلات بنجاح") },
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }

                        // 3. Translucent Blur TopBar Overlay (Sticky on top of scrolling content)
                        if (!isSearchVisible) {
                            SocialHubTopBar(
                                unreadCount = activityItems.count { it.isUnread },
                                blurIntensity = preferences.blurIntensity,
                                onOpenSearch = { viewModel.openSearch() },
                                onOpenNotifications = { viewModel.setTab(NavTab.ACTIVITY) },
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }

                        // 4. Floating Frosted Blur BottomNav Overlay (Sticky on bottom of scrolling content)
                        if (!isSearchVisible) {
                            SocialHubBottomNav(
                                currentTab = currentTab,
                                blurIntensity = preferences.blurIntensity,
                                onTabSelected = { viewModel.setTab(it) },
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }

                        // 5. Toast notification overlay (Dynamic Island top capsule)
                        ToastNotificationBanner(
                            message = toastMessage,
                            onDismiss = { viewModel.dismissToast() },
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 8.dp)
                        )

                        // 6. Create Space Dialog
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
