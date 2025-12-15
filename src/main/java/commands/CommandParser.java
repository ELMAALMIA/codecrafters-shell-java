package commands;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {
    public String[] parse(String input) {
        List<String> arguments = new ArrayList<>();
        StringBuilder currentArgument = new StringBuilder();
        boolean inSingleQuotes = false;
        boolean inDoubleQuotes = false;
        int i = 0;

        while (i < input.length()) {
            char c = input.charAt(i);

            switch (c) {
                case '\\':
                    if (!inSingleQuotes && !inDoubleQuotes) {
                        i++;
                        if (i < input.length()) {
                            char nextChar = input.charAt(i);
                            currentArgument.append(nextChar);
                            i++;
                        }
                    } else {
                        currentArgument.append(c);
                        i++;
                    }
                    break;

                case '\'':
                    if (inSingleQuotes) {
                        inSingleQuotes = false;
                        i++;
                        if (i < input.length() && input.charAt(i) == '\'') {
                            inSingleQuotes = true;
                        } else if (i >= input.length() || input.charAt(i) == ' ' || input.charAt(i) == '\t') {
                            break;
                        }
                    } else if (!inDoubleQuotes) {
                        inSingleQuotes = true;
                        i++;
                    } else {
                        currentArgument.append(c);
                        i++;
                    }
                    break;

                case '"':
                    if (inDoubleQuotes) {
                        inDoubleQuotes = false;
                        i++;
                        if (i < input.length() && input.charAt(i) == '"') {
                            inDoubleQuotes = true;
                        } else if (i >= input.length() || input.charAt(i) == ' ' || input.charAt(i) == '\t') {
                            break;
                        }
                    } else if (!inSingleQuotes) {
                        inDoubleQuotes = true;
                        i++;
                    } else {
                        currentArgument.append(c);
                        i++;
                    }
                    break;

                case ' ':
                case '\t':
                    if (!inSingleQuotes && !inDoubleQuotes) {
                        if (currentArgument.length() > 0) {
                            arguments.add(currentArgument.toString());
                            currentArgument.setLength(0);
                        }
                        while (i < input.length() && (input.charAt(i) == ' ' || input.charAt(i) == '\t')) {
                            i++;
                        }
                    } else {
                        currentArgument.append(c);
                        i++;
                    }
                    break;

                default:
                    currentArgument.append(c);
                    i++;
                    break;
            }
        }

        if (currentArgument.length() > 0) {
            arguments.add(currentArgument.toString());
        }

        if (arguments.isEmpty() && !input.trim().isEmpty()) {
            return new String[]{input.trim()};
        }

        return arguments.toArray(new String[0]);
    }
}

