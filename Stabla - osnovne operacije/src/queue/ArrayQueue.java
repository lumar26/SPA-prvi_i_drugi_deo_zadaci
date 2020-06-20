package queue;

public class ArrayQueue<E> implements Queue{
	
	private E[] array;
	private int front;
	private int rear;
	private int count;
	
	public ArrayQueue(E[] array) {
		super();
		this.array = array;
		this.rear = 0;
		this.front = 0;
		this.count= 0;
	}
	

	@Override
	public boolean isEmpty() {
		if(count != 0) return false;
		return true;
	}

	@Override
	public void enqueue(Object e) {
		if(isEmpty()) {
			this.array[front] = (E) e;
			count++;
			return;
		}
		if(e != null && count < this.array.length) {
			this.rear  = (rear + 1) % this.array.length; 
			array[rear] = (E) e;
			count++;
		}
	}

	@Override
	public Object dequeue() {
		if(this.array[front] != null) {
			E temp = this.array[front];
			this.array[front] = null;
			front = (front + 1) % this.array.length;
			return temp;
		}
		return null;
	}

	@Override
	public Object peek() {
		if(this.array[front] != null) {
			return this.array[front];
		}
		return null;
	}

}
