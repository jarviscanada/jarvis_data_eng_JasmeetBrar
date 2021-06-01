package ca.jrvs.practice.codingChallenge;

public class LinkedListCycle {

    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;

        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if(slow == fast || fast != null && fast.next == slow) {
                return true;
            }
        }

        return false;
    }
}
