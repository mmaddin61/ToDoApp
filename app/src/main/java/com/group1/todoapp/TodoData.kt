package com.group1.todoapp

import org.w3c.dom.Document
import org.w3c.dom.Element

data class TodoData(var id : Int, var title: String, val tasks: MutableList<TaskData>) {
    companion object {
        fun Export(doc : Document, root : Element,  todoData : TodoData) {
            val element = doc.createElement("Todo")
            element.setAttribute("id", todoData.id.toString())
            element.setAttribute("title", todoData.title)

            todoData.tasks.forEach() {
                TaskData.Export(doc, element, it)
            }

            root.appendChild(element)
        }

        fun Import(todoElement : Element) : TodoData {
            val id = todoElement.getAttribute("id").toInt()
            val title = todoElement.getAttribute("title")

            val tasks = mutableListOf<TaskData>()

            val nodes = todoElement.getElementsByTagName("Task")

            for (n in 0 until nodes.length) {
                val element = nodes.item(n) as Element
                tasks.add(TaskData.Import(element))
            }

            return TodoData(id, title, tasks)
        }
    }
}