# GetMoving

Android 应用，Kotlin + Jetpack Compose。当前是 Android Studio 生成的项目模板，业务功能待开发。

## 环境

| 项目 | 版本 |
| --- | --- |
| Gradle | 9.6.0（wrapper） |
| Android Gradle Plugin | 9.4.1 |
| Kotlin | 2.2.10 |
| Compose BOM | 2026.02.01 |
| compileSdk / targetSdk | 37 |
| minSdk | 31 |
| JDK | 25（由 `gradle/gradle-daemon-jvm.properties` 指定，Gradle 自动解析，缺失时下载） |

Android SDK 路径在 `local.properties`，该文件不入库。

## 常用命令

```bash
./gradlew assembleDebug    # 构建 debug APK
./gradlew installDebug     # 安装到已连接的设备
./gradlew test             # 单元测试
./gradlew connectedAndroidTest  # 仪器测试
./gradlew lint             # 静态检查
```

产物：`app/build/outputs/apk/debug/app-debug.apk`

## 目录

- `app/src/main/java/com/example/getmoving/` — 应用代码，入口 `MainActivity.kt`
- `app/src/main/java/com/example/getmoving/ui/theme/` — Compose 主题
- `app/src/main/res/` — 资源
- `gradle/libs.versions.toml` — 依赖版本集中管理

## 关联笔记

需求、设计、决策记录和 AI 提示词放在 `/Users/a11/ymd/MD/Vibe/GetMoving`（Obsidian 库）。代码库只放与代码直接相关的文档。

## 待办

- [ ] 确认应用定位与包名（当前为模板占位 `com.example.getmoving`）
- [ ] 替换模板 `Greeting` 页面，接入首个功能
- [ ] 应用图标与主题色
