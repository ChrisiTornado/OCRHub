package ta.technikumwien.dmsocr.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "documents")
public class DocumentIndex {
    @Id
    private Long documentId;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Keyword)
    private String type;

    @Field(type = FieldType.Long)
    private Long size;

    @Field(type = FieldType.Date)
    private String uploadDate;

    @Field(type = FieldType.Keyword)
    private String fileKey;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Date)
    private String ocrProcessedAt;
}
