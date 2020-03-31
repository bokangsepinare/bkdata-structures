/*
Name and Surname: 
Student Number: 
*/

public class ThreadedAVLTree<T extends Comparable<? super T>>
{
   /*
   TODO: You must complete each of the methods in this class to create your own threaded AVL tree.
   Note that the AVL tree is single-threaded, as described in the textbook and slides.
   
   You may add any additional methods or data fields that you might need to accomplish your task.
   You may NOT alter the given class name, data fields, or method signatures.
   */
   
   private ThreadedAVLNode<T> root;       // the root node of the tree
   public int size;
   
   public ThreadedAVLTree()
   {
      /*
      The default constructor
      */
	   root=null;
	   size=0;
	   
   }
   
   public ThreadedAVLTree(ThreadedAVLTree<T> other)
   {
      /*
      The copy constructor
      */
	   root=other.clone();
   }
   
   public ThreadedAVLTree<T> clone()
   {
      /*
      The clone method should return a copy/clone of this tree.
      */
      if(root!=null){
	      ThreadedAVLNode<T> newR=new ThreadedAVLNode(root.data);
	      clone(root,newR);
	      newR.size=size;
	      return newR;
      }
      return null;
   }
   
   public ThreadedAVLNode<T> getRoot()
   {
      /*
      Return the root of the tree.
      */
      
      return root;
   }
   
   public int getNumberOfNodes()
   {
      /*
      This method should count and return the number of nodes currently in the tree.
      */
      
      return size;
   }
   
   public int getHeight()
   {
      /*
      This method should return the height of the tree. The height of an empty tree is 0; the
      height of a tree with nothing but the root is 1.
      */
      if(root==null)
	return 0;
      return max(root.Lheight,root.Rheight);
   }
   
   public boolean insert(T element)
   {
      /*
      The element passed in as a parameter should be inserted into the tree. Duplicate values are
      not allowed in the tree. Threads must be updated accordingly, as necessary. If the insertion
      operation is successful, the method should return true. If the insertion operation is
      unsuccessful for any reason, the method should return false.
      
      NB: Do not throw any exceptions.
      */
      if(contains(element))
	return false;
      size++;
      if(root==null){
	      root=new ThreadedAVLNode(element);
	        return true;
      }
	insert(element,null,root);
	return true;
   }
   
   public boolean delete(T element)
   {
      /*
      The element passed in as a parameter should be deleted from this tree. Threads must be
      updated accordingly, as necessary. If the deletion operation was successful, the method
      should return true. If the deletion operation is unsuccessful for any reason (e.g. the
      requested element is not found in the tree), the method should return false.
      
      NB: Do not throw any exceptions.
      */
      if(!contains(element))
		return false;
      size--;
      ThreadedAVLNode<T> node=root,farRight=null,prev=null;
      while(node!=null){
	      if(element.compareTo(node.data)<0){
		      prev=node;
		      node=node.left;
	      }
	      else if(element.compareTo(node.data)>0){
		      prev=node;
		      node=node.right;
	      }
	      else 
			break;
      }
      //when the node being deleted  is the root
	if(prev==null){
		if(node.left==null)
			root=root.right;
		else if(FarRight(node.left)==node.left){
			root=node.left;
			root.left=node.left.left;
			node.left.right=node.right;
		}
		else{
			farRight=FarRight(node.left);
			root=farRight;
			root.right=node.right;
			root.left=node.left;
		}
			
	}
	//when the node being deleted is not the root
	else{
		if(node.left==null){
			if(prev.right==node)
				prev.right=node.right;
			else if(prev.left==node)
				prev.left=node.right;
		}
		else if(FarRight(node.left)==node.left){
			if(prev.right==node){
				prev.right=node.left;
				prev.right.right=node.right;
			}
			if(prev.left==node){
				prev.left=node.left;
				prev.left.right=node.right;
			}
		}
		else{
			farRight=farRight(node.left);
			if(prev.left==node){
				prev.left=farRight;
				farRight.left=node.left;
				farRight.right=node.right;
			}
		}
	}
	return true;
   }
   
   public boolean contains(T element)
   {
      /*
      This method searches for the element passed in as a parameter. If the element is found, true
      should be returned. If the element is not in the tree, the method should return false.
      */
	if(root==null)
		return false;
	ThreadedAVLNode<T> node=root;
	while(node!=null){
		if(element.compareTo(node.data)<0){
			node=node.left
			
		}
		else if(element.compareTo(node.data)>0){
			if(node.hasThread)
				break;
			node=node.right;
		}
		else
			return true;
	}
      return false;
   }
   
