package cs601.concurrent;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

/**
 * A reentrant read/write lock that allows: 
 * 1) Multiple readers (when there is no writer).
 * 2) One writer (when nobody else is writing or reading). 
 * 3) A writer is allowed to acquire a read lock while holding the write lock. 
 * The assignment is based on the assignment of Prof. Rollins (original author).
 */
public class ReentrantReadWriteLock {

	// TODO: Add instance variables : you need to keep track of the read lock holders and the write lock holders.
	// We should be able to find the number of read locks and the number of write locks 
	// a thread with the given threadId is holding
	private Map<Long,Integer> writers;
	private Map<Long,Integer> readers;


	/**
	 * Constructor for ReentrantReadWriteLock
	 */
	public ReentrantReadWriteLock() {
		writers=new HashMap<>();
		readers=new HashMap<>();
	}

	/**
	 * Returns true if the current thread holds a read lock.
	 * 
	 * @return
	 */
	public synchronized boolean isReadLockHeldByCurrentThread() {
		if(readers.containsKey(Thread.currentThread().getId())) return true;
		else return false;
	}

	/**
	 * Returns true if the current thread holds a write lock.
	 * 
	 * @return
	 */
	public synchronized boolean isWriteLockHeldByCurrentThread() {
		if(writers.containsKey(Thread.currentThread().getId())) return true;
		else return false;
	}

	/**
	 * Non-blocking method that tries to acquire the read lock. Returns true
	 * if successful.
	 * 
	 * @return
	 */
	public synchronized boolean tryAcquiringReadLock() {
		long currentThreadId=Thread.currentThread().getId();
		if(writers.isEmpty()){
			IncrementReader(currentThreadId);
			return true;
		}
		else if(isWriteLockHeldByCurrentThread()){
			IncrementReader(currentThreadId);
			return true;
		}
		else return false;
	}

	/**
	 * Non-blocking method that tries to acquire the write lock. Returns true
	 * if successful.
	 * 
	 * @return
	 */
	public synchronized boolean tryAcquiringWriteLock() {
		long currentThreadId=Thread.currentThread().getId();
		if(writers.isEmpty()&&readers.isEmpty()){
			IncrementWriter(currentThreadId);
			return true;
		}
		else if(!writers.isEmpty()){
			if(isWriteLockHeldByCurrentThread()){
				IncrementWriter(currentThreadId);
				return true;
			}
			else return false;
		}
		else return false;
	}

	/**
	 * Blocking method - calls tryAcquiringReadLock and returns only when the read lock has been
	 * acquired, otherwise waits.
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void lockRead() {
		while (!tryAcquiringReadLock()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//IncrementReader(Thread.currentThread().getId());
	}

	/**
	 * Releases the read lock held by the current thread. 
	 */
	public synchronized void unlockRead() {
		DecrementReader(Thread.currentThread().getId());
		notify();
	}

	/**
	 * Blocking method that calls tryAcquiringWriteLock and returns only when the write lock has been
	 * acquired, otherwise waits.
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void lockWrite() {
		while(!tryAcquiringWriteLock()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//IncrementWriter(Thread.currentThread().getId());
		
	}

	/**
	 * Releases the write lock held by the current thread. 
	 */

	public synchronized void unlockWrite() {
		DecrementWriter(Thread.currentThread().getId());
		notify();
	}

	private synchronized void IncrementReader(long threadId){
		int cnt;
		if(readers.containsKey(threadId)){
			cnt=readers.get(threadId);
			cnt++;
			readers.replace(threadId,cnt);
		}
		else{
			readers.put(threadId,1);
		}
	}
	private synchronized void DecrementReader(long threadId){
		int cnt;
		if(readers.containsKey(threadId)){
			cnt=readers.get(threadId);
			if(cnt==1){
				readers.remove(threadId);
			}
			else{
				cnt--;
				readers.replace(threadId,cnt);
			}
		}
	}
	private synchronized void IncrementWriter(long threadId){
		int cnt;
		if(writers.containsKey(threadId)){
			cnt=writers.get(threadId);
			cnt++;
			writers.replace(threadId,cnt);
		}
		else{
			writers.put(threadId,1);
		}
	}
	private synchronized void DecrementWriter(long threadId){
		int cnt;
		if(writers.containsKey(threadId)){
			cnt=writers.get(threadId);
			if(cnt==1){
				writers.remove(threadId);
			}
			else{
				cnt--;
				writers.replace(threadId,cnt);
			}
		}
	}
}
