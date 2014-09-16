package ijp.literalgenerator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.command.UndoConfirmationPolicy;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;

import java.util.Random;

@SuppressWarnings("PublicConstructor")
public class GenerateString extends AnAction {
	private static final int MAX_SIZE = 80;
	private static final String DOUBLE_QUOTE = "\"";
	private static final String POOL = "! #$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[ ]^_`abcdefghijklmnopqrstuvwxyz{|}~";
	static final Random RND = new Random();

	@Override
	public void actionPerformed(final AnActionEvent e) {
		final Project project = e.getData(CommonDataKeys.PROJECT);
		final Runnable command = new Runnable() {
			@Override
			public void run() {
				ApplicationManager.getApplication().runWriteAction(Inserter.create(e, DOUBLE_QUOTE, randomString(), DOUBLE_QUOTE));
			}
		};
		final String name = "Generate String Literal";
		final UndoConfirmationPolicy undoPolicy = UndoConfirmationPolicy.DO_NOT_REQUEST_CONFIRMATION;
		CommandProcessor.getInstance().executeCommand(project, command, name, undoPolicy);
	}

	private static String randomString()
	{
		final int length = (Math.abs(RND.nextInt()) % (MAX_SIZE - 1)) + 1;
		final StringBuilder result = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			result.append(POOL.charAt(RND.nextInt(POOL.length())));
		}
		return result.toString();
	}
}
