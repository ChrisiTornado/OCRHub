package at.technikumwien.dmsbackend.persistence;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "documents")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentIndex {
    @Id
    Long documentId;

    @Field(type = FieldType.Text)
    String title;

    @Field(type = FieldType.Text)
    String description;

    @Field(type = FieldType.Keyword)
    String type;

    @Field(type = FieldType.Long)
    Long size;

    @Field(type = FieldType.Date)
    String uploadDate;

    @Field(type = FieldType.Keyword)
    String fileKey;

    @Field(type = FieldType.Text)
    String content;

    @Field(type = FieldType.Date)
    String ocrProcessedAt;
}
