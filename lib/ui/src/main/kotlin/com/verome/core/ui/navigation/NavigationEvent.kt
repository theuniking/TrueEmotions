package com.verome.core.ui.navigation

import com.verome.core.ui.event.NavigationEvent

/**
 * Данное событие используется для открытия экрана с сохранением backstack
 */
class OpenScreenEvent(val screen: Screen) : NavigationEvent()

/**
 * Данное событие используется для перехода назад или закрытия Bottomsheet
 */
object NavigateBackEvent : NavigationEvent()

/**
 * Данное событие используется для открытия Bottomsheet
 */
class OpenBottomSheetEvent(val screen: Screen) : NavigationEvent()

/**
 * Данное событие используется для закрытия Bottomsheet
 */
object CloseBottomSheetEvent : NavigationEvent()