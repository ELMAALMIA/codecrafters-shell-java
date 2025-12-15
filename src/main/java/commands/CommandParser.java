package commands;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {
    private static final char BACKSLASH = '\\';
    private static final char SINGLE_QUOTE = '\'';
    private static final char DOUBLE_QUOTE = '"';
    private static final char SPACE = ' ';
    private static final char TAB = '\t';

    public String[] parse(String input) {
        if (input == null) {
            return new String[0];
        }

        ParsingState state = new ParsingState();
        int index = 0;

        while (index < input.length()) {
            char currentChar = input.charAt(index);

            switch (currentChar) {
                case BACKSLASH:
                    index = handleBackslash(input, index, state);
                    break;

                case SINGLE_QUOTE:
                    index = handleSingleQuote(input, index, state);
                    break;

                case DOUBLE_QUOTE:
                    index = handleDoubleQuote(input, index, state);
                    break;

                case SPACE:
                case TAB:
                    index = handleWhitespace(input, index, state);
                    break;

                default:
                    state.currentArgument.append(currentChar);
                    index++;
                    break;
            }
        }

        addCurrentArgumentIfNotEmpty(state.arguments, state.currentArgument);
        return state.arguments.toArray(new String[0]);
    }

    private static class ParsingState {
        final List<String> arguments = new ArrayList<>();
        final StringBuilder currentArgument = new StringBuilder();
        boolean inSingleQuotes = false;
        boolean inDoubleQuotes = false;
    }

    private int handleBackslash(String input, int index, ParsingState state) {
        if (!state.inSingleQuotes && !state.inDoubleQuotes) {
            return handleBackslashOutsideQuotes(input, index, state.currentArgument);
        } else if (state.inDoubleQuotes) {
            return handleBackslashInsideDoubleQuotes(input, index, state.currentArgument);
        } else {
            state.currentArgument.append(BACKSLASH);
            return index + 1;
        }
    }

    private int handleBackslashOutsideQuotes(String input, int index, StringBuilder currentArgument) {
        index++;
        if (index < input.length()) {
            currentArgument.append(input.charAt(index));
            index++;
        }
        return index;
    }

    private int handleBackslashInsideDoubleQuotes(String input, int index, StringBuilder currentArgument) {
        index++;
        if (index < input.length()) {
            char nextChar = input.charAt(index);
            if (nextChar == DOUBLE_QUOTE || nextChar == BACKSLASH) {
                currentArgument.append(nextChar);
                index++;
            } else {
                currentArgument.append(BACKSLASH);
                currentArgument.append(nextChar);
                index++;
            }
        } else {
            currentArgument.append(BACKSLASH);
            index++;
        }
        return index;
    }

    private int handleSingleQuote(String input, int index, ParsingState state) {
        if (state.inSingleQuotes) {
            state.inSingleQuotes = false;
            index++;
            if (index < input.length() && input.charAt(index) == SINGLE_QUOTE) {
                state.inSingleQuotes = true;
            } else if (index >= input.length() || isWhitespace(input.charAt(index))) {
                return index;
            }
        } else if (!state.inDoubleQuotes) {
            state.inSingleQuotes = true;
            index++;
        } else {
            state.currentArgument.append(SINGLE_QUOTE);
            index++;
        }
        return index;
    }

    private int handleDoubleQuote(String input, int index, ParsingState state) {
        if (state.inDoubleQuotes) {
            state.inDoubleQuotes = false;
            index++;
            if (index < input.length() && input.charAt(index) == DOUBLE_QUOTE) {
                state.inDoubleQuotes = true;
            } else if (index >= input.length() || isWhitespace(input.charAt(index))) {
                return index;
            }
        } else if (!state.inSingleQuotes) {
            state.inDoubleQuotes = true;
            index++;
        } else {
            state.currentArgument.append(DOUBLE_QUOTE);
            index++;
        }
        return index;
    }

    private int handleWhitespace(String input, int index, ParsingState state) {
        if (!state.inSingleQuotes && !state.inDoubleQuotes) {
            addCurrentArgumentIfNotEmpty(state.arguments, state.currentArgument);
            while (index < input.length() && isWhitespace(input.charAt(index))) {
                index++;
            }
        } else {
            state.currentArgument.append(input.charAt(index));
            index++;
        }
        return index;
    }

    private boolean isWhitespace(char c) {
        return c == SPACE || c == TAB;
    }

    private void addCurrentArgumentIfNotEmpty(List<String> arguments, StringBuilder currentArgument) {
        if (currentArgument.length() > 0) {
            arguments.add(currentArgument.toString());
            currentArgument.setLength(0);
        }
    }
}

