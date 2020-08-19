package at.spengergasse.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@RequiredArgsConstructor
public class Reviews {
    @Id
    private String id;
    @NonNull
    private String source;
    @NonNull
    private String content;
}