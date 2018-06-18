package plugin.achdjian.it.ui

import java.awt.GridLayout
import javax.swing.JComponent
import javax.swing.JPanel

class LayoutBuilder {
    val rows = ArrayList<Row>()

    fun row(text:String?=null,init: Row.() -> Unit){
        val row = Row(text)
        row.init()
        rows.add(row)
    }

    fun row(text:String?=null,component: JComponent){
        val row = Row(text)
        row.component = component
        rows.add(row)
    }

    fun build(panel: JPanel) {
        var colSize=1

        if (rows.filterNotNull().any())
            colSize = 2
        val layout = GridLayout(rows.size, colSize)
        panel.layout = layout

        if (colSize==1)
            rows.forEach({panel.add(it.component)})
        else
            rows.forEach({panel.add(it.label());panel.add(it.component)})
    }
}