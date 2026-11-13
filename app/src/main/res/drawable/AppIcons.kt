package drawable

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
        val bookmark: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Outlined.BookmarkBorder
        val home: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Outlined.Home
        val notification: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Outlined.Notifications
        val settings: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Outlined.Settings
        val star: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Outlined.StarOutline
        val filter: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Outlined.Tune
        val timer: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Outlined.Timer
        val search: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Outlined.Search
        val location: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Outlined.LocationOn
        val arrowRight: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.AutoMirrored.Outlined.ArrowForward
        val arrowDown: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Outlined.KeyboardArrowDown
        val arrowUp: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Outlined.KeyboardArrowUp
        val more: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Outlined.MoreHoriz
        val document: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Outlined.Description
        val note: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Outlined.NoteAlt
        val chat: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Outlined.Chat
        val share: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.AutoMirrored.Outlined.Shortcut
        val profile: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Outlined.Person
    }

    object Bold {
        val bookmark: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Filled.Bookmark
        val home: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Filled.Home
        val notification: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Filled.Notifications
        val settings: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Filled.Settings
        val star: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Filled.Star
        val filter: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Filled.Tune
        val timer: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Filled.Timer
        val search: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Filled.Search
        val location: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Filled.LocationOn
        val arrowRight: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ArrowForward
        val arrowDown: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Filled.KeyboardArrowDown
        val arrowUp: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Filled.KeyboardArrowUp
        val more: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Filled.MoreHoriz
        val document: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Filled.Description
        val note: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Filled.NoteAlt
        val chat: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Filled.Chat
        val share: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.Shortcut
        val profile: androidx.compose.ui.graphics.vector.ImageVector = androidx.compose.material.icons.Icons.Filled.Person
    }

    object Social {
        @androidx.annotation.DrawableRes
        val google: Int = com.suwonsmartapp.recipeapp.R.drawable.ic_google
        @androidx.annotation.DrawableRes
        val facebook: Int = com.suwonsmartapp.recipeapp.R.drawable.ic_facebook
    }
}
