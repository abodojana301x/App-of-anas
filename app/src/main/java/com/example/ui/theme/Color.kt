package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Apple iOS Dark Mode Architecture Surface Levels
val CanvasDark = Color(0xFF000000)        // Pure OLED Black
val CanvasAmoled = Color(0xFF000000)      // Pure OLED Black
val CanvasLight = Color(0xFFF2F2F7)       // iOS System Grouped Background Light

// Apple System Gray Hierarchy (Dark Mode)
val AppleSystemGray = Color(0xFF8E8E93)
val AppleSystemGray2 = Color(0xFF636366)
val AppleSystemGray3 = Color(0xFF48484A)
val AppleSystemGray4 = Color(0xFF3A3A3C)
val AppleSystemGray5 = Color(0xFF2C2C2E)
val AppleSystemGray6 = Color(0xFF1C1C1E)

// Surface Container mappings mapped to iOS Grays
val SurfaceContainerLowest = Color(0xFF0A0A0C)
val SurfaceContainerLow = AppleSystemGray6
val SurfaceContainer = Color(0xFF242426)
val SurfaceContainerHigh = AppleSystemGray5
val SurfaceContainerHighest = AppleSystemGray4
val SurfaceBright = AppleSystemGray3

// Apple iOS Translucent Glassmorphism System (~55% - 70% semi-transparency with frosted sheen)
val GlassSurfaceDark = Color(0x9912141A)       // ~60% dark frosted translucent glass
val GlassSurfaceElevated = Color(0xB3181B24)   // ~70% elevated frosted glass
val GlassSurfacePill = Color(0x662C303E)       // ~40% pill/segmented control glass
val GlassSurfaceSubtle = Color(0x331C1E28)     // ~20% sheer subtle glass
val GlassTopBarBackdrop = Color(0x850A0B0E)    // ~52% translucent top bar glass
val GlassBottomBarBackdrop = Color(0x9E101218) // ~62% translucent bottom nav glass
val GlassBorder = Color(0x3DFFFFFF)            // 24% crisp specular white rim
val GlassBorderSubtle = Color(0x1FFFFFFF)      // 12% delicate glass outline
val GlassHighlight = Color(0x52FFFFFF)         // 32% specular top reflection glow

// iOS Typography and Content Colors
val OnSurfaceDark = Color(0xFFFFFFFF)          // Primary Label: White
val OnSurfaceVariantDark = Color(0xFFEBEBF5).copy(alpha = 0.70f) // Secondary Label: ~70%
val OutlineDark = Color(0xFF8E8E93)            // Tertiary Label: Gray
val OutlineVariantDark = Color(0xFF3A3A3C)     // Separators: System Gray 4

// Apple System Accents
val AppleSystemBlue = Color(0xFF0A84FF)
val AppleSystemGreen = Color(0xFF30D158)
val AppleSystemIndigo = Color(0xFF5E5CE6)
val AppleSystemOrange = Color(0xFFFF9F0A)
val AppleSystemPink = Color(0xFFFF375F)
val AppleSystemPurple = Color(0xFFBF5AF2)
val AppleSystemRed = Color(0xFFFF453A)
val AppleSystemTeal = Color(0xFF64D2FF)

// Primary Accent Palette (defaults to Apple Blue / Electric Violet)
val PrimaryViolet = AppleSystemBlue
val PrimaryContainerViolet = Color(0xFF0A84FF)
val OnPrimaryViolet = Color(0xFFFFFFFF)
val OnPrimaryContainerViolet = Color(0xFFFFFFFF)

// Alternative Accent Glows
val AccentViolet = AppleSystemPurple
val AccentIndigo = AppleSystemIndigo
val AccentEmerald = AppleSystemGreen
val AccentAmber = AppleSystemOrange

// Social Platform Accents (Refined & Clean)
val ColorPlatformX = Color(0xFFF5F5F7)
val ColorPlatformInstagram = Color(0xFFFA7E1E)
val ColorPlatformYouTube = Color(0xFFFF453A)
val ColorPlatformTikTok = Color(0xFF00F2FE)
val ColorPlatformFacebook = Color(0xFF1877F2)
val ColorPlatformLinkedIn = Color(0xFF0A66C2)
val ColorPlatformThreads = Color(0xFFFFFFFF)

// Status and Alerts
val StatusSuccess = AppleSystemGreen
val StatusWarning = AppleSystemOrange
val StatusError = AppleSystemRed
val StatusErrorContainer = Color(0xFF5C0006)
