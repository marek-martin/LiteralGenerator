package ijp.literalgenerator;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;

public class Inserter implements Runnable {
	private final AnActionEvent event;
	private final String prefix;
	private final String value;
	private final String suffix;

	public static Runnable create(final AnActionEvent event, final String prefix, final String value, final String suffix) {
		return new Inserter(event, prefix, value, suffix);
	}

	private Inserter(final AnActionEvent event, final String prefix, final String value, final String suffix) {
		this.event = event;
		this.prefix = prefix;
		this.value = value;
		this.suffix = suffix;
	}

	@Override
	public void run() {
		final Editor editor = event.getData(CommonDataKeys.EDITOR);
		if (editor != null) {
			final Document document = editor.getDocument();
			final SelectionModel selectionModel = editor.getSelectionModel();
			final CaretModel caretModel = editor.getCaretModel();
			if (selectionModel.hasSelection()) {
				final int startPos = selectionModel.getSelectionStart();
				final int oldEndPos = selectionModel.getSelectionEnd();
				document.replaceString(startPos, oldEndPos, value);
				final int newEndPos = startPos + value.length();
				selectionModel.setSelection(startPos, newEndPos);
				caretModel.moveToOffset(newEndPos);
			} else {
				final int initialPos = caretModel.getOffset();
				document.insertString(initialPos, prefix);
				final int valueStartPos = initialPos + prefix.length();
				document.insertString(valueStartPos, value);
				final int valueEndPos = valueStartPos + value.length();
				document.insertString(valueEndPos, suffix);
				selectionModel.setSelection(valueStartPos, valueEndPos);
				caretModel.moveToOffset(valueEndPos);
			}
		}
	}

	@Override
	public String toString() {
		return String.format("Inserter{event=%s, prefix='%s', value='%s', suffix='%s'}", event, prefix, value, suffix);
	}
}