   public String inorder()
   {
      /*
      This method must return a string representation of the elements in the tree visited during an
      inorder traversal. This method must not be recursive. Instead, the threads must be utilised
      to perform a depth-first inorder traversal.
      
      Note that there are no spaces in the string, and the elements are comma-separated. Note that
      no comma appears at the end of the string.
      
      If the tree looks as follows:
      
          C
         / \
        B   E
       /   / \
      A   D   F
      
      The following string must be returned:
      
      A,B,C,D,E,F
      */
	if(root!=null){
		String r="";
		ThreadedAVLNode<T> prev,node=root;
		while(node.left!=null)
			node=node.left;
		while(node!=null){
			r+=node.data.toString();
			prev=node;
			node=node.right;
			r+=node.data.toString();
			if(node!=null)
			r+=",";
			if(node!=null &&  !node.hasThread)
				while(node.left!=null)
					node=node.left;
			}
		}
      
      return "";
   }
   
   public String inorderDetailed()
   {
      /*
      This method must return a string representation of the elements in the tree visited during an
      inorder traversal. This method must not be recursive. Instead, the threads must be utilised
      to perform a depth-first inorder traversal.
      
      Note that there are no spaces in the string, and the elements are comma-separated.
      Additionally, whenever a thread is followed during the traversal, a pipe symbol should be
      printed instead of a comma. Note that no comma or pipe symbol appears at the end of the
      string. Also note that if multiple threads are followed directly after one another, multiple
      pipe symbols will be printed next to each other.
      
      If the tree looks as follows:
      
          C
         / \
        B   E
       /   / \
      A   D   F
      
      In this tree, there is a thread linking the right branch of node A to node B, a thread
      linking the right branch of node B to node C, and a thread linking the right branch of node D
      to node E. The following string must therefore be returned:
      
      A|B|C,D|E,F
      */
      
      return "";
   }
   
   public String preorder()
   {
      /*
      This method must return a string representation of the elements in the tree visited during a
      preorder traversal. This method must not be recursive. Instead, the threads must be utilised
      to perform a depth-first preorder traversal. See the last paragraph on page 240 of the
      textbook for more detail on this procedure.
      
      Note that there are no spaces in the string, and the elements are comma-separated. Note that
      no comma appears at the end of the string.
      
      If the tree looks as follows:
      
          C
         / \
        B   E
       /   / \
      A   D   F
      
      The following string must be returned:
      
      C,B,A,E,D,F
      */
      
      return "";
   }
   
