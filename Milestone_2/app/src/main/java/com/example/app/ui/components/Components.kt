package com.example.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.*
import com.example.app.util.toBanglaRating

/* ------------------------------------------------------------------
 * Reusable building blocks. Every screen is assembled from these, so a
 * style change here updates the whole app at once.
 *
 * NOTE ON ICONS: androidx.compose.material.icons was removed from
 * Material 3, so Icons.Default.* no longer exists. Instead of pinning a
 * dead library, the handful of icons this app needs are drawn as Unicode
 * glyphs (Glyph) or with Canvas (SearchGlyph). Zero dependencies.
 * ------------------------------------------------------------------ */

/** A text-based icon. Cheap, scalable, and never breaks on a version bump. */
@Composable
fun Glyph(
    symbol: String,
    tint: Color = Ink,
    size: Int = 18,
    modifier: Modifier = Modifier
) {
    Text(
        text = symbol,
        color = tint,
        fontSize = size.sp,
        fontWeight = FontWeight.Medium,
        modifier = modifier
    )
}

/** Hand-drawn magnifying glass for the search bar. */
@Composable
fun SearchGlyph(modifier: Modifier = Modifier, tint: Color = InkMuted) {
    Canvas(modifier = modifier.size(18.dp)) {
        val radius = size.minDimension * 0.32f
        val center = Offset(size.width * 0.40f, size.height * 0.40f)
        val stroke = 2.dp.toPx()
        drawCircle(color = tint, radius = radius, center = center, style = Stroke(width = stroke))
        drawLine(
            color = tint,
            start = Offset(center.x + radius * 0.72f, center.y + radius * 0.72f),
            end = Offset(size.width * 0.94f, size.height * 0.94f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
    }
}

/** Circular avatar showing the person's first letter. */
@Composable
fun Avatar(name: String, size: Dp = 44.dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(CholoGreenLight),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name.trim().take(1),
            style = MaterialTheme.typography.titleMedium,
            color = CholoGreen
        )
    }
}

/** A form field with the label sitting above the box, as in the wireframes. */
@Composable
fun CholoTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    minLines: Int = 1,
    prefix: String? = null,
    isPassword: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = Ink,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = InkMuted) },
            singleLine = singleLine,
            minLines = minLines,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation =
                if (isPassword && !passwordVisible) PasswordVisualTransformation()
                else VisualTransformation.None,
            leadingIcon = prefix?.let {
                {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            it,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 14.dp, end = 10.dp)
                        )
                        Box(
                            Modifier
                                .width(1.dp)
                                .height(24.dp)
                                .background(Line)
                        )
                    }
                }
            },
            trailingIcon = if (isPassword) {
                {
                    TextButton(onClick = { passwordVisible = !passwordVisible }) {
                        Text(
                            if (passwordVisible) "লুকান" else "দেখান",
                            style = MaterialTheme.typography.labelSmall,
                            color = CholoGreen
                        )
                    }
                }
            } else null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CholoGreen,
                unfocusedBorderColor = Line,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = SurfaceAlt
            )
        )
    }
}

/** Read-only field that opens a dropdown. Used for car type selection. */
@Composable
fun CholoDropdownField(
    label: String,
    selected: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = Ink,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceAlt)
                    .border(1.dp, Line, RoundedCornerShape(12.dp))
                    .clickable { expanded = true }
                    .padding(horizontal = 16.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = selected,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (selected.isBlank()) InkMuted else Ink
                )
                Glyph("▾", tint = InkMuted)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onSelect(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

/** The filled green call-to-action at the bottom of most screens. */
@Composable
fun CholoPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = CholoGreen,
            contentColor = CholoWhite
        )
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}

/** The lower-emphasis sibling — "Decline", "Cancel". */
@Composable
fun CholoOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, Line),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Ink)
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}

/** Top bar with a back arrow, matching the wireframes. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CholoTopBar(title: String, onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
                color = Ink
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Glyph("←", tint = Ink, size = 22)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = CholoWhite)
    )
}

/** Small coloured pill: Ongoing / Completed / Pending / Rented. */
@Composable
fun StatusChip(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 12.dp, vertical = 5.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/** ★ 4.8 — shared by car cards, owner cards and passenger cards. */
@Composable
fun RatingRow(rating: Double, suffix: String? = null) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Glyph("★", tint = StatusPending, size = 14)
        Spacer(Modifier.width(4.dp))
        Text(
            text = rating.toBanglaRating() + (suffix?.let { " $it" } ?: ""),
            style = MaterialTheme.typography.labelMedium,
            color = InkMuted
        )
    }
}

/** Bordered placeholder standing in for a real car photo. */
@Composable
fun CarPhotoPlaceholder(modifier: Modifier = Modifier, label: String = "Car Photo") {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceAlt)
            .border(1.dp, Line, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = InkMuted)
    }
}

/** "Label ............ Value" row used in every summary panel. */
@Composable
fun InfoRow(
    label: String,
    value: String,
    emphasize: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            style = if (emphasize) MaterialTheme.typography.titleMedium
            else MaterialTheme.typography.bodyMedium,
            color = if (emphasize) Ink else InkMuted
        )
        Text(
            value,
            style = if (emphasize) MaterialTheme.typography.titleMedium
            else MaterialTheme.typography.bodyMedium,
            fontWeight = if (emphasize) FontWeight.Bold else FontWeight.SemiBold,
            color = Ink
        )
    }
}

/** Bordered white card — the default container for list items and panels. */
@Composable
fun CholoCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    highlighted: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    val base = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(CholoWhite)
        .border(
            width = if (highlighted) 2.dp else 1.dp,
            color = if (highlighted) CholoGreen else Line,
            shape = RoundedCornerShape(16.dp)
        )

    Column(
        modifier = if (onClick != null) base.clickable(onClick = onClick) else base,
        content = content
    )
}

@Composable
fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = Ink,
        modifier = modifier
    )
}

/** Footer trust line from the wireframes. */
@Composable
fun VerifiedFooter(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Glyph("✓", tint = CholoGreen, size = 15)
        Spacer(Modifier.width(8.dp))
        Text(
            "১০০% ভেরিফাইড এবং সুরক্ষিত পেমেন্ট সুবিধা",
            style = MaterialTheme.typography.labelMedium,
            color = InkMuted
        )
    }
}
