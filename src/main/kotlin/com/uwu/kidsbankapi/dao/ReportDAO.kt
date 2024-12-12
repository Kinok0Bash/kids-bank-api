package com.uwu.kidsbankapi.dao

import com.uwu.kidsbankapi.dto.report.LimitsReport
import com.uwu.kidsbankapi.dto.report.ShopTransactionReport
import com.uwu.kidsbankapi.dto.report.TransactionReport
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat

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
            "accounts" ON "transactions"."sender" = "accounts"."id"
                JOIN
            "users" ON "accounts"."user_id" = "users"."id"
                JOIN
            "shops" ON "transactions"."recipient" = "shops"."id"
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
            CONCAT("child"."lastname" , ' ', "child"."name" , ' ', "child"."father_name") AS "ФИО ребенка",
            CONCAT("parent"."lastname" , ' ', "parent"."name" , ' ', "parent"."father_name") AS "ФИО родителя",
            "shop_categories"."name" AS "Запрещенная категория"
        FROM
            "categories_limit"
                JOIN
            "shop_categories" ON "categories_limit"."category" = "shop_categories"."id"
                JOIN
            "users" AS "child" ON "categories_limit"."child" = "child"."id"
                JOIN
            "users" AS "parent" ON "parent"."child" = child."id"
        WHERE
            "parent"."role" = 'PARENT'
          AND "child"."role" = 'CHILD'
        GROUP BY
            "ФИО ребенка", "ФИО родителя", "Запрещенная категория"
        ORDER BY
            "ФИО ребенка";
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
            "accounts" ON "transactions"."sender" = "accounts"."id"
                JOIN
            "users" ON "accounts"."user_id" = "users"."id"
                JOIN
            "shops" ON "transactions"."recipient" = "shops"."id"
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
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
        return data.map {
            TransactionReport(
                childFullName = it["ФИО ребенка"].toString(),
                shopName = it["Название магазина"].toString(),
                shopCategory = it["Категория магазина"].toString(),
                sum = it["Сумма траты"].toString(),
                date = dateFormat.format(it["Дата покупки"])
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