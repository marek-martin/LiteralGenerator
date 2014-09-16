package ijp.literalgenerator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.command.UndoConfirmationPolicy;
import com.intellij.openapi.project.Project;

import java.util.Random;

@SuppressWarnings("PublicConstructor")
public class GenerateDouble extends AnAction {
	static final Random RND = new Random();

	@Override
	public void actionPerformed(final AnActionEvent e) {
		final Project project = e.getData(CommonDataKeys.PROJECT);
		final Runnable command = new Runnable() {
			@Override
			public void run() {
				ApplicationManager.getApplication().runWriteAction(Inserter.create(e, "", Double.toString(RND.nextDouble()), "D"));
			}
		};
		final String name = "Generate Double Literal";
		final UndoConfirmationPolicy undoPolicy = UndoConfirmationPolicy.DO_NOT_REQUEST_CONFIRMATION;
		CommandProcessor.getInstance().executeCommand(project, command, name, undoPolicy);
	}
}
