package com.example.getmoving.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.getmoving.R
import com.example.getmoving.feature.history.HistoryScreen
import com.example.getmoving.feature.home.HomeRoute
import com.example.getmoving.feature.settings.SettingsScreen

private data class TopLevelDestination(
    val key: NavKey,
    val labelRes: Int,
    val icon: ImageVector,
)

private val topLevelDestinations = listOf(
    TopLevelDestination(HomeKey, R.string.nav_home, Icons.Filled.Home),
    TopLevelDestination(HistoryKey, R.string.nav_history, Icons.Filled.DateRange),
    TopLevelDestination(SettingsKey, R.string.nav_settings, Icons.Filled.Settings),
)

@Composable
fun GetMovingNavigation() {
    val backStack = rememberNavBackStack(HomeKey)
    val currentKey = backStack.lastOrNull()

    Scaffold(
        bottomBar = {
            NavigationBar {
                topLevelDestinations.forEach { destination ->
                    NavigationBarItem(
                        selected = currentKey == destination.key,
                        onClick = { backStack.switchTo(destination.key) },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = null,
                            )
                        },
                        label = { Text(stringResource(destination.labelRes)) },
                    )
                }
            }
        },
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                entry<HomeKey> { HomeRoute() }
                entry<HistoryKey> { HistoryScreen() }
                entry<SettingsKey> { SettingsScreen() }
            },
        )
    }
}

private fun NavBackStack<NavKey>.switchTo(key: NavKey) {
    if (lastOrNull() == key) return
    while (size > 1) {
        removeLastOrNull()
    }
    if (lastOrNull() != key) {
        removeLastOrNull()
        add(key)
    }
}