   public String preorderDetailed()
   {
      /*
      This method must return a string representation of the elements in the tree visited during a
      preorder traversal. This method must not be recursive. Instead, the threads must be utilised
      to perform a depth-first preorder traversal. See the last paragraph on page 240 of the
      textbook for more detail on this procedure.
      
      Note that there are no spaces in the string, and the elements are comma-separated.
      Additionally, whenever a thread is followed during the traversal, a pipe symbol should be
      printed instead of a comma. Note that no comma or pipe symbol appears at the end of the
      string. Also note that if multiple threads are followed directly after one another, multiple
      pipe symbols will be printed next to each other.
      
      If the tree looks as follows:
      
          C
         / \
        B   E
       /   / \
      A   D   F
      
      In this tree, there is a thread linking the right branch of node A to node B, a thread
      linking the right branch of node B to node C, and a thread linking the right branch of node D
      to node E. The following string must therefore be returned:
      
      C,B,A||E,D|F
      
      Note that two pipe symbols are printed between A and E, because the thread linking the right
      child of node A to node B is followed, B is not printed because it has already been visited,
      and the thread linking the right child of node B to node C is followed.
      */
      
      return "";
   }

private ThreadedAVLNode<T> FarRight(ThreadedAVLNode<T> node){
	ThreadedAVLNode <T> prev=null;
	if(node.hasThread==
	while(!node.hasThread){
		prev=node;
		node=node.right
	}
	if(prev!=null){
		if(prev.right==node){
			prev.balanceFactor--;
			if(node.left!=null){
				prev.right=node.left;
				prev.right.hasThread=true;
				prev.right.right=node.right; //inherit the thread
			}
			else prev.right=node.right; //inherit the thread
		}
		else if(prev.left==node){
			prev.balanceFactor++;
			if(node.left!=null){
				prev.left=node.left;
				prev.left.hasThread=true;
				prev.left.right=node.right;//inherit the thread from the far right node
			}
			else prev.left=node.right;
			
		}
			
	}
	return node;
}
private boolean insert(T elem,ThreadedAVLNode<T> node){
	if(elem.compareTo(node.data)<0){
		if(node.left==null){
			node.Lheight++;s
			node.left=new ThreadedAVLNode(elem);
			/*node.left.hasThread=true;
			node.left.right=node;*/
			if(--node.balanceFactor==-1){//if it changes the balance factors of the items above
				return true;
			}
			else return false;
		}
		else{
			if(insert(elem,node.left)){//if it is necessary to change the balance factors
				if(--node.balanceFactor<-1){
					char [] ord=order(node);
					if(ord[0]=='l' && ord[1]=='l')//homogenous
						rotateR(node);
					if(ord[0]=='l' &&ord[1]=='r'){
						rotateL(node.left);
						rotateR(node);//these might need to be changed if I change the way I do rotations  
					}
					return false; // now everything is in order no need to change the balance factors
				}
				return true;//continue changing balancefactors as long as one does not find the balance factor that needs to be fixed 
			}
			return false;
		}
	}
	if(elem.compareTo(node.data)>0){//
		if(node.right==null){
			node.Rheight++;
			ThreadedAVLNode <T> right=node.right;
			node.right=new ThreadedAVLNode(elem);
			/*if(node.hasThread){
				node.right.right=right;// inherit the thread
				node.right.hasThread=true;
			}*/
			if(++node.balanceFactor==1){
				return true;// meaning  that it is gonnna affect the balancefactors above the current node
			}
			else return false; //else no need to change the balance factors above this 
		}
		else{// if this  is not the last node to reach before insertion

			if(insert(elem,node.right)){
				if(++node.balanceFactor>1){
					if(ord[0]=='r' && ord[1]=='r')//homogenous
						rotateL(node);
					if(ord[0]=='r' &&ord[1]=='l'){
						rotateR(node.right);
						rotateL(node);
					}
					return false; // now everything is in order no need to change the balance factors
				}
				node.Rheight++;
				return true;//continue changing balancefactors as long as one does not find the balance factor that needs to be fixed
			}
			return false;
		}
	}
}
private char order(ThreadedAVLNode<T> node, T elem){
	char[] ord=new char[2];
	int count=0;
	while(count<2){
		if(elem.compareTo(node.data)<0 ||elem.equals(node.data)){
			node=node.left;
			ord[count]='l';
		}
		else if(elem.compareTo(node.data)>0 || elem.equals(node.data)){
			node=node.right;
			ord[count]='r';
		}
		count++;
		}
		return ord;
}
private void clone(ThreadedAVLNode<T> old, ThreadedAVLNode<T> newt){
	if(old!=null){
	if(old.right!=null&& !old.hasThread){
		newt.right=new ThreadedAVLNode(old.right.data);
		clone(old.right,newt.right);
	}
	if(old.left!=null){
		newt.left=new ThreadedAVLNode(old.left.data);
		clone(old.left,newt.left);
	}
		
}
private void rotateL(ThreadedAVLNode<T> search){
	ThreadedAVLNode<T> gr=null,node=root,child=root.right;
	int swaph;
	if(root==search){
		root.right=child.left;
		root.Rheight=child.Lheight;
		swaph=max(root.Rheight,root.Lheight)+1;
		child.left=root;
		child.Lheight=swaph;
		root=child;
	}
	while(node!=null){
		if(node.data.compareTo(search.data)>0){
			gr=node;
			node=node.left;
		}
		else if(node.data.compareTo(search.data)<0){
			gr=node;
			node=node.right;
		}
		else{
			child=node.right;
			break;
		}
	}
	//node is the pivot 
	node.right=child.left;
	node.Rheight=child.Lheight;
	swaph=max(node.Rheight,node.Lheight)+1;
	if(child.left==null){//the part for the threads
		node.hasThread=true;
		node.right=child;
	}
	child.left=node;
	child.Lheight=swaph;
	if(gr.right==node){
		gr.right=child;
		gr.Rheight=max(child.Rheight,child.Lheight)+1;
		gr.balanceFactor=gr.Rheight-gr.Lheight;
	}
	else if(gr.left==node){
		gr.left=child;
		gr.Lheight=max(child.Rheight,child.Lheight)+1;
		gr.balanceFactor=gr.Rheight-gr.Lheight;
	}
}
private void rotateR(ThreadedAVLNode<T> search){
	ThreadedAVLNode<T> gr=null,node=root,child=root.left;
	int swaph;
	if(root==search){
		root.left=child.right;
		root.Lheight=child.Rheight;
		swaph=max(root.Lheight,root.Rheight)+1;
		root.balanceFactor=root.Rheight-root.Lheight;
		child.right=root;
		child.Rheight=swaph;
		child.balanceFactor=child.Rheight-child.Lheight;
		root=child;
	}
	while(node!=null){//the contradcition of this statement will never be reached
		if(node.data.compareTO(search.data)>0){
			gr=node;
			node=node.left;
		}
		else if (node.data.compareTo(search.data)<0){
			gr=node;
			node=node.right;
		}
		else{
			child=node.left;
			break;
		}
	}
	node.left=child.right;
	node.Lheight=child.Rheight;
	swaph=max(node.Lheight,node.Rheight)+1;
	node.balanceFactor=node.Rheight-node.Lheight;
	child.right=node;
	child.Rheight=swaph;
	child.balanceFactor=child.Rheight-child.Lheight;
	if(gr.right==node){
		gr.right=child;
		gr.Rheight=max(child.Rheight,Lheight)+1;
		gr.balanceFactor=gr.Rheight-gr.Lheight;// not necessary but for safety it needs to be done
	}
	else if(gr.left==node){
		gr.left=child;
		gr.Lheight=max(child.Rheight,Lheight)+1;
		gr.balanceFactor=gr.Rheight-gr.Lheight;// not necessary but for safety it needs to be done
	}
	
}
private int max(int n, int m){
	if(m>n)
		return m;
	else
		return n;
}
