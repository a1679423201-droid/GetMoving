# AGENTS.md

## 项目

GetMoving：Kotlin + Jetpack Compose 的 Android 应用，单模块 `:app`，当前处于阶段 1 工程骨架，产品定位见笔记。

- applicationId / namespace：`com.example.getmoving`（占位，待定稿）
- 入口：`app/src/main/java/com/example/getmoving/MainActivity.kt`
- 架构：MVVM + UDF、Hilt、Room、DataStore、Navigation 3
- 主题：`app/src/main/java/com/example/getmoving/ui/theme/`

## 硬规则

- **未经用户明确要求，不要执行 `git commit`、`git push`、打 tag 或改写历史。** 只修改工作区并汇报，由用户决定何时提交。
- 不要提交 `local.properties`、签名密钥、`.gradle/`、`build/`、`.idea/`。
- 产品定位和功能范围以笔记里的 PRD 为准，不要自行假设目标设备、使用场景或业务需求。
- 不要把「已连接设备」默认当成目标设备，也不要把车机/平台签名等特定环境结论写进通用文档。

## 构建与验证

```bash
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
./gradlew assembleDebug
./gradlew test
./gradlew lint
```

Gradle wrapper 9.6.0，守护进程 JVM 固定为 JDK 25（`gradle/gradle-daemon-jvm.properties`）。本机必须把 `JAVA_HOME` 指向 Android Studio 的 JBR 25；否则 Gradle 会尝试从 foojay 下载 JDK 25，网络受限时会卡住。CI 使用 Temurin 25。

## 技术约定

- Kotlin 2.2.10 由 AGP 9 内置 Kotlin 提供，不要应用 `org.jetbrains.kotlin.android`。
- 依赖版本统一改 `gradle/libs.versions.toml`，模块里不写死版本号。
- UI 使用 Compose + Material3，不引入 XML 布局。
- 分层：`feature`（Screen/ViewModel/UiState）→ `domain`（纯 Kotlin 模型、接口、规则）→ `data`（Room/DataStore 实现）；UI 不直接依赖 Room/DataStore。
- 每个屏幕一个 ViewModel，暴露 `StateFlow<XxxUiState>`；用户操作用方法调用，不用事件总线。
- 新增依赖需说明用途；优先用 Compose BOM 管理的组件。
- 改包名时同步修改 `namespace`、`applicationId`、源码目录和 `AndroidManifest.xml`。
- 提交信息遵循 `<type>(<scope>): <描述>`，type 用 feat/fix/docs/refactor/test/chore/build。

## 文档

- 路线与门禁：`/Users/a11/ymd/MD/Vibe/GetMoving/路线与门禁.md`
- 架构、需求、UI、决策记录、AI 提示词：`/Users/a11/ymd/MD/Vibe/GetMoving`
- 非平凡技术决策（架构、选型、协议）要有决策记录，写在笔记里。
