package com.example.bus_schedules.ui.composables

import com.example.bus_schedules.ui.UiState

data class BottomSheetState (
    val sheetState : SheetState,
    val currentSheet : Sheets
) : UiState {
    companion object {
        fun initial() = BottomSheetState(
            sheetState = SheetState.PEEK,
            currentSheet = Sheets.Home
        )
    }
}

enum class Sheets {
    Home, Schedules
}
enum class SheetState {
    PEEK, EXPANDED
}
