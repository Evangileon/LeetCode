package com.quora.challenge;


import java.io.*;
import java.util.*;

public class Ontology {

    private static final int LN = 19;

    private Graph graph;
    private List<Pair> questions;
    private SegmentTree segmentTree;

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

        // questions is sorted
        SegmentTree(int N, Iterable<Pair> questions) {
            roots = new ArrayList<>(N);
            nodes = new ArrayList<>(N);
            roots.add(build(0, N - 1));

            int i = 0;
            for (Pair pair : questions) {
                roots.add(update(roots.get(i), 0, N - 1, pair.topicId, 1));
                i++;
            }
        }

        int newNode(int val, int left, int right) {
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
                return newNode(0, -1, -1);
            }
            int m = l + (r - l) / 2;
            int left = build(l, m);
            int right = build(m + 1, r);
            int val = getVal(left) + getVal(right);

            return newNode(val, left, right);
        }

        int update(int at, int l, int r, int idx, int val) {
            if (l == r) {
                return newNode(nodes.get(at).val + val, -1, -1);
            }
            int m = l + (r - l) / 2;
            int left, right;
            if (idx <= m) {
                left = update(nodes.get(at).left, l, m, idx, val);
                right = nodes.get(at).right;
            } else {
                left = nodes.get(at).left;
                right = update(nodes.get(at).right, m + 1, r, idx, val);
            }
            int s = getVal(left) + getVal(right);
            return newNode(s, left, right);
        }

        int pa(int u, int v) {
            return graph.adjs.get(u).get(v);
        }

        int LCA(int u, int v) {
//            if(depth[u] < depth[v])
//                return LCA(v, u);

            //int diff = depth[u] - depth[v];

            int diff = 1;

            for (int i = 0; i < LN; i++) {
                if (((diff >> i) & 1) == 1) {
                    u = pa(u, i);
                }
            }

            if (u != v) {
                for (int i = LN - 1; i >= 0; i--) {
                    if (pa(u, i) != pa(v, i)) {
                        u = pa(u, i);
                        v = pa(v, i);
                    }
                }
                u = pa(u, 0);
            }

            return u;
        }

        int querySum(int at, int l, int r, int ql, int qr) {
            if (ql > qr || at == -1) {
                return 0;
            }
            if (l == ql && r == qr) {
                return getVal(at);
            }
            int m = l + (r - l) / 2;
            int s1 = querySum(nodes.get(at).left, l, m, ql, Math.min(qr, m));
            int s2 = querySum(nodes.get(at).right, m + 1, r, Math.max(m + 1, ql), qr);
            return s1 + s2;
        }

        //int querySum(int start, )
    }

    private static class Graph {
        private List<List<Integer>> adjs;
        private Map<String, Integer> ids;

        private List<Integer> startTime;
        private List<Integer> endTime;

        private int t = -1;

        Graph(int N) {
            ids = new HashMap<>(N);
            adjs = new ArrayList<>(N + 5);
            startTime = new ArrayList<>(N);
            endTime = new ArrayList<>(N);
        }


        Graph parse(String treeStr) {
            StringTokenizer st = new StringTokenizer(treeStr, " ");
            String token;
            t = -1;

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
                            this.adjs.add(new ArrayList<Integer>());
                        } else {
                            id = this.ids.get(token);
                        }

                        if (parent != -1) {
                            this.adjs.get(parent).add(id);
                            this.adjs.get(id).add(parent);
                        }
                        prev = id;
                        break;
                }
            }

            dfs(0, -1);

            return this;
        }

        private void setList(List<Integer> list, int index, int val) {
            while (list.size() <= index) {
                list.add(0);
            }
            list.set(index, val);
        }

        private void dfs(int at, int from) {
            setList(this.startTime, at, (++t));

            for (int to : this.adjs.get(at)) {
                if (to != from) {
                    dfs(to, at);
                }
            }

            setList(this.endTime, at, t);
        }
    }

    // return betweem 0, size - 1
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

        int cnt1 = segmentTree.querySum(segmentTree.roots.get(idx2), 0, graph.ids.size() - 1,
                graph.startTime.get(cid), graph.endTime.get(cid));
        int cnt2 = segmentTree.querySum(segmentTree.roots.get(idx), 0, graph.ids.size() - 1,
                graph.startTime.get(cid), graph.endTime.get(cid));

        return cnt1 - cnt2;
    }

    public static String nextLine(BufferedReader in) {
        String tmp = null;
        try {
            while ((tmp = in.readLine()) == null || tmp.length() == 0) {
            } // handle \r\n
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmp;
    }


    public static void main(String[] args) {
        int N, M, K;

        BufferedReader in = null;
        in = new BufferedReader(new InputStreamReader(System.in));

        //Parse topics
        String tmp;
        tmp = nextLine(in);
        N = Integer.parseInt(tmp);
        String tree;
        tree = nextLine(in);

        Ontology ontology = new Ontology();

        Graph graph = new Graph(N);
        graph.parse(tree);

        // Parse questions
        tmp = nextLine(in);
        M = Integer.parseInt(tmp);
        List<Pair> questions = new ArrayList<>();
        String line;

        for (int i = 0; i < M; i++) {
            line = nextLine(in);
            String topic = line.substring(0, line.indexOf(':'));
            String body = line.substring(line.indexOf(':') + 2);
            questions.add(new Pair(body, graph.ids.get(topic)));
        }

        ontology.buildSegmentTree(graph, questions);

        tmp = nextLine(in);
        K = Integer.parseInt(tmp);

        for (int i = 0; i < K; i++) {
            line = nextLine(in);

            String topic = line.substring(0, line.indexOf(' '));
            String body = line.substring(line.indexOf(' ') + 1);
            System.out.println(ontology.queryCount(topic, body));
        }
    }
}
