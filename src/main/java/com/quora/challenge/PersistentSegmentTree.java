package com.quora.challenge;


import java.util.List;

public class PersistentSegmentTree {
    List<Node> root;
//    int v[N], pa[N][LN], RM[N], depth[N], maxi = 0;
//    vector <int> adj[N];
//    map <int, int> M;

    class Node {
        int count;
        Node left, right;

        public Node(int count, Node left, Node right) {
            this.count = count;
            this.left = left;
            this.right = right;
        }

        Node insert(int l, int r, int w) {
            if (l <= w && w < r) {
                // With in the range, we need a new node
                if (l + 1 == r) {
                    return new Node(count + 1, null, null);
                }

                int m = (l + r) >> 1;

                return new Node(count + 1, left.insert(l, m, w), right.insert(m, r, w));
            }

            // Out of range, we can use previous tree node.
            return this;
        }

        void dfs(int cur, int prev)
        {
//            pa[cur][0] = prev;
//            depth[cur] = (prev == -1 ? 0 : depth[prev] + 1);
//
//            // Construct the segment tree for this node using parent segment tree
//            // This is the formula we derived
//            root[cur] = ( prev == -1 ? null : root[prev] )->insert( 0, maxi, M[v[cur]] );
//
//            rep(i, adj[cur].sz)
//            if(adj[cur][i] != prev)
//                dfs(adj[cur][i], cur);
        }
    }
}
