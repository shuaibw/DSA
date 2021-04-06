import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;

class Bracket {
    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean Match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;
    int position;
}

class check_brackets {
    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();

        Stack<Bracket> stack = new Stack<>();
        for (int position = 0; position < text.length(); ++position) {
            char cur = text.charAt(position);

            if (cur == '(' || cur == '[' || cur == '{') {
                stack.push(new Bracket(cur, position));
            }

            if (cur == ')' || cur == ']' || cur == '}') {
                if (stack.isEmpty()) {
                    System.out.println(position + 1);
                    return;
                }
                Bracket popped = stack.pop();
                if (!popped.Match(cur)) {
                    System.out.println(position + 1);
                    return;
                }
            }
        }
        if (!stack.isEmpty()) {
            System.out.println(stack.firstElement().position + 1);
        } else {
            System.out.println("Success");
        }
        // Printing answer, write your code here
    }
}
