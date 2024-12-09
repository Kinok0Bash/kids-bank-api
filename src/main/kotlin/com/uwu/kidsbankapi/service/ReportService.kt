package com.uwu.kidsbankapi.service

import com.uwu.kidsbankapi.dao.ReportDAO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ReportService(
    private val reportDAO: ReportDAO
) {
    private val logger = LoggerFactory.getLogger(ReportService::class.java)

    fun generateTransactionsTxtReport(): ByteArray {
        val data = reportDAO.getTransactionsReport()
        val header = "Отчет по всем транзакциям пользователей\n\n" +
                " ФИО ребенка                                                  | Название магазина    | Категория магазина        | Сумма покупки   | Дата и время\n" +
                "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n"

        val body = StringBuilder()
        data.forEach { row ->
            body.append(String.format(
                " %-60s | %-20s | %-25s | %-15s | %-30s",
                row.childFullName,
                row.shopName,
                row.shopCategory,
                row.sum,
                row.date
            ) + "\n")
        }

        logger.info("Отчет сформирован успешно")
        return (header + body.toString()).toByteArray(Charsets.UTF_8)
    }

    fun generateLimitsTxtReport(): ByteArray {
        val data = reportDAO.getLimitsReport()
        val header = "Отчет по всем ограничениям пользователей\n\n" +
                " ФИО ребенка                                                  | ФИО родителя                                                 | Категория магазина\n" +
                "--------------------------------------------------------------------------------------------------------------------------------------------------------------------\n"

        val body = StringBuilder()
        data.forEach { row ->
            body.append(String.format(
                " %-60s | %-60s | %-25s",
                row.childFullName,
                row.parentFullName,
                row.category
            ) + "\n")
        }

        logger.info("Отчет сформирован успешно")
        return (header + body.toString()).toByteArray(Charsets.UTF_8)
    }

    fun generateShopTransactionsTxtReport(): ByteArray {
        val data = reportDAO.getShopTransactionsReport()
        val header = "Сумма потраченная каждым пользователем в каждом магазине\n\n" +
                " ФИ ребенка                               | Название магазина    | Категория магазина        | Сумма покупок\n" +
                "-------------------------------------------------------------------------------------------------------------------------\n"

        val body = StringBuilder()
        data.forEach { row ->
            body.append(String.format(
                " %-40s | %-20s | %-25s | %-15s",
                row.childName,
                row.shop,
                row.category,
                row.sum
            ) + "\n")
        }

        logger.info("Отчет сформирован успешно")
        return (header + body.toString()).toByteArray(Charsets.UTF_8)
    }
}
