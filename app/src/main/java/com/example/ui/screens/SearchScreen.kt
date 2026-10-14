package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import com.example.ui.components.GlassCard
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.PlatformType
import com.example.ui.theme.*

@Composable
fun SearchScreen(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    onCloseSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedPlatform by remember { mutableStateOf(PlatformType.ALL) }
    var selectedTab by remember { mutableStateOf(0) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .testTag("unified_search_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Back button & Radar Title
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onCloseSearch,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "رجوع",
                        tint = OnSurfaceDark
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = SurfaceContainerHigh,
                        shape = CircleShape
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryContainerViolet)
                            )
                            Text(
                                text = "رادار المحتوى الشامل",
                                style = MaterialTheme.typography.labelSmall,
                                color = PrimaryContainerViolet
                            )
                        }
                    }

                    Surface(
                        color = SurfaceContainerLow,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "٥ منصات متصلة",
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariantDark,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "البحث والاستكشاف الموحد",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceDark
                )
                Text(
                    text = "تصفية ذكية ومتزامنة للمحتوى عبر X، إنستغرام، يوتيوب، تيك توك وفيسبوك في تدفق واحد مجمّع.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnSurfaceVariantDark,
                    lineHeight = 22.sp
                )
            }
        }

        // 2. Search Input Box
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onQueryChange,
                placeholder = {
                    Text(
                        text = "ابحث عن حسابات، وسوم، مقاطع، أو مواضيع...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OutlineDark
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "بحث",
                        tint = OnSurfaceVariantDark
                    )
                },
                trailingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(end = 6.dp)
                    ) {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = onClearQuery, modifier = Modifier.size(32.dp)) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "مسح", tint = OnSurfaceVariantDark, modifier = Modifier.size(18.dp))
                            }
                        }
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainerHigh),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Tune, contentDescription = "تصفية", tint = PrimaryContainerViolet, modifier = Modifier.size(18.dp))
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryContainerViolet,
                    unfocusedBorderColor = OutlineVariantDark,
                    focusedTextColor = OnSurfaceDark,
                    unfocusedTextColor = OnSurfaceDark,
                    focusedContainerColor = SurfaceContainerLowest,
                    unfocusedContainerColor = SurfaceContainerLowest
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("unified_search_input_field")
            )
        }

        // 3. Platform Target Selector Pills
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PlatformSearchPill(
                    label = "كل المنصات (5)",
                    isSelected = selectedPlatform == PlatformType.ALL,
                    onClick = { selectedPlatform = PlatformType.ALL }
                )
                PlatformSearchPill(
                    label = "منصة X",
                    isSelected = selectedPlatform == PlatformType.X,
                    onClick = { selectedPlatform = PlatformType.X }
                )
                PlatformSearchPill(
                    label = "Instagram",
                    isSelected = selectedPlatform == PlatformType.INSTAGRAM,
                    onClick = { selectedPlatform = PlatformType.INSTAGRAM }
                )
                PlatformSearchPill(
                    label = "YouTube",
                    isSelected = selectedPlatform == PlatformType.YOUTUBE,
                    onClick = { selectedPlatform = PlatformType.YOUTUBE }
                )
                PlatformSearchPill(
                    label = "TikTok",
                    isSelected = selectedPlatform == PlatformType.TIKTOK,
                    onClick = { selectedPlatform = PlatformType.TIKTOK }
                )
                PlatformSearchPill(
                    label = "Facebook",
                    isSelected = selectedPlatform == PlatformType.FACEBOOK,
                    onClick = { selectedPlatform = PlatformType.FACEBOOK }
                )
            }
        }

        // 4. Result Classification Tabs
        item {
            Surface(
                color = SurfaceContainerLow,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    val tabs = listOf("أفضل النتائج", "حسابات", "وسائط ومرئيات", "منشورات", "الوسوم")
                    tabs.forEachIndexed { index, title ->
                        val isSelected = selectedTab == index
                        Surface(
                            color = if (isSelected) SurfaceContainerHigh else Color.Transparent,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedTab = index }
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) OnSurfaceDark else OnSurfaceVariantDark,
                                modifier = Modifier
                                    .padding(vertical = 6.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }

        // 5. Recent Queries & Sparks
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.History, contentDescription = null, tint = PrimaryContainerViolet, modifier = Modifier.size(18.dp))
                            Text(text = "عمليات البحث السريعة والمتداولة", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Medium, color = OnSurfaceDark)
                        }
                        Text(
                            text = "مسح السجل",
                            style = MaterialTheme.typography.labelSmall,
                            color = OutlineDark,
                            modifier = Modifier.clickable { }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            "#الذكاء_الاصطناعي",
                            "مقارنة نماذج اللغة الحوسبية",
                            "#تصميم_الواجهات",
                            "استوديو عتاد آبل M4"
                        ).forEach { tag ->
                            Surface(
                                color = SurfaceContainer,
                                shape = CircleShape,
                                modifier = Modifier.clickable { onQueryChange(tag.replace("#", "")) }
                            ) {
                                Text(
                                    text = tag,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = OnSurfaceDark,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 6. Result Metrics Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "تم العثور على ١٤٢ نتيجة متطابقة",
                    style = MaterialTheme.typography.labelSmall,
                    color = OnSurfaceVariantDark
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "الترتيب: الأكثر صلة", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                    Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null, tint = OnSurfaceVariantDark, modifier = Modifier.size(16.dp))
                }
            }
        }

        // Card 1: Verified Creator Account
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            AsyncImage(
                                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBd7Zu5tzYXQpjs-xr1bfa-PlHbvq6olY6UV8mLlL8fcpEMBLVQjdp7D1A1BVbOr5eFY5APBdmBL6SZHBFwYbKEQUVWkNm_vAnrvaadjVfxi-ZT4FzNXgKhKtgJhD7GhwNPoSnXSdoxjWbQNn5DXdhhNkL0ELj_BaPGzaGyj0gIHnFT5l8dXvPwY7Ju552PGvFAqoHVTv3T6NZePfdCN9LPjYSdubEGxdVA5A8P8mK4TW9yN6TQJgg",
                                contentDescription = "د. طارق المنصور",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                            )
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(text = "د. طارق المنصور", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = OnSurfaceDark)
                                    Icon(imageVector = Icons.Filled.Verified, contentDescription = null, tint = PrimaryContainerViolet, modifier = Modifier.size(16.dp))
                                }
                                Text(text = "@almansoor_tech", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                            }
                        }

                        Surface(color = SurfaceContainerHighest, shape = CircleShape) {
                            Text(text = "𝕏 148K", style = MaterialTheme.typography.labelSmall, color = OnSurfaceDark, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                        }
                    }

                    Text(
                        text = "مستشار في أتمتة الأنظمة وتجربة المنتجات الرقمية. أقود مبادرات توطين تقنيات الذكاء التوليدي في بيئات العمل الإبداعية العربية.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSurfaceDark,
                        lineHeight = 22.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "١٫٤ ألف منشور  •  نشط منذ ٤٥ دقيقة", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Button(
                                onClick = { },
                                colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHigh, contentColor = OnSurfaceDark),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(text = "حفظ", style = MaterialTheme.typography.labelSmall)
                            }
                            Button(
                                onClick = { },
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainerViolet, contentColor = OnPrimaryContainerViolet),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(text = "متابعة موحدة", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }

        // Card 2: YouTube Analysis Video
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(SurfaceContainerHighest)
                    ) {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuB2QepRUsDQHyvEFC3UBfZn7F7eXahvdmROdREmOmHnafM5dUllOiXJHeUn1-8S60LnC2eFOsxTFSGTZS2RQzbp_KsywF8W1c1ZIkX-ZfhcH7zEYGjupItBkb0PYpbaUKZhFTuvDAUfYFs-M-fTTDYgyWO9Lqe0Y4RKID2aWnSceRxIf2ZqsIoUo8TU09tc2XHensjHo8aUfVA1sjcTjumoA0GMw12lcNfDEJfLFnD3V_i94kodlws",
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        Surface(
                            color = CanvasDark.copy(alpha = 0.8f),
                            shape = CircleShape,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        ) {
                            Text(text = "YouTube", style = MaterialTheme.typography.labelSmall, color = ColorPlatformYouTube, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                        }

                        Surface(
                            color = CanvasDark.copy(alpha = 0.85f),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(8.dp)
                        ) {
                            Text(text = "14:28", style = MaterialTheme.typography.labelSmall, color = OnSurfaceDark, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }

                    Text(
                        text = "كيف تعيد واجهات الذكاء الاصطناعي تشكيل تجربة المستخدم في عام 2025؟ دراسة أداء وحلول عملية",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = OnSurfaceDark,
                        lineHeight = 24.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "قناة تجارب التقنية  •  ٥٤ ألف مشاهدة", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                        Text(text = "فتح في YouTube ↗", style = MaterialTheme.typography.labelSmall, color = PrimaryContainerViolet)
                    }
                }
            }
        }

        // Card 3: Visual Inspiration (Instagram Carousel Showcase)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AsyncImage(
                                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuDx-N0mpYqmUwVpwfbMjkI3croOnKQhjH66Vh2VHqnvavsDw0ZYUyc7na9qABem3ZLQrJT_URD9bA9kBpB8SHflCzRtJJjesNHQG_L9PrpWdXIh_Ui9jqZo00LRWrv9FamBaNNajdFE-Wn8_rAUrye3CcFwePxwPazoS-b8KRAs0ilfAxO6JCFU88qGbD0B7srrXZlvxlBUg15Rc2ToUycASIMZwAAw9PtSy3Hw2dar8IigLz_IarI",
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                            )
                            Column {
                                Text(text = "استوديو نوى للتصميم", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = OnSurfaceDark)
                                Text(text = "@nawa.designs", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                            }
                        }

                        Surface(color = SurfaceContainerHighest, shape = CircleShape) {
                            Text(text = "Instagram", style = MaterialTheme.typography.labelSmall, color = ColorPlatformInstagram, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                        }
                    }

                    Text(
                        text = "نماذج مبدئية لنظام تصميم داكن (Dark System) يركز على تباين الخطوط العربية وأريحية العين أثناء جلسات التحليل الممتدة ✨🖤",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSurfaceDark,
                        lineHeight = 22.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(140.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(SurfaceContainerHighest)
                        ) {
                            AsyncImage(
                                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuDMztJhDH0M2kZ4NQI3c4rRkvoS_nBM60xOE9sZnT3JDmp5XWQztO8yWuamXhtL6oIhsyntsGhF5wpAXjS9q4xF44JaEd8CWiVWcKqyHL2xKmLz2pJgBoEAqDMaDske8abTVU6Ekaa6NllCEldoUD2dm5g2HwtDB101IIhCx3DyCzys7dPPZkBrxW9XXiuke7PiZiyA7S-K9vhGdsRAYzCu9B_BVHRjzeri148eUnzQ37Ja5r72bNk",
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(140.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(SurfaceContainerHighest)
                        ) {
                            AsyncImage(
                                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuDOCbf2nSpLHCtoicDkyBuOzsGzAg48Cl-ydKDwaz0T2L19yFRp409Mqu5SWXrlGLyJ06s0Eyp4SZhsdAMTNqWghw0Cxfu9Zq9BsO5r9wj5R7roO2irg43fMjwQuVvLp0jTfVVdeMRtDpOmsVfD5_nyVgfuDdUNdiLFvJEf2WDymFfLgHFVoxaLP3oBqhKvdVf6MZc0jun867J96bm-FZv7P2XR5oqMNul_No47_5127TgbQHuooyc",
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(CanvasDark.copy(alpha = 0.5f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "+٣ صور", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text(text = "❤️ 3,892", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                            Text(text = "💬 214", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                        }
                        Text(text = "توسيع المعاينة ⛶", style = MaterialTheme.typography.labelSmall, color = PrimaryContainerViolet)
                    }
                }
            }
        }
    }
}

@Composable
fun PlatformSearchPill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        color = if (isSelected) PrimaryContainerViolet else SurfaceContainer,
        shape = CircleShape,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) OnPrimaryContainerViolet else OnSurfaceDark,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
        )
    }
}
