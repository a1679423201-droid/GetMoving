# AGENTS.md

## 项目

GetMoving：Kotlin + Jetpack Compose 的 Android 应用，单模块 `:app`，目前处于初始化阶段（模板页面）。

- applicationId / namespace：`com.example.getmoving`（占位，后续确认）
- 入口：`app/src/main/java/com/example/getmoving/MainActivity.kt`
- 主题：`app/src/main/java/com/example/getmoving/ui/theme/`

## 构建与验证

```bash
./gradlew assembleDebug
./gradlew test
```

Gradle wrapper 9.6.0，守护进程 JVM 固定为 JDK 25（`gradle/gradle-daemon-jvm.properties`），本机实际取用 Android Studio 自带的 JBR 25，缺失时 Gradle 会自动下载。改动后以 `assembleDebug` 通过为编译验收标准。

## 约定

- 依赖版本统一改 `gradle/libs.versions.toml`，模块里不写死版本号。
- UI 使用 Compose + Material3，不引入 XML 布局。
- 不要提交 `local.properties`、`.gradle/`、`build/`、`.idea/workspace.xml`。
- 新增依赖需说明用途；优先用 Compose BOM 管理的组件。
- 改包名时同步修改 `namespace`、`applicationId`、源码目录和 `AndroidManifest.xml`。

## 笔记

需求梳理、方案对比、踩坑记录、AI 提示词写到 `/Users/a11/ymd/MD/Vibe/GetMoving`，不要塞进代码库。代码库内只保留 README 这类与代码直接相关、需要跟版本走的文档。
