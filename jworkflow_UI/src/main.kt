import panels.WorkflowTabbedPane
import utils.FileUtils
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.UIManager

val FRAME_WIDTH = 800
val FRAME_HEIGHT = 500

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
    frame.iconImage = FileUtils.loadImage("icons/icon.png")

    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT)
    frame.isVisible = true
}