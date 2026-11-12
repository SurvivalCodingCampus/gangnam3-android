package com.suwonsmartapp.recipeapp.ui

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Shortcut
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.Shortcut
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.ui.graphics.vector.ImageVector
import com.suwonsmartapp.recipeapp.R

/**
 * App icon set mapped onto Material icons.
 * Outline = thin stroke icons, Bold = filled icons, Social = brand vector drawables.
 */
object AppIcons {

    object Outline {
        val bookmark: ImageVector = Icons.Outlined.BookmarkBorder
        val home: ImageVector = Icons.Outlined.Home
        val notification: ImageVector = Icons.Outlined.Notifications
        val settings: ImageVector = Icons.Outlined.Settings
        val star: ImageVector = Icons.Outlined.StarOutline
        val filter: ImageVector = Icons.Outlined.Tune
        val timer: ImageVector = Icons.Outlined.Timer
        val search: ImageVector = Icons.Outlined.Search
        val location: ImageVector = Icons.Outlined.LocationOn
        val arrowRight: ImageVector = Icons.AutoMirrored.Outlined.ArrowForward
        val arrowDown: ImageVector = Icons.Outlined.KeyboardArrowDown
        val arrowUp: ImageVector = Icons.Outlined.KeyboardArrowUp
        val more: ImageVector = Icons.Outlined.MoreHoriz
        val document: ImageVector = Icons.Outlined.Description
        val note: ImageVector = Icons.Outlined.NoteAlt
        val chat: ImageVector = Icons.Outlined.Chat
        val share: ImageVector = Icons.AutoMirrored.Outlined.Shortcut
        val profile: ImageVector = Icons.Outlined.Person
    }

    object Bold {
        val bookmark: ImageVector = Icons.Filled.Bookmark
        val home: ImageVector = Icons.Filled.Home
        val notification: ImageVector = Icons.Filled.Notifications
        val settings: ImageVector = Icons.Filled.Settings
        val star: ImageVector = Icons.Filled.Star
        val filter: ImageVector = Icons.Filled.Tune
        val timer: ImageVector = Icons.Filled.Timer
        val search: ImageVector = Icons.Filled.Search
        val location: ImageVector = Icons.Filled.LocationOn
        val arrowRight: ImageVector = Icons.AutoMirrored.Filled.ArrowForward
        val arrowDown: ImageVector = Icons.Filled.KeyboardArrowDown
        val arrowUp: ImageVector = Icons.Filled.KeyboardArrowUp
        val more: ImageVector = Icons.Filled.MoreHoriz
        val document: ImageVector = Icons.Filled.Description
        val note: ImageVector = Icons.Filled.NoteAlt
        val chat: ImageVector = Icons.Filled.Chat
        val share: ImageVector = Icons.AutoMirrored.Filled.Shortcut
        val profile: ImageVector = Icons.Filled.Person
    }

    object Social {
        @DrawableRes val google: Int = R.drawable.ic_google
        @DrawableRes val facebook: Int = R.drawable.ic_facebook
    }
}
