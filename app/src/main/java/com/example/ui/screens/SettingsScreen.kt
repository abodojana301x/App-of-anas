package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import com.example.ui.components.GlassCard
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppPreferences
import com.example.model.NavTab
import com.example.ui.theme.*

@Composable
fun SettingsScreen(
    preferences: AppPreferences,
    onUpdatePreferences: ((AppPreferences) -> AppPreferences) -> Unit,
    onClearCache: () -> Unit,
    onClearSearchHistory: () -> Unit,
    onResetDefaults: () -> Unit,
    onSavePreferences: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expandedPriorityDropdown by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("settings_screen_list"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Header Summary Banner
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Surface(
                                color = PrimaryContainerViolet.copy(alpha = 0.2f),
                                shape = CircleShape
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoFixHigh,
                                        contentDescription = null,
                                        tint = PrimaryContainerViolet,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "التحكم البصري المتقدم",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = PrimaryContainerViolet
                                    )
                                }
                            }

                            Text(
                                text = "التخصيص والتحكم",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = OnSurfaceDark
                            )

                            Text(
                                text = "قم بضبط وتشكيل تجربتك عبر المنصات لتلائم أسلوب عملك، من أطياف الإضاءة إلى كثافة القراءة والتغذية.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = OnSurfaceVariantDark,
                                lineHeight = 22.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(PrimaryContainerViolet.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Palette,
                                contentDescription = null,
                                tint = PrimaryContainerViolet,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }

                    // Active Profile Capsule
                    Surface(
                        color = SurfaceContainer.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(PrimaryContainerViolet)
                                )
                                Text(
                                    text = "النمط الحالي: داكن معماري • متسع",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = OnSurfaceDark
                                )
                            }
                            Surface(
                                color = SurfaceContainerHighest,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "مُخصص",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = OnSurfaceVariantDark,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. Group 1: Appearance & Visual Tone
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainerHighest),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Contrast, contentDescription = null, tint = PrimaryContainerViolet, modifier = Modifier.size(18.dp))
                        }
                        Column {
                            Text(text = "المظهر العام", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = OnSurfaceDark)
                            Text(text = "التحكم في مستويات الإضاءة، نغمة الألوان، وانحناء الأشكال", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                        }
                    }

                    // Theme Mode Selector
                    Text(text = "نمط العرض والسطوع", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Medium, color = OnSurfaceDark)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLowest)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        listOf(
                            Triple("dark", "داكن فاخر", Icons.Default.DarkMode),
                            Triple("amoled", "سواد عاتم", Icons.Default.Brightness1),
                            Triple("light", "نهاري هادئ", Icons.Default.LightMode)
                        ).forEach { (mode, label, icon) ->
                            val isSelected = preferences.themeMode == mode
                            Surface(
                                color = if (isSelected) SurfaceContainerHigh else Color.Transparent,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onUpdatePreferences { it.copy(themeMode = mode) } }
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = label,
                                        tint = if (isSelected) PrimaryContainerViolet else OnSurfaceVariantDark,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                                        color = if (isSelected) PrimaryContainerViolet else OnSurfaceVariantDark
                                    )
                                }
                            }
                        }
                    }

                    // Accent Color Selection
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "اللون المميز (Accent Glow)", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Medium, color = OnSurfaceDark)
                        Text(
                            text = when (preferences.accentColorName) {
                                "violet" -> "البنفسجي المعماري"
                                "indigo" -> "الأزرق النيلي"
                                "emerald" -> "الزمردي الهادئ"
                                else -> "العنبري الدافئ"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = PrimaryContainerViolet
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(SurfaceContainerLow)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        listOf(
                            Pair("violet", PrimaryContainerViolet),
                            Pair("indigo", AccentIndigo),
                            Pair("emerald", AccentEmerald),
                            Pair("amber", AccentAmber)
                        ).forEach { (colorName, colorValue) ->
                            val isSelected = preferences.accentColorName == colorName
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(colorValue)
                                    .clickable { onUpdatePreferences { it.copy(accentColorName = colorName) } }
                                    .then(if (isSelected) Modifier.border(2.dp, Color.White, CircleShape) else Modifier),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {
                                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = CanvasDark, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }

                    // Content Density & Card Corners Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Density
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(text = "كثافة المحتوى", style = MaterialTheme.typography.labelMedium, color = OnSurfaceDark)
                            Surface(
                                color = SurfaceContainerLow,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    listOf(
                                        Pair("spacious", "مريح (Spacious)"),
                                        Pair("standard", "قياسي (Normal)"),
                                        Pair("compact", "مضغوط (Compact)")
                                    ).forEach { (denseKey, label) ->
                                        val isSelected = preferences.density == denseKey
                                        Surface(
                                            color = if (isSelected) SurfaceContainerHigh else Color.Transparent,
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { onUpdatePreferences { it.copy(density = denseKey) } }
                                        ) {
                                            Text(
                                                text = label,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                                color = if (isSelected) PrimaryContainerViolet else OnSurfaceVariantDark,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 7.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Corners
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(text = "هندسة البطاقات", style = MaterialTheme.typography.labelMedium, color = OnSurfaceDark)
                            Surface(
                                color = SurfaceContainerLow,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    listOf(
                                        Pair("rounded", "منحنية (Rounded)"),
                                        Pair("soft", "فائقة الانحناء (Soft)"),
                                        Pair("sharp", "حادة (Sharp)")
                                    ).forEach { (cornerKey, label) ->
                                        val isSelected = preferences.cornerRadiusStyle == cornerKey
                                        Surface(
                                            color = if (isSelected) SurfaceContainerHigh else Color.Transparent,
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { onUpdatePreferences { it.copy(cornerRadiusStyle = cornerKey) } }
                                        ) {
                                            Text(
                                                text = label,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                                color = if (isSelected) PrimaryContainerViolet else OnSurfaceVariantDark,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 7.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 3. Group 2: Feed Customization & Flow
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainerHighest),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.DynamicFeed, contentDescription = null, tint = PrimaryContainerViolet, modifier = Modifier.size(18.dp))
                        }
                        Column {
                            Text(text = "خلاصة المنشورات والوسائط", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = OnSurfaceDark)
                            Text(text = "التحكم في خوارزمية الجمع، الاستعراض، والأسبقية", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                        }
                    }

                    // Toggles
                    SettingToggleRow(
                        icon = Icons.Default.Smartphone,
                        title = "عرض الفيديوهات العمودية",
                        subtitle = "دمج Shorts و Reels في الخلاصة تلقائياً",
                        checked = preferences.verticalVideosEnabled,
                        onCheckedChange = { chk -> onUpdatePreferences { it.copy(verticalVideosEnabled = chk) } }
                    )

                    SettingToggleRow(
                        icon = Icons.Default.VolumeOff,
                        title = "معاينة متحركة صامتة",
                        subtitle = "تشغيل المقاطع دون صوت عند التمرير السلس",
                        checked = preferences.silentPreviewEnabled,
                        onCheckedChange = { chk -> onUpdatePreferences { it.copy(silentPreviewEnabled = chk) } }
                    )

                    SettingToggleRow(
                        icon = Icons.Default.FilterAlt,
                        title = "إخفاء التكرار العابر للشبكات",
                        subtitle = "تجاهل المنشورات المشتركة بين منصات متعددة",
                        checked = preferences.hideDuplicates,
                        onCheckedChange = { chk -> onUpdatePreferences { it.copy(hideDuplicates = chk) } }
                    )

                    // Feed Priority Dropdown
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = "معيار فرز وترتيب الأسبقية", style = MaterialTheme.typography.labelMedium, color = OnSurfaceDark)
                        Box {
                            Surface(
                                color = SurfaceContainerLowest,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { expandedPriorityDropdown = !expandedPriorityDropdown }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = when (preferences.feedPriority) {
                                            "chronological" -> "الأحدث زمنياً (تدفق آني متسلسل)"
                                            "engagement" -> "الأعلى تفاعلاً ومشاركة (Top Viral)"
                                            "affinity" -> "المنصات المفضلة أولاً (حسب الأولويات)"
                                            else -> "المنشورات الغنية بالصور ومقاطع الفيديو أولاً"
                                        },
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = OnSurfaceDark
                                    )
                                    Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null, tint = OnSurfaceVariantDark)
                                }
                            }

                            DropdownMenu(
                                expanded = expandedPriorityDropdown,
                                onDismissRequest = { expandedPriorityDropdown = false },
                                modifier = Modifier.background(SurfaceContainerHigh)
                            ) {
                                listOf(
                                    Pair("chronological", "الأحدث زمنياً (تدفق آني متسلسل)"),
                                    Pair("engagement", "الأعلى تفاعلاً ومشاركة (Top Viral)"),
                                    Pair("affinity", "المنصات المفضلة أولاً (حسب الأولويات)"),
                                    Pair("media", "المنشورات الغنية بالصور ومقاطع الفيديو أولاً")
                                ).forEach { (key, label) ->
                                    DropdownMenuItem(
                                        text = { Text(label, color = OnSurfaceDark) },
                                        onClick = {
                                            onUpdatePreferences { it.copy(feedPriority = key) }
                                            expandedPriorityDropdown = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 4. Group 3: Layout & Navigation
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainerHighest),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Tab, contentDescription = null, tint = PrimaryContainerViolet, modifier = Modifier.size(18.dp))
                        }
                        Column {
                            Text(text = "المسارات والتنقل", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = OnSurfaceDark)
                            Text(text = "تخصيص الشاشة الافتراضية وهيكلة أقسام الشريط", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                        }
                    }

                    Text(text = "واجهة الإطلاق التلقائي", style = MaterialTheme.typography.labelMedium, color = OnSurfaceDark)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLow)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        listOf(
                            Triple(NavTab.HOME, "الرئيسية", Icons.Default.DynamicFeed),
                            Triple(NavTab.SPACES, "مساحتي", Icons.Default.DashboardCustomize),
                            Triple(NavTab.PLATFORMS, "المنصات", Icons.Default.Share)
                        ).forEach { (tab, label, icon) ->
                            val isSelected = preferences.startupScreen == tab
                            Surface(
                                color = if (isSelected) SurfaceContainerHigh else Color.Transparent,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onUpdatePreferences { it.copy(startupScreen = tab) } }
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(imageVector = icon, contentDescription = label, tint = if (isSelected) PrimaryContainerViolet else OnSurfaceVariantDark, modifier = Modifier.size(20.dp))
                                    Text(text = label, style = MaterialTheme.typography.labelSmall, color = if (isSelected) PrimaryContainerViolet else OnSurfaceVariantDark)
                                }
                            }
                        }
                    }
                }
            }
        }

        // 5. Group 4: Accounts, Storage & Privacy
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainerHighest),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = PrimaryContainerViolet, modifier = Modifier.size(18.dp))
                        }
                        Column {
                            Text(text = "الحسابات والبيانات المحلية", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = OnSurfaceDark)
                            Text(text = "سلامة التخزين المؤقت، سجلات البحث وأذونات الربط", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                        }
                    }

                    // Media cache
                    Surface(
                        color = SurfaceContainerLow,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Cached, contentDescription = null, tint = OnSurfaceVariantDark, modifier = Modifier.size(20.dp))
                                Column {
                                    Text(text = "الذاكرة المؤقتة للوسائط", style = MaterialTheme.typography.titleSmall, color = OnSurfaceDark)
                                    Text(text = "${preferences.cacheSizeMb} ميغابايت مستهلكة محلياً", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                                }
                            }
                            Button(
                                onClick = onClearCache,
                                colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHighest, contentColor = OnSurfaceDark),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(text = "تفريغ الآن", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }

                    // Search history
                    Surface(
                        color = SurfaceContainerLow,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(imageVector = Icons.Default.ManageSearch, contentDescription = null, tint = OnSurfaceVariantDark, modifier = Modifier.size(20.dp))
                                Column {
                                    Text(text = "سجل البحث والاستكشاف", style = MaterialTheme.typography.titleSmall, color = OnSurfaceDark)
                                    Text(text = "حذف الاستعلامات السابقة المحفوظة", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                                }
                            }
                            Button(
                                onClick = onClearSearchHistory,
                                colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHighest, contentColor = Color(0xFFEF4444)),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(text = "مسح السجل", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }

                    // OAuth tokens
                    Surface(
                        color = SurfaceContainerLow,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Key, contentDescription = null, tint = PrimaryContainerViolet, modifier = Modifier.size(20.dp))
                                Column {
                                    Text(text = "أذونات المنصات المربوطة (OAuth)", style = MaterialTheme.typography.titleSmall, color = OnSurfaceDark)
                                    Text(text = "4 شبكات متصلة بمفاتيح تشفير آمنة", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                                }
                            }
                            Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null, tint = OnSurfaceVariantDark)
                        }
                    }
                }
            }
        }

        // 6. Action Submission Buttons
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onSavePreferences,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryContainerViolet,
                        contentColor = OnPrimaryContainerViolet
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_save_preferences")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(imageVector = Icons.Default.DoneAll, contentDescription = null, modifier = Modifier.size(20.dp))
                        Text(text = "حفظ وتطبيق التفضيلات", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                    }
                }

                TextButton(
                    onClick = onResetDefaults,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(imageVector = Icons.Default.RestartAlt, contentDescription = null, tint = OnSurfaceVariantDark, modifier = Modifier.size(18.dp))
                        Text(text = "استعادة الضبط الافتراضي للمنصة", style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariantDark)
                    }
                }
            }
        }
    }
}

@Composable
fun SettingToggleRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        color = GlassSurfacePill,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(0.5.dp, GlassBorderSubtle),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = OnSurfaceVariantDark, modifier = Modifier.size(18.dp))
                }
                Column {
                    Text(text = title, style = MaterialTheme.typography.titleSmall, color = OnSurfaceDark)
                    Text(text = subtitle, style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                }
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = OnPrimaryContainerViolet,
                    checkedTrackColor = PrimaryContainerViolet,
                    uncheckedThumbColor = OnSurfaceVariantDark,
                    uncheckedTrackColor = SurfaceContainerHighest
                )
            )
        }
    }
}
