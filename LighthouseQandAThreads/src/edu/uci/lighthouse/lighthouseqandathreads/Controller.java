package edu.uci.lighthouse.lighthouseqandathreads;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;

import edu.uci.lighthouse.model.QAforums.LHforum;


public class Controller implements Observer{
	NewQuestionDialog nqDialog;
	LHforum forum;
	
	public Controller(NewQuestionDialog dialog, LHforum forum){
		nqDialog = dialog;
		//nqDialog.getObservablePoint().addObserver(this);
		//FakeDataBase.getInstance().addObserver(this);
		this.forum = forum;
		this.forum.addObserver(this);
		
		
	}
	

	
	public void stopObserving(){
		//FakeDataBase.getInstance().deleteObserver(this);
		forum.deleteObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		/*if(DataBase update){
			//new reply Post to Post
			
		}else if(Dialog update){
			//loaded?
		}*/
		nqDialog.clearTree();
		//nqDialog.populateTree(FakeDataBase.getInstance().getForum());
		nqDialog.populateTree(forum);
	}
	
	
}
