package ca.jrvs.practice.codingChallenge;

public class MiddleNode {

    public ListNode middleNode(ListNode head) {
        ListNode first = head, second = head;

        while(second != null && second.next != null) {
            second = second.next.next;
            first = first.next;
        }

        return first;
    }
}
