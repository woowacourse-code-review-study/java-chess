package chess.controller.command;

import java.util.Arrays;
import java.util.List;

public enum CommandType {

    START(0),
    MOVE(2),
    END(0),
    ;

    private final int parameterSize;

    CommandType(final int parameterSize) {
        this.parameterSize = parameterSize;
    }

    public static CommandType find(final String input) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("없는 커맨드입니다."));
    }

    public boolean matchSize(final List<String> parameters) {
        return parameters.size() == parameterSize;
    }
}
