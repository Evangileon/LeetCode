package org.leetcode;


import java.util.LinkedList;

public class PartitionList {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode partition(ListNode head, int x) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode p = dummy;
        ListNode preI = null;
        ListNode i = null;
        ListNode j = null;

        while (p.next != null) {
            if (p.next.val >= x) {
                // should move
                if (i == null) {
                    i = p.next;
                    preI = p;
                }
                j = p.next;
                p = p.next;
            } else {
                if (j != null) {
                    // move
                    preI.next = j.next;
                    // find proper position
                    ListNode barJ = j;
                    while (barJ.next != null && barJ.next.val < x) {
                        barJ = barJ.next;
                    }

                    ListNode tmp = barJ.next;
                    barJ.next = i;
                    j.next = tmp;
                    if (tmp == null) {
                        break;
                    }
                    p = j;
                } else {
                    p = p.next;
                }
            }
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        PartitionList solution = new PartitionList();
        int[] values = {1, 4, 3, 2, 5, 2};
        ListNode dummy = new ListNode(0);
        ListNode p = dummy;
        for (int val : values) {
            p.next = new ListNode(val);
            p = p.next;
        }


        ListNode newHead = solution.partition(dummy.next, 3);
        LinkedList<Integer> list = new LinkedList<>();

        while (newHead != null) {
            list.add(newHead.val);
            newHead = newHead.next;
        }

        System.out.println(list.toString());
    }
}
