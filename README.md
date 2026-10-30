# GetMoving

远程仓库：https://github.com/a1679423201-droid/GetMoving

Android 应用，Kotlin + Jetpack Compose。当前完成阶段 1 工程骨架：Hilt、Room、DataStore、Navigation 3、Material 3 主题和三 Tab 首页；FR-1 的基础「记录今天体重」已能工作。

## 环境

| 项目 | 版本 |
| --- | --- |
| Gradle | 9.6.0（wrapper） |
| Android Gradle Plugin | 9.4.1 |
| Kotlin | 2.2.10（AGP 9 内置 Kotlin，不单独应用 kotlin-android） |
| KSP | 2.3.12 |
| Compose BOM | 2026.02.01 |
| Hilt | 2.59.2（AndroidX Hilt 1.4.0） |
| Room | 2.8.5 |
| DataStore | 1.3.0 |
| Navigation 3 | 1.2.0 |
| kotlinx.serialization | 1.9.0 |
| compileSdk / targetSdk | 37 |
| minSdk | 30 |
| JDK | 25 |

## 构建

本机需要把 `JAVA_HOME` 指向 Android Studio 自带的 JBR 25，否则 Gradle 会尝试从 foojay 下载 JDK 25，网络受限时会长时间卡住：

```bash
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
./gradlew assembleDebug
./gradlew test
./gradlew lint
```

产物：`app/build/outputs/apk/debug/app-debug.apk`

CI 用 GitHub Actions + Temurin 25，见 `.github/workflows/ci.yml`。

## 目录

- `app/src/main/java/com/example/getmoving/`
  - `di/` — Hilt 模块（数据库、DataStore、Clock、Repository 绑定）
  - `data/` — Room 实体/DAO/数据库、DataStore、Repository 实现
  - `domain/` — 纯 Kotlin 模型、仓库接口、校验与目标差值规则
  - `feature/` — 按功能划分的 Compose 页面与 ViewModel
  - `navigation/` — Navigation 3 的 NavKey、NavDisplay、底部导航
  - `ui/` — 主题、通用组件、格式化
- `app/schemas/` — Room schema 导出（迁移用）
- `gradle/libs.versions.toml` — 依赖版本集中管理

## 文档

- 路线与质量门禁：`/Users/a11/ymd/MD/Vibe/GetMoving/路线与门禁.md`
- 需求、设计、决策记录：`/Users/a11/ymd/MD/Vibe/GetMoving`

## 当前状态

- 工程骨架完成，`assembleDebug`、`test`、`lint` 通过。
- 已实现：今日体重新增/覆盖、最近 7 天列表、目标差值展示、3 个底部 Tab、固定青绿主题。
- 未实现：补录、左滑删除、目标设置 UI、历史趋势、提醒、导出/导入。

## 待办

- [ ] 补齐 FR-1 的补录、删除、目标设置
- [ ] 阶段 1 收尾：包名定稿、分支保护
- [ ] 按笔记里的需求拆分继续 FR-2 ~ FR-8
