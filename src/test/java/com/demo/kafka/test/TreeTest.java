package com.demo.kafka.test;

public class TreeTest {

    public static void main(String[] args) {
        TreeNode p3 = new TreeNode(3);
        TreeNode p2 = new TreeNode(2, p3, null);
        TreeNode p = new TreeNode(1, null, p2);

        TreeNode q3 = new TreeNode(3);
        TreeNode q2 = new TreeNode(2, null, q3);
        TreeNode q = new TreeNode(1, null, q2);

        boolean isSame = TreeTest.isSameTree(p, q);
        System.out.println(isSame);
    }

    public static boolean isSameTree(TreeNode p, TreeNode q) {
        return equals(p, q);
    }

    public static boolean equals(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null) return false;
        if (q == null) return false;
        return p.val == q.val
                && equals(p.left, q.left)
                &&  equals(p.right, q.right);
    }
}
