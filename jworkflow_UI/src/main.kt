import panels.WorkflowTabbedPane
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.UIManager

val FRAME_WIDTH = 750
val FRAME_HEIGHT = 450

fun main(args: Array<String>) {
    SwingUtilities.invokeLater {
        UIManager.put("swing.boldMetal", false)
        createAndShowGui()
    }
}

private fun createAndShowGui() {
    val frame = JFrame("Workflow Analyzer")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.add(WorkflowTabbedPane())

    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT)
    frame.isVisible = true
}