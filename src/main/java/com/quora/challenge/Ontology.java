package com.quora.challenge;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.min;
import static java.lang.Math.max;

public class Ontology {

    private static class Node {
        int val;
        int left, right;

        Node(int val, int l, int r) {
            this.val = val;
            this.left = l;
            this.right = r;
        }
    }

    static class Pair implements Comparable<Pair> {
        String body;
        int topicId;

        Pair(String body, int topicId) {
            this.body = body;
            this.topicId = topicId;
        }

        @Override
        public int compareTo(Pair o) {
            if (o == null) {
                return 1;
            }

            int compare = this.body.compareTo(o.body);
            if (compare != 0) {
                return compare;
            }

            return this.topicId - o.topicId;
        }
    }

    private class SegmentTree {
        List<Integer> roots;
        List<Node> nodes;

        // msg is ordered set
        SegmentTree(int N, Iterable<Pair> questions) {
            roots = new ArrayList<>();
            nodes = new ArrayList<>();
            roots.add(build(0, N - 1));

            int i = 0;
            for (Pair pair : questions) {
                roots.add(modify(roots.get(i), 0, N - 1, pair.topicId, 1));
                i++;
            }
        }

        int generateNode(int val, int left, int right) {
            Node node = new Node(val, left, right);
            nodes.add(node);
            return nodes.size() - 1;
        }

        int getVal(int pos) {
            if (pos == -1) {
                return 0;
            }
            return nodes.get(pos).val;
        }

        int build(int l, int r) {
            if (l == r) {
                return generateNode(0, -1, -1);
            }
            int m = (l + r) / 2;
            int left = build(l, m);
            int right = build(m + 1, r);
            int val = getVal(left) + getVal(right);

            return generateNode(val, left, right);
        }

        int modify(int at, int l, int r, int idx, int val) {
            if (l == r) {
                return generateNode(nodes.get(at).val + val, -1, -1);
            }
            int m = (l + r) / 2;
            int left, right;
            if (idx <= m) {
                left = modify(nodes.get(at).left, l, m, idx, val);
                right = nodes.get(at).right;
            } else {
                left = nodes.get(at).left;
                right = modify(nodes.get(at).right, m + 1, r, idx, val);
            }
            int s = getVal(left) + getVal(right);
            return generateNode(s, left, right);
        }

        int sum(int at, int l, int r, int ql, int qr) {
            if (ql > qr || at == -1) {
                return 0;
            }
            if (l == ql && r == qr) {
                return getVal(at);
            }
            int m = (l + r) / 2;
            int s1 = sum(nodes.get(at).left, l, m, ql, min(qr, m));
            int s2 = sum(nodes.get(at).right, m + 1, r, max(m + 1, ql), qr);
            return s1 + s2;
        }
    }

    private static class Graph {
        List<List<Integer>> g;
        Map<String, Integer> ids;

        List<Integer> tin;
        List<Integer> tout;

        private int tm = -1;

        Graph(int N) {
            ids = new HashMap<>(N);
            g = new ArrayList<>(N + 5);
            tin = new ArrayList<>(N);
            tout = new ArrayList<>(N);
        }


        Graph parse(String treeStr) {
            StringTokenizer st = new StringTokenizer(treeStr, " ");
            String token;
            tm = -1;

            Deque<Integer> stack = new ArrayDeque<>();
            int parent = -1;
            int prev = -1;
            int index = 0;

            while (st.hasMoreTokens()) {
                token = st.nextToken();

                switch (token) {
                    case "(":
                        if (parent != -1) {
                            stack.push(parent);
                        }

                        if (prev == -1) {
                            throw new RuntimeException("Tree serialization invalid");
                        }

                        parent = prev;
                        prev = -1;

                        break;
                    case ")":
                        if (!stack.isEmpty()) {
                            parent = stack.pop();
                        }
                        prev = -1;

                        break;
                    default:
                        int id;
                        if (!this.ids.containsKey(token)) {
                            id = index++;
                            this.ids.put(token, id);
                            this.g.add(new ArrayList<Integer>());
                        } else {
                            id = this.ids.get(token);
                        }

                        if (parent != -1) {
                            this.g.get(parent).add(id);
                            this.g.get(id).add(parent);
                        }
                        prev = id;
                        break;
                }
            }

            buildRangeDFS(0, -1);

            return this;
        }

