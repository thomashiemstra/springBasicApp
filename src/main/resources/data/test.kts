import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File
import java.time.DateTimeException
import java.time.LocalDate

fun String.sanitizeSqlData(): String {
    return this.replace("\'", "\'\'")
}


data class Book (
    val bookID: Int?,
    val title: String,
    val author: String,
    val average_rating: Float,
    val isbn: String,
    val isbn13: String,
    val language_code: String,
    val num_pages: Int,
    val ratings_count: Int,
    val text_reviews_count: Int,
    val publication_date: LocalDate,
    val publisher: String
) {
    fun toSqlInsert(): String {
        return """INSERT INTO books (title, author_id, average_rating, isbn, isbn13, language_code, num_pages, ratings_count, text_reviews_count, publication_date, publisher_id) VALUES ('$title', (SELECT author_id from authors where authors.author_name='$author'), '$average_rating', '$isbn', '$isbn13', '$language_code', '$num_pages', '$ratings_count', '$text_reviews_count', '$publication_date', (SELECT publisher_id from publishers where publishers.publisher_name='$publisher'));"""
    }
}

fun convert() {
    val file = File("books.csv")
    val rows: List<List<String>> = csvReader().readAll(file)

    val uniqueAuthors = mutableSetOf<String>()
    val uniquePublishers = mutableSetOf<String>()
    val books = mutableListOf<Book>()

    rows.drop(1).forEach{row ->
        run {

            val sanitizedRow = row.map { it.sanitizeSqlData() }

            val title = sanitizedRow[1]
            val author = getFirstAuthor(sanitizedRow[2])
            uniqueAuthors.add(author)
            val rating = sanitizedRow[3].toFloat()
            val isbn = sanitizedRow[4]
            val isbn13 = sanitizedRow[5]
            val language_code = sanitizedRow[6]
            val num_pages = sanitizedRow[7].toInt()
            val ratings_count = sanitizedRow[8].toInt()
            val text_reviews_count = sanitizedRow[9].toInt()
            val publication_date = getDate(sanitizedRow[10])
            val publisher = sanitizedRow[11]
            uniquePublishers.add(publisher)

            books.add(
                Book(
                    bookID = null,
                    title = title,
                    author = author,
                    average_rating = rating,
                    isbn = isbn,
                    isbn13 = isbn13,
                    language_code = language_code,
                    num_pages = num_pages,
                    ratings_count = ratings_count,
                    text_reviews_count = text_reviews_count,
                    publication_date = publication_date,
                    publisher = publisher
                )
            )
        }
    }



    File("../db/migration/V20232005_10001__authorsData.sql").printWriter().use { out ->
        uniqueAuthors.forEach {
            out.println("INSERT INTO authors (author_name) VALUES (\'$it\');")
        }
        out.println()
    }

    File("../db/migration/V20232005_10002__publishersData.sql").printWriter().use { out ->
        uniquePublishers.forEach {
            out.println("INSERT INTO publishers (publisher_name) VALUES (\'$it\');")
        }
        out.println()
    }

    File("../db/migration/V20232005_10003__booksData.sql").printWriter().use {out ->
        books.forEach{
            out.println(it.toSqlInsert())
        }
    }
}


fun getFirstAuthor(input: String): String {
    return input.split("/")[0]
}

fun getDate(input: String): LocalDate {
    val split = input.split("/")
    return try {
        LocalDate.of(split[2].toInt(), split[0].toInt(), split[1].toInt())
    } catch (e: DateTimeException) {
        LocalDate.of(split[2].toInt(), 1, 1)
    }
}

convert()



