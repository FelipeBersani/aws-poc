package awspock.poc.sqs.example.common.model.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Conditions {

    EQ("="),
    NOT_EQ("<>"),
    BT(">"),
    BTE(">="),
    LT("<"),
    LTE("<="),
    BETWEEN("Between"),
    EXISTS("Exists"),
    NOT_EXISTS("Not Exists"),
    CONTAINS("Contains"),
    NOT_CONTAINS("Not Contains"),
    BEGINS_WITH("Begins with");

    private String condition;
}
