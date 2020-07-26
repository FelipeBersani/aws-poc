package awspock.poc.sqs.example.common.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {
    private String timestamp;
    private Integer statusCode;
    private String errorMessage;
}
