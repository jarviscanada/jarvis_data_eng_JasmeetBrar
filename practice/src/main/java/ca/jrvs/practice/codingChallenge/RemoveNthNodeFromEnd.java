package ca.jrvs.practice.codingChallenge;

public class RemoveNthNodeFromEnd {

    public void removeNthNodeFromEnd(ListNode head, int n) {
        ListNode nthNode = head;
        ListNode endNode = head;

        for(int i = 0; i < n + 1; i++) {
            endNode = endNode.next;
        }

        while(endNode != null) {
            endNode = endNode.next;
            nthNode = nthNode.next;
        }

        assert nthNode != null;
        nthNode.next = nthNode.next.next;
    }
}
