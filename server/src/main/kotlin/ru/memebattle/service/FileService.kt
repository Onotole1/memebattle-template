package ru.memebattle.service

import io.ktor.features.BadRequestException
import io.ktor.features.UnsupportedMediaTypeException
import io.ktor.http.ContentType
import io.ktor.http.content.MultiPartData
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.AutoDetectParser
import ru.memebattle.common.dto.MediaResponseDto
import ru.memebattle.common.model.MediaType
import java.io.BufferedInputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class FileService(private val uploadPath: String) {

	init {
		if (Files.notExists(Paths.get(uploadPath))) {
			Files.createDirectory(Paths.get(uploadPath))
		}
	}

	suspend fun save(multipart: MultiPartData): MediaResponseDto {
		var response: MediaResponseDto? = null
		multipart.forEachPart { part ->
			when (part) {
				is PartData.FileItem -> {
					if (part.name == "file") {
						BufferedInputStream(part.streamProvider()).use { buffer ->
							val parser = AutoDetectParser()
							val detector = parser.detector
							val md = Metadata()
							val mediaType = withContext(Dispatchers.IO) {
								detector.detect(buffer, md)
							}
							if (mediaType.type != "image") {
								throw UnsupportedMediaTypeException(
									part.contentType ?: ContentType.Any
								)
							}

							val ext = when (part.contentType) {
								ContentType.Image.JPEG -> "jpg"
								ContentType.Image.PNG  -> "png"
								else                   -> throw UnsupportedMediaTypeException(part.contentType!!)
							}
							val name = "${UUID.randomUUID()}.$ext"
							val path = Paths.get(uploadPath, name)

							withContext(Dispatchers.IO) {
								Files.copy(buffer, path)
							}

							part.dispose()
							response = MediaResponseDto(name, MediaType.IMAGE)
							return@forEachPart
						}
					}

					part.dispose()
				}
			}
		}

		@Suppress("EXPERIMENTAL_API_USAGE")
		return response ?: throw BadRequestException("No file field in request")
	}
}

