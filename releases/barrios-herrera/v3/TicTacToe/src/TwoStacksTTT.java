
public class TwoStacksTTT {
	private int[] stack;
	private int top1;
	private int top2;
	private int capacity;

	//constructor. Creates stack of given size
	public TwoStacksTTT(int size) {
		stack = new int[10];
		top1 = -1;
		top2 = stack.length;
		capacity = stack.length;
	}
	
	//pushesh element to the stack 1. (Left one)
	public void push1(int x) {
		if (top2 == top1 + 1) {
			System.out.println("Stack overflow");
		} else {
			top1++;
			stack[top1] = x;
		}
	}
	
	//pushes element to the stack 2. (Right one)
	public void push2(int y) {
		if (top2 == top1 + 1) {
			System.out.println("Stack overflow");
		} else {
			top2--;
			stack[top2] = y;
		}
	}
	
	//gives back stack 1 size
	public int size1() {
		return top1;
	}
	
	//gives back stack 2 size
	public int size2() {
		return stack.length - top2;
	}
	
	//returns last element from stack 1 and deletes it
	public int pop1() {

		int x = stack[top1];
		top1--;
		return x;

	}
	
	//returns last element from stack 2 and deletes it
	public int pop2() {
		int y = stack[top2];
		top2++;
		return y;
	}
	
	//returns last element from stack 1
	public int peek1() {
		return stack[top1];
	}

	//returns last element from stack 2
	public int peek2() {
		return stack[top2];
	}

	// checks if stack 1 is empty
	public boolean isEmpty1() {
		return top1 < 0;
	}

	// checks if stack 2 is empty
	public boolean isEmpty2() {
		return top2 >= capacity;
	}

}