        private void setList(List<Integer> list, int index, int val) {
            while (list.size() <= index) {
                list.add(0);
            }
            list.set(index, val);
        }

        void buildRangeDFS(int at, int from) {
            setList(this.tin, at, (++tm));

            for (int to : this.g.get(at)) {
                if (to != from) {
                    buildRangeDFS(to, at);
                }
            }

            setList(this.tout, at, tm);
        }
    }

    public <T extends Comparable<T>> int binarySearchFloor(List<T> list, T target) {
        int l = 0;
        int r = list.size() - 1;
        int m;

        if (list.isEmpty()) {
            return -1;
        }

        if (target.compareTo(list.get(list.size() - 1)) > 0) {
            return list.size();
        }

        while (l <= r) {
            m = l + (r - l) / 2;
            T mid = list.get(m);

            if (target.compareTo(mid) > 0) {
                l = m + 1;
            } else if (target.compareTo(mid) < 0) {
                if (m == 0) {
                    return 0;
                }
                if (list.get(m - 1).compareTo(target) < 0) {
                    return m;
                }
                r = m;
            } else {
                return m;
            }
        }

        return 0;
    }

    private Graph graph;
    private List<Pair> questions;
    private SegmentTree segmentTree;

    public void buildSegmentTree(Graph graph, List<Pair> questions) {
        Collections.sort(questions);
        this.graph = graph;
        this.questions = questions;

        segmentTree = new SegmentTree(graph.ids.size(), questions);
    }

    public int queryCount(String topic, String body) {
        int cid = graph.ids.get(topic);
        int idx = binarySearchFloor(questions, new Pair(body, -1));
        int idx2 = binarySearchFloor(questions, new Pair(body + Character.MAX_VALUE, -1));

        if (idx2 < idx) {
            return 0;
        }

        int cnt1 = segmentTree.sum(segmentTree.roots.get(idx2), 0, graph.ids.size() - 1, graph.tin.get(cid), graph.tout.get(cid));
        int cnt2 = segmentTree.sum(segmentTree.roots.get(idx), 0, graph.ids.size() - 1, graph.tin.get(cid), graph.tout.get(cid));

        return cnt1 - cnt2;
    }


    public static void main(String[] args) throws IOException {
        int N, M, K;

        Scanner file = new Scanner(System.in);
        BufferedReader in = new BufferedReader(new FileReader(file.nextLine()));

        //Parse topics
        String tmp;
        while ((tmp = in.readLine()) == null || tmp.length() == 0) {
        }
        N = Integer.parseInt(tmp);
        String tree;
        while ((tree = in.readLine()) == null || tree.length() == 0) {
        }

        Ontology ontology = new Ontology();

        Graph graph = new Graph(N);
        graph.parse(tree);

        // Parse queries
        while ((tmp = in.readLine()) == null || tmp.length() == 0) {
        }
        M = Integer.parseInt(tmp);
        List<Pair> questions = new ArrayList<>();
        String line;

        for (int i = 0; i < M; i++) {
            while ((line = in.readLine()) == null || line.length() == 0) {
            }
            String category = line.substring(0, line.indexOf(':'));
            String body = line.substring(line.indexOf(':') + 2);
            questions.add(new Pair(body, graph.ids.get(category)));
        }

        ontology.buildSegmentTree(graph, questions);

        //use segment tree to record query count -- segmented by topic ids
        //use persistent data structure(tree) to record the count(up to current version)
        // -- each msg is a new version to update count

        // put queries to persistent tree
        while ((tmp = in.readLine()) == null || tmp.length() == 0) {
        }
        K = Integer.parseInt(tmp);

        for (int i = 0; i < K; i++) {
            while ((line = in.readLine()) == null || line.length() == 0) {
            }

            String topic = line.substring(0, line.indexOf(' '));
            String body = line.substring(line.indexOf(' ') + 1);
            System.out.println(ontology.queryCount(topic, body));
        }
    }
}
