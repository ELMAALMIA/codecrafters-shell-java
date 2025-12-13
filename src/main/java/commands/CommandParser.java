package commands;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {
    public String[] parse(String input) {
        List<String> arguments = new ArrayList<>();
        StringBuilder currentArgument = new StringBuilder();
        boolean inSingleQuotes = false;
        int i = 0;

        while (i < input.length()) {
            char c = input.charAt(i);

            if (c == '\'') {
                if (inSingleQuotes) {
                    inSingleQuotes = false;
                    i++;
                    if (i < input.length() && input.charAt(i) == '\'') {
                        inSingleQuotes = true;
                        continue;
                    }
                    if (i >= input.length() || input.charAt(i) == ' ' || input.charAt(i) == '\t') {
                        continue;
                    }
                } else {
                    inSingleQuotes = true;
                    i++;
                    continue;
                }
            } else if (inSingleQuotes) {
                currentArgument.append(c);
                i++;
            } else if (c == ' ' || c == '\t') {
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

