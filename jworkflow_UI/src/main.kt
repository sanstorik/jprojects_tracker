import activities.days.Days
import activities.projects.Projects
import native_hooks.ActivityGlobalListener
import panels.WorkflowTabbedPane
import java.util.*
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.UIManager
import kotlin.concurrent.timerTask

val FRAME_WIDTH = 750
val FRAME_HEIGHT = 450

fun main(args: Array<String>) {
    SwingUtilities.invokeLater {
        UIManager.put("swing.boldMetal", false)
        createAndShowGui()
    }
}

private fun createAndShowGui() {
    val frame = JFrame()
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.add(WorkflowTabbedPane())

    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT)
    frame.isVisible = true
}