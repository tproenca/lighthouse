package edu.uci.lighthouse.lighthouseqandathreads.view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.MouseEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.swt.events.MouseListener;

import edu.uci.lighthouse.model.QAforums.ForumThread;
import edu.uci.lighthouse.model.QAforums.Post;
import edu.uci.lighthouse.model.QAforums.TeamMember;

public class ThreadView extends ConversationElement implements IHasObservablePoint, Observer{
	
	ArrayList<PostView> postViews = new ArrayList<PostView>();
	private ForumThread thread;
	private TeamMember tm;
	private int height = 30;
	private ObservablePoint observablePoint = new ObservablePoint();

	
	public ThreadView(Composite parent, int style, ForumThread thread, TeamMember tm) {
		super(parent, style);
		this.thread = thread;
			this.tm = tm;
	      GridData compsiteData = new GridData(550, 30);

			this.setLayout(new GridLayout(1, false));
			this.setLayoutData(compsiteData);
			this.setBackground(ColorConstants.white);
		
		
			addPost(thread.getRootQuestion());
	
	}
	
	
	
	public void addPost(Post post){
		
		GridData compsiteData = new GridData(550, height+30);
		this.setLayoutData(compsiteData);
		addPostView(post,  tm);
		this.getParent().layout();

		addResponsePosts(post.getResponses());
	}
	
	public void addResponsePosts(Set<Post> posts){
		
		
		
		for(Post post: posts){
			
			GridData compsiteData = new GridData(550, height+30);
			this.setLayoutData(compsiteData);
			
			
			addPostView(post,  tm);
			
			
			this.getParent().layout();
			
			for(Post childPost: post.getResponses()){
				addPost(childPost);
			}

		}


	}
	
	private void addPostView(Post post, TeamMember tm){
		PostView pv = new PostView(this, SWT.None, post,tm);
		pv.observeThis(this);
	}
	
	public void addPostView(PostView postView){
		this.postViews.add(postView);
	}



	public void observeThis(Observer observer) {
		observablePoint.addObserver(observer);
	}


	public ObservablePoint getObservablePoint() {
		return observablePoint;
	}



	@Override
	public void update(Observable o, Object arg) {
		observablePoint.changed(arg);
		
	}
	


}