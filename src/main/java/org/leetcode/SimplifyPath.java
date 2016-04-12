package org.leetcode;


import java.util.LinkedList;

public class SimplifyPath {
    public String simplifyPath(String path) {
        LinkedList<String> stack = new LinkedList<>();

        int start = -1;
        //int end = 0;

        int i;
        for (i = 0; i < path.length(); i++) {
            if (path.charAt(i) == '/') {
                if (start < 0) {
                    start = i;
                    continue;
                }

                if (start == (i - 1)) { // redundant slashes
                    start = i;
                    continue;
                }

                String sub = path.substring(start + 1, i);
                start = i;
                if (sub.equals(".")) {
                    continue;
                }

                if (sub.equals("..")) {
                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                    continue;
                }

                stack.push(sub);
            }
        }

        if (start != (i - 1)) { // redundant slashes
            String sub = path.substring(start + 1, i);
            if (!sub.equals(".") && !sub.equals("..")) {
                stack.push(sub);
            }
        }

        if (stack.isEmpty()) {
            return "/";
        }

        StringBuffer buffer = new StringBuffer();
        while (!stack.isEmpty()) {
            buffer.append("/");
            buffer.append(stack.pop());
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        SimplifyPath solution = new SimplifyPath();
        String path = "/.../";
        System.out.println(solution.simplifyPath(path));
    }
}
