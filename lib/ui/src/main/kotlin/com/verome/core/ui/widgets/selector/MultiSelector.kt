package com.verome.core.ui.widgets.selector

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.verome.core.ui.theme.additionalColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val ANIMATION_DURATION = 200

@Stable
interface MultiSelectorState {
    val selectedIndex: Float
    val startCornerPercent: Int
    val endCornerPercent: Int

    fun selectOption(scope: CoroutineScope, index: Int)
}

@Stable
class MultiSelectorStateImpl<T>(
    options: Array<T>,
    selectedOption: T,
) : MultiSelectorState {

    override val selectedIndex: Float
        get() = _selectedIndex.value
    override val startCornerPercent: Int
        get() = _startCornerPercent.value.toInt()
    override val endCornerPercent: Int
        get() = _endCornerPercent.value.toInt()

    private var _selectedIndex = Animatable(options.indexOf(selectedOption).toFloat())
    private var _startCornerPercent = Animatable(
        if (options.first() == selectedOption) 50f else 15f,
    )
    private var _endCornerPercent = Animatable(
        if (options.last() == selectedOption) 50f else 15f,
    )

    private val numOptions = options.size
    private val animationSpec = tween<Float>(
        durationMillis = ANIMATION_DURATION,
        easing = FastOutSlowInEasing,
    )

    override fun selectOption(scope: CoroutineScope, index: Int) {
        scope.launch {
            _selectedIndex.animateTo(
                targetValue = index.toFloat(),
                animationSpec = animationSpec,
            )
        }
        scope.launch {
            _startCornerPercent.animateTo(
                targetValue = if (index == 0) 50f else 15f,
                animationSpec = animationSpec,
            )
        }
        scope.launch {
            _endCornerPercent.animateTo(
                targetValue = if (index == numOptions - 1) 50f else 15f,
                animationSpec = animationSpec,
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultiSelectorStateImpl<*>

        if (_selectedIndex != other._selectedIndex) return false
        if (_startCornerPercent != other._startCornerPercent) return false
        if (_endCornerPercent != other._endCornerPercent) return false
        if (numOptions != other.numOptions) return false
        if (animationSpec != other.animationSpec) return false

        return true
    }

    override fun hashCode(): Int {
        var result = _selectedIndex.hashCode()
        result = 31 * result + _startCornerPercent.hashCode()
        result = 31 * result + _endCornerPercent.hashCode()
        result = 31 * result + numOptions
        result = 31 * result + animationSpec.hashCode()
        return result
    }
}

@Composable
fun <T> MultiSelector(
    modifier: Modifier = Modifier,
    options: Array<T>,
    selectedOption: T,
    onOptionSelect: (T) -> Unit,
) {
    val state: MultiSelectorState = rememberMultiSelectorState(
        options = options,
        selectedOption = selectedOption,
    )
    require(options.size >= 2) { "This composable requires at least 2 options" }
    require(options.contains(selectedOption)) { "Invalid selected option [$selectedOption]" }
    LaunchedEffect(key1 = options, key2 = selectedOption) {
        state.selectOption(this, options.indexOf(selectedOption))
    }
    Layout(
        modifier = modifier
            .clip(shape = RoundedCornerShape((9 * 1.2).dp))
            .background(MaterialTheme.additionalColors.selectorBackground)
            .height((32 * 1.2).dp)
            .padding((2 * 1.2).dp),
        content = {
            options.forEach { option ->
                Box(
                    modifier = Modifier
                        .layoutId(MultiSelectorOption.Option)
                        .clickable(
                            onClick = { onOptionSelect(option) },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = option.toString(),
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.additionalColors.textMain,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 4.dp),
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape((9 * 1.2).dp))
                        .layoutId(MultiSelectorOption.Background)
                        .background(MaterialTheme.additionalColors.coreWhite),
                )
            }
        },
    ) { measurables, constraints ->
        val optionWidth = constraints.maxWidth / options.size
        val optionConstraints = Constraints.fixed(
            width = optionWidth,
            height = constraints.maxHeight,
        )
        val optionPlaceables = measurables
            .filter { measurable -> measurable.layoutId == MultiSelectorOption.Option }
            .map { measurable -> measurable.measure(optionConstraints) }
        val backgroundPlaceable = measurables
            .first { measurable -> measurable.layoutId == MultiSelectorOption.Background }
            .measure(optionConstraints)
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            // 4
            backgroundPlaceable.placeRelative(
                x = (state.selectedIndex * optionWidth).toInt(),
                y = 0,
            )
            optionPlaceables.forEachIndexed { index, placeable ->
                placeable.placeRelative(
                    x = optionWidth * index,
                    y = 0,
                )
            }
        }
    }
}

@Composable
fun <T> rememberMultiSelectorState(
    options: Array<T>,
    selectedOption: T,
) = remember {
    MultiSelectorStateImpl(
        options,
        selectedOption,
    )
}

enum class MultiSelectorOption {
    Option,
    Background,
}