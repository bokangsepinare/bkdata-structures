/*
Name and Surname: 
Student Number: 
*/

public class ThreadedAVLNode<T extends Comparable<? super T>>
{
   /*
   TODO: You must implement a node class which would be appropriate to use with your trees.
   
   You may add methods and data fields. You may NOT alter the given class name or data fields.
   */
   public ThreadedAVLNode(T elem){
	   data=elem;
	   left=null;
	   hasThread=false;
	   right=null;
	   balanceFactor=0;
	   Lheight=0;
	   Rheight=0;
   }
   public void setBal(int bal){
	   balanceFactor=bal;
   } 
   public int getBal(){
	   return balanceFactor;
   }
   public void setLeft_Right(ThreadAVLNode<T> Left, ThreadedAVLNode <T> Right){
	   left=Left;
	   right=Right;
   }
   public ThreadedAVLNode<T> getLeft(){
	   return left;
   }
   public ThreadedAVLNode<T> getRight(){
	   return right;
   }
   public void HasT(){
	   hasThread=true;
   }
   public boolean getH(){
	   return hasThread;
   }
   public T getData(){
	   return data;
   }
   protected T data;                      // stored data
   protected int balanceFactor;           // balance factor (follow the definition in the textbook and slides exactly)
   protected ThreadedAVLNode<T> left;     // right child
   protected ThreadedAVLNode<T> right;    // left child
   protected boolean hasThread;   // flag indicating whether the right pointer is a thread
   protected int Lheight;
   protected int Rheight;
}
