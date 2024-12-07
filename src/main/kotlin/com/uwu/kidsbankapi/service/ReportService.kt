package com.uwu.kidsbankapi.service

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class ReportService(private val jdbcTemplate: JdbcTemplate) {

    fun generateAnimalReport(): ByteArray {
        val query = """
            SELECT 
                e.description AS "Название загона",
                a.name AS "Имя животного",
                a.species AS "Вид",
                a.date_of_birth AS "Дата рождения",
                a.arrival_date AS "Дата прибытия"
            FROM Animals a
            JOIN Enclosures e ON a.enclosure_id = e.enclosure_id
        """
        val data = jdbcTemplate.queryForList(query)

        val header = "Список животных в загонах на декабрь 2024 года\n\n" +
                "Название загона                         | Имя животного    | Вид                 | Дата рождения | Дата прибытия\n" +
                "-------------------------------------------------------------------------------------------------------------------\n"

        val body = data.joinToString("\n") { row ->
            String.format(
                "%-40s| %-17s| %-20s| %-14s| %-13s",
                row["Название загона"],
                row["Имя животного"],
                row["Вид"],
                row["Дата рождения"],
                row["Дата прибытия"]
            )
        }

        return (header + body).toByteArray(Charsets.UTF_8)
    }

    fun generateMedicalReport(month: Int, year: Int): ByteArray {
        val query = """
            SELECT 
                a.name AS "Имя животного",
                m.checkup_date AS "Дата осмотра",
                m.diagnosis AS "Диагноз",
                m.treatment AS "Назначенное лечение",
                CONCAT(s.first_name, ' ', s.last_name) AS "ФИО сотрудника"
            FROM Medical_checkups m
            JOIN Animals a ON m.animal_id = a.animal_id
            JOIN Staff s ON m.staff_id = s.staff_id
            WHERE EXTRACT(MONTH FROM m.checkup_date) = ? 
              AND EXTRACT(YEAR FROM m.checkup_date) = ?
        """
        val data = jdbcTemplate.queryForList(query, month, year)

        val header = "Отчет о медицинских осмотрах за $month/$year\n\n" +
                "Имя животного    | Дата осмотра  | Диагноз                                 | Назначенное лечение           | ФИО сотрудника\n" +
                "--------------------------------------------------------------------------------------------------------------------------------------\n"

        val body = data.joinToString("\n") { row ->
            String.format(
                "%-17s| %-14s| %-40s| %-30s| %-20s",
                row["Имя животного"],
                row["Дата осмотра"],
                row["Диагноз"],
                row["Назначенное лечение"],
                row["ФИО сотрудника"]
            )
        }

        return (header + body).toByteArray(Charsets.UTF_8)
    }

    fun generateTicketSalesReport(month: Int, year: Int): ByteArray {
        val query = """
            SELECT 
                v.name AS "Имя посетителя",
                t.ticket_type AS "Тип билета",
                t.price AS "Цена",
                t.purchase_date AS "Дата покупки"
            FROM Tickets t
            JOIN Visitors v ON t.visitor_id = v.visitor_id
            WHERE EXTRACT(MONTH FROM t.purchase_date) = ?
              AND EXTRACT(YEAR FROM t.purchase_date) = ?
        """
        val data = jdbcTemplate.queryForList(query, month, year)

        val header = "Продажи билетов за $month/$year\n\n" +
                "Имя посетителя                          | Тип билета  | Цена        | Дата покупки\n" +
                "--------------------------------------------------------------------------------------\n"

        val body = data.joinToString("\n") { row ->
            String.format(
                "%-40s| %-12s| %-12s| %-13s",
                row["Имя посетителя"],
                row["Тип билета"],
                row["Цена"],
                row["Дата покупки"]
            )
        }

        return (header + body).toByteArray(Charsets.UTF_8)
    }
}
