package com.example.zebracoffee.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

object CommonUtils {

    class DateUtils {
        companion object {
            fun reformatDate(input: String): String {
                if (input.length != 8) {
                    return "Invalid input"
                }
                return "${input.substring(0, 2)}.${input.substring(2, 4)}.${input.substring(4)}"
            }

            fun requestDate(input: String): String {
                val parts = input.split(".")
                if (parts.size != 3) {
                    return "Invalid input"
                }
                return "${parts[2]}-${parts[1]}-${parts[0]}"
            }

            @RequiresApi(Build.VERSION_CODES.O)
            fun isValidDate(dateString: String): Boolean {
                val parts = dateString.split("-")
                if (parts.size != 3) {
                    return false
                }

                try {
                    val year = when (parts[0].length) {
                        4 -> {
                            if (parts[0][0] == '0') {
                                return false
                            }
                            parts[0].toInt()
                        }

                        2 -> {
                            val currentYearLastTwoDigits = LocalDate.now().year % 100
                            val inputYear = parts[0].toInt()

                            if (inputYear <= currentYearLastTwoDigits) {
                                2000 + inputYear
                            } else {
                                1900 + inputYear
                            }
                        }

                        else -> return false
                    }

                    val month = parts[1].toInt()
                    val day = parts[2].toInt()

                    if (month < 1 || month > 12 || day < 1 || day > 31) {
                        return false
                    }

                    val currentDate = LocalDate.now()
                    val inputDate = LocalDate.of(year, month, day)

                    return !inputDate.isAfter(currentDate)
                } catch (e: NumberFormatException) {
                    return false
                }
            }
        }
    }

    class FormatNumber {
        companion object {
            fun reformatPhoneNumber(originalNumber: String): String {
                val digitsOnly = originalNumber.replace(Regex("\\D"), "")

                return buildString {
                    if (digitsOnly.isNotEmpty()) {
                        append("+${digitsOnly[0]} ")
                    }
                    if (digitsOnly.length >= 4) {
                        append("(${digitsOnly.substring(1, 4)}) ")
                    }
                    if (digitsOnly.length >= 7) {
                        append("${digitsOnly.substring(4, 7)} ")
                    }
                    if (digitsOnly.length >= 9) {
                        append("${digitsOnly.substring(7, 9)} ")
                    }
                    if (digitsOnly.length >= 11) {
                        append(digitsOnly.substring(9))
                    }
                }
            }

        }
    }
}