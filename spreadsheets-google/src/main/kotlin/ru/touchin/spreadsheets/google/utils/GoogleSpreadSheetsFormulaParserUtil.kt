package ru.touchin.spreadsheets.google.utils

import java.net.URL

object GoogleSpreadSheetsFormulaParserUtil {

    private const val HYPERLINK_REGEX_LINK_INDEX: Int = 1
    private const val RUS_LOCALE = "ru_RU"
    private const val DEFAULT_DIVIDER = ","
    private const val RUS_DIVIDER = ";"

    fun isFormulaHyperlink(jiraIssueLink: String, locale: String): Boolean {
        return getHyperLinkRegexByLocale(locale).matches(jiraIssueLink)
    }

    fun asFormulaHyperlink(url: URL, label: String, locale: String): String {
        val divider = getFormulaeDividerByLocale(locale)
        return "=HYPERLINK(\"$url\"$divider\"$label\")"
    }

    fun fromFormulaHyperlink(hyperlink: String, locale: String): URL {
        return getHyperLinkRegexByLocale(locale).matchEntire(hyperlink)
            ?.groupValues
            ?.get(HYPERLINK_REGEX_LINK_INDEX)
            ?.trim { it == '"' }
            ?.let { URL(it) }
            ?: throw IllegalStateException("Could not parse hyperlink \"$hyperlink\"")
    }

    private fun getHyperLinkRegexByLocale(locale: String): Regex {
        val divider = getFormulaeDividerByLocale(locale)
        return Regex("=HYPERLINK\\((.*)$divider(.*)\\)")
    }

    private fun getFormulaeDividerByLocale(locale: String): String {
        return if (locale == RUS_LOCALE) {
            RUS_DIVIDER
        } else {
            DEFAULT_DIVIDER
        }
    }

}
