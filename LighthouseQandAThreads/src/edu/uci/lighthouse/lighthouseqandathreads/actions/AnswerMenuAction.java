package edu.uci.lighthouse.lighthouseqandathreads.actions;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import edu.uci.lighthouse.core.util.ModelUtility;
import edu.uci.lighthouse.lighthouseqandathreads.PersistAndUpdate;
import edu.uci.lighthouse.lighthouseqandathreads.view.CompartmentViews.CompartmentNewPostView;
import edu.uci.lighthouse.model.LighthouseAuthor;
import edu.uci.lighthouse.model.LighthouseClass;
import edu.uci.lighthouse.model.LighthouseEntity;
import edu.uci.lighthouse.model.QAforums.ForumThread;
import edu.uci.lighthouse.model.QAforums.LHforum;
import edu.uci.lighthouse.model.QAforums.LHthreadCreator;
import edu.uci.lighthouse.model.QAforums.TeamMember;
import edu.uci.lighthouse.ui.utils.GraphUtils;

public class AnswerMenuAction extends Action{
	private TeamMember tm;
    private PersistAndUpdate pu;
    private ForumThread thread;
	public  AnswerMenuAction(ForumThread thread, TeamMember tm, PersistAndUpdate pu){
		this.tm = tm;
		this.pu = pu;
		
		this.thread = thread;
		setText("Set As Answer");
		
		if(!thread.getRootQuestion().getTeamMemberAuthor().getAuthor().getName().equals(tm.getAuthor().getName()))
			this.setEnabled(false);
	}
	public void run() {
		//post.setAnswer(true, tm);
	}
}