package com.uwu.kidsbankapi.dao

import com.uwu.kidsbankapi.dto.report.ShopTransactionReport
import com.uwu.kidsbankapi.dto.report.TransactionReport
import com.uwu.kidsbankapi.dto.report.LimitsReport
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class ReportDAO(private val jdbcTemplate: JdbcTemplate) {

    private val transactionsReportQuery = """
        SELECT
            CONCAT("users"."lastname", ' ', "users"."name", ' ', "users"."father_name") AS "ФИО ребенка",
            "shops"."name" AS "Название магазина",
            "shop_categories"."name" AS "Категория магазина",
            "transactions"."sum" AS "Сумма траты",
            "transactions"."time" AS "Дата покупки"
        FROM
            "transactions"
                JOIN
            "accounts" ON "transactions"."from" = "accounts"."id"
                JOIN
            "users" ON "accounts"."user" = "users"."id"
                JOIN
            "shops" ON "transactions"."to" = "shops"."id"
                JOIN
            "shop_categories" ON "shops"."category" = "shop_categories"."id"
        WHERE
            "users"."role" = 'CHILD'
        GROUP BY
            "ФИО ребенка", "transactions"."time","shops"."name", "shop_categories"."name", "transactions"."sum"
        ORDER BY
            "ФИО ребенка", "transactions"."time" DESC;
    """.trimIndent()

    private val limitsReportQuery = """
        SELECT
            CONCAT("users"."lastname" , ' ', "users"."name") AS "ФИ ребенка",
            "shops"."name" AS "Магазин",
            "shop_categories"."name" AS "Категория магазина",
            SUM("transactions"."sum") AS "Сумма потрачена"
        FROM
            "transactions"
                JOIN
            "accounts" ON "transactions"."from" = "accounts"."id"
                JOIN
            "users" ON "accounts"."user" = "users"."id"
                JOIN
            "shops" ON "transactions"."to" = "shops"."id"
                JOIN
            "shop_categories" ON "shops"."category" = "shop_categories"."id"
        WHERE
            "users"."role" = 'CHILD'
        GROUP BY
            "users"."name", "users"."lastname", "shop_categories"."name", "shops"."name"
        ORDER BY
            "ФИ ребенка", "Сумма потрачена" DESC;
    """.trimIndent()

    private val shopTransactionsReportQuery = """
        SELECT
            CONCAT("users"."name" , ' ', "users"."lastname") AS "ФИ ребенка",
            "shop_categories"."name" AS "Категория магазина",
            "shops"."name" AS "Магазин",
            SUM("transactions"."sum") AS "Сумма потрачена"
        FROM
            "transactions"
                JOIN
            "accounts" ON "transactions"."from" = "accounts"."id"
                JOIN
            "users" ON "accounts"."user" = "users"."id"
                JOIN
            "shops" ON "transactions"."to" = "shops"."id"
                JOIN
            "shop_categories" ON "shops"."category" = "shop_categories"."id"
        WHERE
            "users"."role" = 'CHILD'
        GROUP BY
            "users"."name", "users"."lastname", "shop_categories"."name", "shops"."name"
        ORDER BY
            "users"."lastname", "users"."name", "Категория магазина", "Магазин";
    """.trimIndent()

    fun getTransactionsReport(): List<TransactionReport> {
        val data = jdbcTemplate.queryForList(transactionsReportQuery)
        return data.map {
            TransactionReport(
                childFullName = it["ФИО ребенка"].toString(),
                shopName = it["Название магазина"].toString(),
                shopCategory = it["Категория магазина"].toString(),
                sum = it["Сумма покупки"].toString(),
                date = it["Дата и время"].toString()
            )
        }
    }

    fun getLimitsReport(): List<LimitsReport> {
        val data = jdbcTemplate.queryForList(limitsReportQuery)
        return data.map {
            LimitsReport(
                parentFullName = it["ФИО родителя"].toString(),
                childFullName = it["ФИО ребенка"].toString(),
                category = it["Запрещенная категория"].toString()
            )
        }
    }

    fun getShopTransactionsReport(): List<ShopTransactionReport> {
        val data = jdbcTemplate.queryForList(shopTransactionsReportQuery)
        return data.map {
            ShopTransactionReport(
                childName = it["ФИ ребенка"].toString(),
                category = it["Категория магазина"].toString(),
                shop = it["Магазин"].toString(),
                sum = it["Сумма потрачена"].toString()
            )
        }
    }
}