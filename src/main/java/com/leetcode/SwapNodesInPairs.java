package com.leetcode;


import java.util.LinkedList;

public class SwapNodesInPairs {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode p = dummy;

        int count = 0;
        while (p.next != null) {
            count++;
            if ((count & 1) == 1) {
                if (p.next.next != null) {
                    ListNode tmp = p.next;
                    ListNode tmp2 = p.next.next.next;
                    p.next = p.next.next;
                    p.next.next = tmp;
                    tmp.next = tmp2;
                }
            }
            p = p.next;
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        SwapNodesInPairs solution = new SwapNodesInPairs();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);

        ListNode newHead = solution.swapPairs(head);
        LinkedList<Integer> list = new LinkedList<>();

        while (newHead != null) {
            list.add(newHead.val);
            newHead = newHead.next;
        }

        System.out.println(list.toString());
    }
}
